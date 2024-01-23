package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;

public interface AmministratoreDAO {
    boolean verificaSuperAdmin() throws SQLException;
    AmministratoreBean login(final String email, final String password) throws SQLException;
    List<AmministratoreBean> visualizzaAmministratori() throws SQLException;
    AmministratoreBean aggiornaAmministratore(final AmministratoreBean amministratore) throws SQLException;
    void aggiungiAmministratore(final AmministratoreBean amministratore) throws SQLException;
    AmministratoreBean getById(final int id) throws SQLException;
    void rimuoviAmministratore(final int idAmministratore) throws SQLException;

}
