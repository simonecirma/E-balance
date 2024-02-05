package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAO;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAOImpl;

import java.sql.SQLException;

/**
 * La classe {@code AccessoServiceImpl} implementa l'interfaccia {@code AccessoService} e fornisce
 * l'implementazione concreta per le operazioni di accesso e autenticazione degli amministratori nel sistema eBalance.
 * Utilizza un'istanza di {@code AmministratoreDAO} per accedere al database e verificare le credenziali di accesso.
 */
public class AccessoServiceImpl implements AccessoService {

    private AmministratoreDAO amministratoreDAO = new AmministratoreDAOImpl();

    /**
     * Effettua l'accesso di un amministratore utilizzando l'indirizzo email e la password forniti,
     * delegando l'operazione al DAO associato.
     *
     * @param email    L'indirizzo email dell'amministratore.
     * @param password La password dell'amministratore.
     * @return L'oggetto {@code AmministratoreBean} dell'amministratore autenticato, o null se le credenziali sono sbagliate.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public AmministratoreBean login(final String email, final String password) throws SQLException {
        return amministratoreDAO.login(email, password);
    }
}
