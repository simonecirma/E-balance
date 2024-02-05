package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAO;
import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAOImpl;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;


/**
 * La classe {@code AmministratoreServiceImpl} implementa l'interfaccia {@code AmministratoreService} e fornisce
 * l'implementazione concreta per le operazioni di gestione degli amministratori nel sistema eBalance.
 * Utilizza un'istanza di {@code AmministratoreDAO} per accedere al database e eseguire le operazioni specifiche.
 */
public class AmministratoreServiceImpl implements AmministratoreService {

    private AmministratoreDAO amministratoreDAO = new AmministratoreDAOImpl();

    /**
     * Verifica se esiste almeno un Super-Admin nel sistema.
     *
     * @return true se esiste almeno un superadmin, false altrimenti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public Boolean verificaSuperAdmin() throws SQLException {
        return amministratoreDAO.verificaSuperAdmin();
    }

    /**
     * Restituisce una lista di tutti gli amministratori presenti nel sistema.
     *
     * @return Una lista di oggetti {@code AmministratoreBean}.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public List<AmministratoreBean> visualizzaAmministratori() throws SQLException {
        return amministratoreDAO.visualizzaAmministratori();
    }

    /**
     * Aggiorna i dati di un amministratore nel sistema.
     *
     * @param amministratore L'oggetto {@code AmministratoreBean} con i nuovi dati.
     * @return L'oggetto {@code AmministratoreBean} aggiornato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public AmministratoreBean aggiornaAmministratore(final AmministratoreBean amministratore) throws SQLException {
        return amministratoreDAO.aggiornaAmministratore(amministratore);
    }

    /**
     * Aggiunge un nuovo amministratore al sistema.
     *
     * @param amministratore L'oggetto {@code AmministratoreBean} con i dati del nuovo amministratore.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public void aggiungiAmministratore(final AmministratoreBean amministratore) throws SQLException {
        amministratoreDAO.aggiungiAmministratore(amministratore);
    }

    /**
     * Restituisce l'oggetto {@code AmministratoreBean} corrispondente all'ID specificato.
     *
     * @param id L'ID dell'amministratore.
     * @return L'oggetto {@code AmministratoreBean} corrispondente all'ID specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public AmministratoreBean getById(final int id) throws SQLException {
        return amministratoreDAO.getById(id);
    }

    /**
     * Rimuove un amministratore dal sistema in base all'ID specificato.
     *
     * @param idAmministratore L'ID dell'amministratore da rimuovere.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public void rimuoviAmministratore(final int idAmministratore) throws SQLException {
        amministratoreDAO.rimuoviAmministratore(idAmministratore);
    }

    /**
     * Verifica se un'email è già presente nel sistema.
     *
     * @param email L'indirizzo email da verificare.
     * @return true se l'email è presente, false altrimenti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public boolean verificaPresenzaEmail(String email) throws SQLException {
        return amministratoreDAO.verificaPresenzaEmail(email);
    }

}
