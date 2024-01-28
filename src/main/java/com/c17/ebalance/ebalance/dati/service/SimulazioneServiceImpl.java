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
    Calendar calendario = Calendar.getInstance();
    Date data;
    int cont=0;
    @Override
    public void simulazioneEnergia() throws SQLException {
        int numBatterie = batteriaDAO.ottieniNumBatterieAttive();
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {
            for (int i = 0; i < 24; i++) {
                cont++;
                List<InteragisceBean> parametriAttivi = parametriIADAO.ottieniParametriAttivi();

                Random random = new Random();
                int numEdifici = consumoDAO.ottieniNumEdifici();
                float consumoOrarioAttualeTot = 0.02f;
                for (int y = 0; y < numEdifici; y++) {
                    float consumoOrario = random.nextFloat() * 15 + 15;
                    consumoOrario = (float) (Math.round(consumoOrario * 100.0) / 100.0);
                    consumoDAO.simulaConsumo(consumoOrario, y+1, sqlDate);
                    batteriaDAO.aggiornaBatteria((-consumoOrario) / numBatterie, numBatterie);
                    consumoOrarioAttualeTot = consumoOrarioAttualeTot + consumoOrario;
                }

                float produzioneOrariaAttualeTot = 0.02f;
                Random random2 = new Random();
                int sorgentiAttive = produzioneDAO.ottieniSorgenti();
                for (int y = 1; y < sorgentiAttive; y++) {
                    float produzioneOraria = random2.nextFloat() * 100 + 0;
                    produzioneOraria = (float) (Math.round(produzioneOraria * 100.0) / 100.0);
                    for (InteragisceBean bean: parametriAttivi) {
                        if (bean.getTipoSorgente().equalsIgnoreCase("Pannello Fotovoltaico")) {
                            produzioneOraria = produzioneOraria * ((float) bean.getPercentualeUtilizzoSorgente() / 100);
                            break;
                        }
                    }
                    produzioneDAO.simulaProduzione(y+1, produzioneOraria,  sqlDate);
                    batteriaDAO.aggiornaBatteria((produzioneOraria) / numBatterie, numBatterie);
                    produzioneOrariaAttualeTot = produzioneOrariaAttualeTot + produzioneOraria;
                }

                float produzioneNecessaria = (float) (Math.round((consumoOrarioAttualeTot - produzioneOrariaAttualeTot) * 100.0) / 100.0);
                for (InteragisceBean bean: parametriAttivi) {
                    if (bean.getTipoSorgente().equalsIgnoreCase("Pannello Fotovoltaico")) {
                        produzioneNecessaria = produzioneNecessaria * ((float) bean.getPercentualeUtilizzoSorgente() /100);
                        break;
                    }
                }
                produzioneDAO.simulaProduzioneSEN(produzioneNecessaria, sqlDate);
                batteriaDAO.aggiornaBatteria((produzioneNecessaria) / numBatterie, numBatterie);
                System.out.println("simulazione num:" + cont);

                Thread.sleep(10000); // Ritardo di 10 secondi
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }

    @Override
    public void insertPrevisioni() throws SQLException {
        meteoDAO.insertPrevisioni();
    }
}
