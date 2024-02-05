package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAO;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAOImpl;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@code ProduzioneService} che fornisce operazioni per ottenere informazioni sulla produzione di energia nel sistema eBalance.
 * Questa classe utilizza un'istanza di {@code ProduzioneDAO} per accedere ai dati di produzione dal database.
 */
public class ProduzioneServiceImpl implements ProduzioneService {

    private ProduzioneDAO produzioneDAO = new ProduzioneDAOImpl();

    /**
     * Ottiene e aggiorna l'array contenente la produzione prodotta da diverse sorgenti nel tempo.
     *
     * @param produzioneSorgente L'array contenente la produzione prodotta dalle diverse sorgenti nel tempo.
     * @return L'array aggiornato con la produzione attuale.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public float[] ottieniProduzioneProdotta(float[] produzioneSorgente) throws SQLException {
        int numElementi = produzioneSorgente.length;
        float produzioneAttuale = produzioneDAO.ottieniProduzioneProdotta();
        if (produzioneAttuale != produzioneSorgente[numElementi - 1]) {
            for (int i = 1; i < numElementi; i++) {
                produzioneSorgente[i - 1] = produzioneSorgente[i];
            }
            produzioneSorgente[numElementi - 1] = produzioneAttuale;
        }
        return produzioneSorgente;
    }

    /**
     * Ottiene l'energia deruivante dal Servizio Elettrico Nazionale.
     *
     * @return L'energia deruivante dal Servizio Elettrico Nazionale.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public float ottieniProduzioneSEN() throws SQLException {
        return produzioneDAO.ottieniProduzioneSEN();
    }

    /**
     * Ottiene i tipi di sorgenti di produzione di energia nel sistema.
     *
     * @return Una lista di oggetti {@code TipoSorgenteBean} rappresentanti i tipi di sorgenti di produzione di energia.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException {
        return produzioneDAO.ottieniTipoSorgente();
    }

    /**
     * Calcola la quantità di energia rinnovabile prodotta nel sistema in un intervallo di date specificato.
     *
     * @param dataInizio Data di inizio dell'intervallo.
     * @param dataFine   Data di fine dell'intervallo.
     * @return La quantità di energia rinnovabile prodotta nel sistema nell'intervallo di date specificato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public float energiaRinnovabileProdottaPerData(
            final Date dataInizio, final Date dataFine)
            throws SQLException {
        return produzioneDAO.energiaRinnovabileProdottaPerData(
                dataInizio, dataFine);
    }

}
