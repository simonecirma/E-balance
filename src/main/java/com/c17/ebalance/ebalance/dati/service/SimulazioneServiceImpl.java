package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ConsumoDAO;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAOImpl;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAO;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAOImpl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SimulazioneServiceImpl implements SimulazioneService {
    private ConsumoDAO consumoDAO = new ConsumoDAOImpl();
    private ProduzioneDAO produzioneDAO = new ProduzioneDAOImpl();
    Calendar calendario = Calendar.getInstance();
    Date data;
    @Override
    public void simulazioneEnergia() throws SQLException {
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {

            Random random = new Random();
            int numEdifici = consumoDAO.ottieniNumEdifici();
            float consumoOrarioAttualeTot = 0.02f;
            for (int y = 0; y < numEdifici; y++) {
                float consumoOrario = random.nextFloat() * 15 + 15;
                consumoOrario = (float) (Math.round(consumoOrario * 100.0) / 100.0);
                consumoDAO.simulaConsumo(consumoOrario, y+1, sqlDate);
                //batteriaService.aggiornaBatteria(consumoOrario);
                consumoOrarioAttualeTot = consumoOrarioAttualeTot + consumoOrario;
            }

            float produzioneOrariaAttualeTot = 0.02f;
            Random random2 = new Random();
            int sorgentiAttive = produzioneDAO.ottieniSorgenti();
            for (int y = 1; y < sorgentiAttive; y++) {
                float produzioneOraria = random2.nextFloat() * 100 + 0;
                produzioneOraria = (float) (Math.round(produzioneOraria * 100.0) / 100.0);
                produzioneDAO.simulaProduzione(y+1, produzioneOraria,  sqlDate);
                produzioneOrariaAttualeTot = produzioneOrariaAttualeTot + produzioneOraria;
            }

            float produzioneNecessaria = 0.02f;
            produzioneNecessaria = (float) (Math.round((consumoOrarioAttualeTot - produzioneOrariaAttualeTot) * 100.0) / 100.0);
            produzioneDAO.simulaProduzioneSEN(produzioneNecessaria, sqlDate);

            Thread.sleep(10000); // Ritardo di 10 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }
}
