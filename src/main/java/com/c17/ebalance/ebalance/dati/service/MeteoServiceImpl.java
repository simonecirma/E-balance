package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.MeteoDAO;
import com.c17.ebalance.ebalance.model.DAO.MeteoDAOImpl;
import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementazione del servizio per la gestione dei dati meteorologici nel sistema eBalance.
 * Utilizza un'istanza di {@code MeteoDAO} per accedere ai dati meteorologici dal database.
 *
 * @author Il Tuo Nome
 * @version 1.0
 */
public class MeteoServiceImpl implements MeteoService {
    private MeteoDAO meteoDAO = new MeteoDAOImpl();

    /**
     * Ottiene le condizioni meteo attuali.
     *
     * @return Una lista di oggetti {@code MeteoBean} rappresentanti le condizioni meteo attuali.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public List<MeteoBean> getCondizioniMeteo() throws SQLException {
        return meteoDAO.getCondizioniMeteo();
    }

    /**
     * Ottiene le condizioni meteo settimanali.
     *
     * @return Una lista di oggetti {@code MeteoBean} rappresentanti le condizioni meteo settimanali.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public List<MeteoBean> getCondizioniSettimanali() throws SQLException {
        return meteoDAO.mediaGiornaliera();
    }
}
