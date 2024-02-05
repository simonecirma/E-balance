package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia {@code ContrattoService} definisce il contratto per la gestione dei contratti nel sistema eBalance.
 * Fornisce metodi per la visualizzazione, l'aggiornamento, l'aggiunta e l'accesso alle informazioni relative ai contratti.
 */
public interface ContrattoService {
    /**
     * Restituisce il contratto attivo nel sistema.
     *
     * @return Un oggetto {@code ContrattoBean} rappresentante il contratto attivo.
     * @throws SQLException in caso di errori di accesso al database.
     */
    ContrattoBean visualizzaContratto() throws SQLException;

    /**
     * Restituisce l'elenco storico di tutti i contratti nel sistema.
     *
     * @return Una lista di oggetti {@code ContrattoBean} rappresentanti gli storici contratti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<ContrattoBean> visualizzaStoricoContratti() throws SQLException;

    /**
     * Aggiorna le informazioni del contratto nel sistema.
     *
     * @param contratto L'oggetto {@code ContrattoBean} rappresentante il contratto con le informazioni aggiornate.
     * @return Un oggetto {@code ContrattoBean} con le informazioni aggiornate.
     * @throws SQLException in caso di errori di accesso al database.
     */
    ContrattoBean aggiornaContratto(ContrattoBean contratto) throws SQLException;

    /**
     * Aggiunge un nuovo contratto al sistema.
     *
     * @param contrattoNuovo L'oggetto {@code ContrattoBean} rappresentante il nuovo contratto da aggiungere.
     * @throws SQLException in caso di errori di accesso al database.
     */
    void aggiungiContratto(ContrattoBean contrattoNuovo) throws SQLException;

    /**
     * Verifica se è presente almeno un contratto nel sistema.
     *
     * @return True se è presente almeno un contratto, altrimenti False.
     * @throws SQLException in caso di errori di accesso al database.
     */
    boolean verificaPrimoContratto() throws SQLException;

    /**
     * Restituisce il contratto attivo nel sistema per il periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Un oggetto {@code ContrattoBean} rappresentante il contratto attivo nel periodo specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    ContrattoBean getContrattoAttivo(final Date dataInizio, final Date dataFine) throws SQLException;

    /**
     * Ottiene il prezzo di vendita dal contratto attivo nel sistema.
     *
     * @return Il prezzo di vendita del contratto attivo.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float ottieniPrezzoVendita() throws SQLException;
}

