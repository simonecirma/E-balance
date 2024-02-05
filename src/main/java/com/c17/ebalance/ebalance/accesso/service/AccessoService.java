package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;

/**
 * L'interfaccia {@code AccessoService} definisce il contratto per la gestione delle operazioni di accesso
 * e autenticazione degli amministratori nel sistema eBalance.
 */
public interface AccessoService {

    /**
     * Effettua l'accesso di un amministratore utilizzando l'indirizzo email e la password forniti.
     *
     * @param email    L'indirizzo email dell'amministratore.
     * @param password La password dell'amministratore.
     * @return L'oggetto {@code AmministratoreBean} dell'amministratore autenticato, o null se le credenziali sono sbagliate.
     * @throws SQLException in caso di errori di accesso al database.
     */
    AmministratoreBean login(String email, String password) throws SQLException;

}
