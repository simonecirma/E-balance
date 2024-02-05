package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati per gli amministratori nel sistema eBalance.
 * Questa interfaccia fornisce i metodi per verificare la presenza di un super amministratore, eseguire il login,
 * visualizzare gli amministratori, aggiornare un amministratore, aggiungere un nuovo amministratore,
 * ottenere un amministratore per ID, rimuovere un amministratore, e verificare la presenza di un'email nel sistema.
 * Tutti i metodi possono generare un'eccezione SQLException in caso di errore durante l'accesso al database.
 */
public interface AmministratoreDAO {

    /**
     * Verifica se esiste un super amministratore nel sistema.
     *
     * @return true se esiste almeno un super amministratore, false altrimenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    boolean verificaSuperAdmin() throws SQLException;

    /**
     * Esegue il login di un amministratore utilizzando l'email e la password fornite.
     *
     * @param email    L'email dell'amministratore.
     * @param password La password dell'amministratore.
     * @return Un oggetto AmministratoreBean se il login ha successo, null altrimenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    AmministratoreBean login(final String email, final String password) throws SQLException;

    /**
     * Restituisce una lista di tutti gli amministratori presenti nel sistema.
     *
     * @return Una lista di oggetti AmministratoreBean.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<AmministratoreBean> visualizzaAmministratori() throws SQLException;

    /**
     * Aggiorna i dati di un amministratore nel sistema.
     *
     * @param amministratore L'oggetto AmministratoreBean con i nuovi dati.
     * @return L'oggetto AmministratoreBean aggiornato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    AmministratoreBean aggiornaAmministratore(final AmministratoreBean amministratore) throws SQLException;

    /**
     * Aggiunge un nuovo amministratore al sistema.
     *
     * @param amministratore L'oggetto AmministratoreBean con i dati del nuovo amministratore.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void aggiungiAmministratore(final AmministratoreBean amministratore) throws SQLException;

    /**
     * Restituisce un amministratore per ID.
     *
     * @param id L'ID dell'amministratore da ottenere.
     * @return L'oggetto AmministratoreBean corrispondente all'ID specificato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    AmministratoreBean getById(final int id) throws SQLException;

    /**
     * Rimuove un amministratore dal sistema utilizzando il suo ID.
     *
     * @param idAmministratore L'ID dell'amministratore da rimuovere.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void rimuoviAmministratore(final int idAmministratore) throws SQLException;

    /**
     * Verifica la presenza di un'email nel sistema.
     *
     * @param email L'email da verificare.
     * @return true se l'email è presente, false altrimenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    boolean verificaPresenzaEmail(String email) throws SQLException;
}
