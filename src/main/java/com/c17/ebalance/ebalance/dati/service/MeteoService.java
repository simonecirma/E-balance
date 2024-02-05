package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce il contratto per la gestione dei dati meteorologici nel sistema eBalance.
 * Fornisce metodi per ottenere le condizioni meteo attuali e settimanali.
 */
public interface MeteoService {

    /**
     * Ottiene le condizioni meteo attuali.
     *
     * @return Una lista di oggetti {@code MeteoBean} rappresentanti le condizioni meteo attuali.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<MeteoBean> getCondizioniMeteo()throws SQLException;

    /**
     * Ottiene le condizioni meteo settimanali.
     *
     * @return Una lista di oggetti {@code MeteoBean} rappresentanti le condizioni meteo settimanali.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<MeteoBean> getCondizioniSettimanali()throws SQLException;
}
