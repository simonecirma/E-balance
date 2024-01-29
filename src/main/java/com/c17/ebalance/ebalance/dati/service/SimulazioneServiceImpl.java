package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.BatteriaDAO;
import com.c17.ebalance.ebalance.model.DAO.BatteriaDAOImpl;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAO;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAOImpl;
import com.c17.ebalance.ebalance.model.DAO.MeteoDAO;
import com.c17.ebalance.ebalance.model.DAO.MeteoDAOImpl;
import com.c17.ebalance.ebalance.model.DAO.ParametriIADAO;
import com.c17.ebalance.ebalance.model.DAO.ParametriIADAOImpl;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAO;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAOImpl;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SimulazioneServiceImpl implements SimulazioneService {
    private ConsumoDAO consumoDAO = new ConsumoDAOImpl();
    private ProduzioneDAO produzioneDAO = new ProduzioneDAOImpl();
    private BatteriaDAO batteriaDAO = new BatteriaDAOImpl();
    private MeteoDAO meteoDAO = new MeteoDAOImpl();
    private ParametriIADAO parametriIADAO = new ParametriIADAOImpl();
    private Random random = new Random();
    private Calendar calendario = Calendar.getInstance();
    private Date data;
    float percentualeEccesso = 0.00f;
    private boolean simulazioneVenditaFlag = false; //setta a true se vuoi far simulare la generazione di una vendita
    @Override
    public void simulazioneEnergia() throws SQLException {
        int numBatterie = batteriaDAO.ottieniNumBatterieAttive();
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {
            for (int i = 0; i < 24; i++) {
                List<InteragisceBean> parametriAttivi = parametriIADAO.ottieniParametriAttivi();

                Random random = new Random();
                int numEdifici = consumoDAO.ottieniNumEdifici();
                float consumoOrarioAttualeTot = 0.02f;
                for (int y = 0; y < numEdifici; y++) {
                    float consumoOrario = 0.00f;
                    if (simulazioneVenditaFlag) {
                        consumoOrario = random.nextFloat() * 9 + 3;
                    } else {
                        consumoOrario = random.nextFloat() * 15 + 15;
                    }
                    consumoOrario = (float) (Math.round(consumoOrario * 100.0) / 100.0);
                    consumoDAO.simulaConsumo(consumoOrario, y+1, sqlDate);
                    percentualeEccesso = batteriaDAO.aggiornaBatteria((-consumoOrario) / numBatterie, numBatterie);
                    if (percentualeEccesso < 0) {
                        parametriIADAO.aggiornaPercentualeSEN(10);
                    }
                    consumoOrarioAttualeTot = consumoOrarioAttualeTot + consumoOrario;
                }

                float produzioneOrariaAttualeTot = 0.02f;
                Random random2 = new Random();
                int sorgentiAttive = produzioneDAO.ottieniSorgenti();
                for (int y = 1; y < sorgentiAttive; y++) {
                    float produzioneOraria = 0.00f;
                    if (simulazioneVenditaFlag) {
                        produzioneOraria = random2.nextFloat() * 200 + 100;
                    } else {
                        produzioneOraria = random2.nextFloat() * 100 + 0;
                    }
                    produzioneOraria = (float) (Math.round(produzioneOraria * 100.0) / 100.0);
                    for (InteragisceBean bean: parametriAttivi) {
                        if (bean.getTipoSorgente().equalsIgnoreCase("Pannello Fotovoltaico")) {
                            produzioneOraria = produzioneOraria * ((float) bean.getPercentualeUtilizzoSorgente() / 100);
                            break;
                        }
                    }
                    produzioneDAO.simulaProduzione(y+1, produzioneOraria,  sqlDate);
                    percentualeEccesso = batteriaDAO.aggiornaBatteria((produzioneOraria) / numBatterie, numBatterie);
                    if (percentualeEccesso > 0) {
                        parametriIADAO.aggiornaPercentualeSEN(-10);
                    }
                    produzioneOrariaAttualeTot = produzioneOrariaAttualeTot + produzioneOraria;
                }

                if (consumoOrarioAttualeTot > produzioneOrariaAttualeTot) {
                    float produzioneNecessaria = (float) (Math.round((consumoOrarioAttualeTot - produzioneOrariaAttualeTot) * 100.0) / 100.0);
                    for (InteragisceBean bean: parametriAttivi) {
                        if (bean.getTipoSorgente().equalsIgnoreCase("Servizio Elettrico Nazionale")) {
                            produzioneNecessaria = produzioneNecessaria * ((float) bean.getPercentualeUtilizzoSorgente() /100);
                            break;
                        }
                    }
                    produzioneDAO.simulaProduzioneSEN(produzioneNecessaria, sqlDate);
                    percentualeEccesso = batteriaDAO.aggiornaBatteria((produzioneNecessaria) / numBatterie, numBatterie);
                    if (percentualeEccesso > 0) {
                        parametriIADAO.aggiornaPercentualeSEN(-10);
                    }
                }


                Thread.sleep(10000); // Ritardo di 10 secondi
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }

    @Override
    public void insertPrevisioni() throws SQLException {

        List<String> condizioni = meteoDAO.getCondizione();
        try {
            data = calendario.getTime();
            java.sql.Date sqlDate = new java.sql.Date(data.getTime());
            java.sql.Time sqlTime = new java.sql.Time(data.getTime());
            for (int i = 0; i < 12; i++) {
                float vel = random.nextFloat() * 10;
                vel = (float) (Math.round(vel * 100.0) / 100.0);
                int indiceCasuale = random.nextInt(condizioni.size());
                String condizioneCasuale = condizioni.get(indiceCasuale);
                int prob = 0;
                if (condizioneCasuale.equalsIgnoreCase("nevoso")) {
                    prob = random.nextInt(10) + 30;
                }
                if (condizioneCasuale.equalsIgnoreCase("nuvoloso")) {
                    prob = random.nextInt(10) + 30;
                }
                if (condizioneCasuale.equalsIgnoreCase("piovoso")) {
                    prob = random.nextInt(20) + 40;
                }
                if (condizioneCasuale.equalsIgnoreCase("sereno")) {
                    prob = random.nextInt(20);
                }
                if (condizioneCasuale.equalsIgnoreCase("soleggiato")) {
                    prob = random.nextInt(10);
                }
                if (condizioneCasuale.equalsIgnoreCase("ventilato")) {
                    prob = random.nextInt(10) + 20;
                }
                meteoDAO.insertPrevisioni(sqlDate, sqlTime, vel, prob, condizioneCasuale);
                calendario.add(Calendar.HOUR_OF_DAY, 6);
                data = calendario.getTime();
                sqlDate = new java.sql.Date(data.getTime());
                sqlTime = new java.sql.Time(data.getTime());
            }
            Thread.sleep(10000); // Ritardo di 10 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificaPrevisioni() throws SQLException {
        List<String> condizioni=meteoDAO.getCondizione();
        try {
            data = calendario.getTime();
            java.sql.Date sqlDate = new java.sql.Date(data.getTime());
            java.sql.Time sqlTime = new java.sql.Time(data.getTime());
            float vel = random.nextFloat() * 10;
            vel = (float) (Math.round(vel * 100.0) / 100.0);
            int indiceCasuale = random.nextInt(condizioni.size());
            String condizioneCasuale = condizioni.get(indiceCasuale);
            int prob = 0;
            if (condizioneCasuale.equalsIgnoreCase("nevoso")) {
                prob = random.nextInt(10) + 30;
            }
            if (condizioneCasuale.equalsIgnoreCase("nuvoloso")) {
                prob = random.nextInt(10) + 30;
            }
            if (condizioneCasuale.equalsIgnoreCase("piovoso")) {
                prob = random.nextInt(20) + 40;
            }
            if (condizioneCasuale.equalsIgnoreCase("sereno")) {
                prob = random.nextInt(20);
            }
            if (condizioneCasuale.equalsIgnoreCase("soleggiato")) {
                prob = random.nextInt(10);
            }
            if (condizioneCasuale.equalsIgnoreCase("ventilato")) {
                prob = random.nextInt(10) + 20;
            }
            meteoDAO.insertPrevisioni(sqlDate, sqlTime, vel, prob, condizioneCasuale);
            calendario.add(Calendar.HOUR_OF_DAY, 6);
            Thread.sleep(10000); // Ritardo di 10 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
