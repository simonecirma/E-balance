package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia {@code VenditaService} fornisce metodi per la gestione delle vendite
 * nell'ambito delle funzionalità dell'amministratore dell'applicazione.
 */
public interface VenditaService {

    /**
     * Restituisce una lista di oggetti {@code VenditaBean} che rappresentano le vendite nel periodo specificato.
     *
     * @param dataInizio Data di inizio del periodo delle vendite.
     * @param dataFine   Data di fine del periodo delle vendite.
     * @return Lista di {@code VenditaBean} corrispondenti alle vendite nel periodo specificato.
     * @throws SQLException Se si verifica un'eccezione SQL durante l'accesso al database.
     */
    List<VenditaBean> getVendite(Date dataInizio, Date dataFine) throws SQLException;

    /**
     * Restituisce il ricavo totale generato dalle vendite nel periodo specificato.
     *
     * @param dataInizio Data di inizio del periodo delle vendite.
     * @param dataFine   Data di fine del periodo delle vendite.
     * @return Ricavo totale delle vendite nel periodo specificato.
     * @throws SQLException Se si verifica un'eccezione SQL durante l'accesso al database.
     */
    float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException;

    /**
     * Registra l'effettuazione di una nuova vendita associata all'amministratore identificato dall'ID specificato.
     *
     * @param idAmministratore ID dell'amministratore associato alla vendita.
     * @throws SQLException Se si verifica un'eccezione SQL durante l'accesso al database.
     */
    void effettuaVendita(int idAmministratore) throws SQLException;
}
