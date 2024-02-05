package com.c17.ebalance.ebalance.dati.service;

import java.sql.SQLException;

/**
 * Interfaccia che fornisce operazioni per la simulazione e l'inserimento di previsioni iniziali nel sistema eBalance.
 * Questa interfaccia definisce metodi per gestire la simulazione dei dati e l'inserimento di previsioni iniziali nel database.
 */
public interface SimulazioneService {

    /**
     * Esegue la simulazione dei dati nel sistema eBalance.
     *
     * @throws SQLException Se si verifica un errore durante l'esecuzione della simulazione o l'accesso al database.
     */
    void simulazione() throws SQLException;

    /**
     * Inserisce le previsioni iniziali nel sistema eBalance.
     *
     * @throws SQLException Se si verifica un errore durante l'inserimento delle previsioni iniziali o l'accesso al database.
     */
    void insertPrevisioniIniziali() throws SQLException;
}
