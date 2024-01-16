package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;

public interface AmministratoreDAO {
    public boolean verificaSuperAdmin() throws SQLException;
    public AmministratoreBean login(final String email, final String password) throws SQLException;
    public List<AmministratoreBean> visualizzaAmministratori() throws SQLException;
    public AmministratoreBean aggiornaAmministratore(final AmministratoreBean amministratore) throws SQLException;
    public void aggiungiAmministratore(final AmministratoreBean amministratore) throws SQLException;
    public AmministratoreBean getById(final int id) throws SQLException;
    public void rimuoviAmministratore(final int idAmministratore) throws SQLException;

}
