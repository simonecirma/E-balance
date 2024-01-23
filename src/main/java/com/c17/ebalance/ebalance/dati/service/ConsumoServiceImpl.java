package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ConsumoDAO;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ConsumoServiceImpl implements ConsumoService {
    private ConsumoDAO consumoDAO = new ConsumoDAOImpl();
    Calendar calendario = Calendar.getInstance();
    Date data;

    @Override
    public List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException {
        return consumoDAO.visualizzaConsumo();
    }

    @Override
    public float ottieniConsumiEdifici() throws SQLException {
        return consumoDAO.ottieniConsumiEdifici();
    }

    @Override
    public List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException {
        return consumoDAO.visualizzaStoricoConsumi();
    }

    @Override
    public void simulaConsumo() throws SQLException {
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());

        for (int i = 0; i < 24; i++) {
            Random random = new Random();
            int numEdifici = consumoDAO.ottieniNumEdifici();

            for (int y = 0; y < numEdifici; y++) {
                float consumoOrario = random.nextFloat() * 15 + 15;
                consumoOrario = (float) (Math.round(consumoOrario * 100.0) / 100.0);
                consumoDAO.simulaConsumo(consumoOrario, y+1, sqlDate);
                //batteriaService.aggiornaBatteria(consumoOrario);
            }
            try {
                Thread.sleep(10000); // Ritardo di 10 secondi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }

    public float getConsumoPerData(java.sql.Date dataInizio, java.sql.Date dataFine) throws SQLException{
        return consumoDAO.getConsumoPerData(dataInizio, dataFine);
    }
}
