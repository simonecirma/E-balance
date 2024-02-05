package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia {@code AmministratoreService} definisce il contratto per la gestione degli amministratori
 * nel sistema eBalance. Fornisce metodi per la verifica, la visualizzazione, l'aggiornamento, l'aggiunta e
 * la rimozione degli amministratori, nonché la verifica della presenza di un'email utile alla registrazione.
 *
 * @author Il Tuo Nome
 * @version 1.0
 */
public interface AmministratoreService {

    /**
     * Verifica se esiste almeno un Super-Admin nel sistema.
     *
     * @return true se esiste almeno un superadmin, false altrimenti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    Boolean verificaSuperAdmin() throws SQLException;

    /**
     * Restituisce una lista di tutti gli amministratori presenti nel sistema.
     *
     * @return Una lista di oggetti {@code AmministratoreBean}.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<AmministratoreBean> visualizzaAmministratori() throws SQLException;

    /**
     * Aggiorna i dati di un amministratore nel sistema.
     *
     * @param amministratore L'oggetto {@code AmministratoreBean} con i nuovi dati.
     * @return L'oggetto {@code AmministratoreBean} aggiornato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    AmministratoreBean aggiornaAmministratore(AmministratoreBean amministratore) throws SQLException;

    /**
     * Aggiunge un nuovo amministratore al sistema.
     *
     * @param amministratore L'oggetto {@code AmministratoreBean} con i dati del nuovo amministratore.
     * @throws SQLException in caso di errori di accesso al database.
     */
    void aggiungiAmministratore(AmministratoreBean amministratore) throws SQLException;

    /**
     * Restituisce l'oggetto {@code AmministratoreBean} corrispondente all'ID specificato.
     *
     * @param id L'ID dell'amministratore.
     * @return L'oggetto {@code AmministratoreBean} corrispondente all'ID specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    AmministratoreBean getById(int id) throws SQLException;

    /**
     * Rimuove un amministratore dal sistema in base all'ID specificato.
     *
     * @param idAmministratore L'ID dell'amministratore da rimuovere.
     * @throws SQLException in caso di errori di accesso al database.
     */
    void rimuoviAmministratore(int idAmministratore) throws SQLException;

    /**
     * Verifica se un'email è già presente nel sistema.
     *
     * @param email L'indirizzo email da verificare.
     * @return true se l'email è presente, false altrimenti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    boolean verificaPresenzaEmail(String email) throws SQLException;
}
