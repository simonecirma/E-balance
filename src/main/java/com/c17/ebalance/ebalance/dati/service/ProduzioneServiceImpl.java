package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAO;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ProduzioneServiceImpl implements ProduzioneService {

    private ProduzioneDAO produzioneDAO = new ProduzioneDAOImpl();
    Calendar calendario = Calendar.getInstance();
    Date data;

    @Override
    public List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException {
        return produzioneDAO.visualizzaProduzione();
    }

    @Override
    public List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException {
        return produzioneDAO.visualizzaProduzioneSorgente();
    }

    @Override
    public String[][] ottieniProduzione() throws SQLException {
        return produzioneDAO.ottieniProduzione();
    }

    @Override
    public List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException {
        return produzioneDAO.ottieniTipoSorgente();
    }

    @Override
    public void simulaProduzione() throws SQLException {
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        for (int i = 0; i < 24; i++) {
            Random random = new Random();
            int sorgentiAttive = produzioneDAO.ottieniSorgenti();

            for (int y = 1; y < sorgentiAttive; y++) {
                float produzioneSimulata = random.nextFloat() * 100 + 0;
                produzioneSimulata = (float) (Math.round(produzioneSimulata * 100.0) / 100.0);
                produzioneDAO.simulaProduzione(y+1, produzioneSimulata,  sqlDate);
            }
            try {
                Thread.sleep(10000); // Ritardo di 10 secondi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }

    public void simulaProduzioneSEN() throws SQLException {
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {
            Thread.sleep(1000); // Ritardo di 10 secondi
            float produzioneNecessaria = (float) (Math.round(produzioneDAO.ottieniProduzioneNecessaria() * 100.0) / 100.0);
            produzioneDAO.simulaProduzioneSEN(produzioneNecessaria, sqlDate);
            Thread.sleep(9000); // Ritardo di 10 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }

}
