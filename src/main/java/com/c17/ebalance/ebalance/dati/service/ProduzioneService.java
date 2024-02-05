package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce i metodi per la gestione della produzione di energia nel sistema eBalance.
 * Questa interfaccia fornisce operazioni per ottenere informazioni sulla produzione di energia, inclusa la produzione da sorgenti rinnovabili.
 * Le implementazioni di questa interfaccia devono essere fornite per accedere ai dati di produzione dal database.
 */
public interface ProduzioneService {

    /**
     * Ottiene la produzione di energia prodotta da diverse sorgenti.
     *
     * @param produzioneSorgente Un array di float rappresentante la produzione da diverse sorgenti.
     * @return Un array di float con la produzione di energia da diverse sorgenti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float[] ottieniProduzioneProdotta(float[] produzioneSorgente) throws SQLException;

    /**
     * Ottiene la produzione di energia dal Servizio Elettrico Nazionale (SEN).
     *
     * @return La produzione di energia dal SEN.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float ottieniProduzioneSEN() throws SQLException;

    /**
     * Ottiene i tipi di sorgenti disponibili per la produzione di energia.
     *
     * @return Una lista di oggetti {@code TipoSorgenteBean} rappresentanti i tipi di sorgenti disponibili.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException;

    /**
     * Ottiene l'energia rinnovabile prodotta nel sistema nel periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return L'energia rinnovabile prodotta nel periodo specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float energiaRinnovabileProdottaPerData(final Date dataInizio, final Date dataFine) throws SQLException;

}
