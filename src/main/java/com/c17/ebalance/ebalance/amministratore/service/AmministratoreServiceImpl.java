package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAO;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;

public class AmministratoreServiceImpl implements AmministratoreService {

    private AmministratoreDAO amministratoreDAO = new AmministratoreDAO();

    public Boolean verificaSuperAdmin() throws SQLException {
        return amministratoreDAO.verificaSuperAdmin();
    }
    @Override
    public List<AmministratoreBean> visualizzaAmministratori() throws SQLException {
        return amministratoreDAO.visualizzaAmministratori();
    }

    @Override
    public AmministratoreBean aggiornaAmministratore(final AmministratoreBean amministratore) throws SQLException {
        return amministratoreDAO.aggiornaAmministratore(amministratore);
    }
    public void aggiungiAmministratore(final AmministratoreBean amministratore) throws SQLException {
         amministratoreDAO.aggiungiAmministratore(amministratore);
    }
    public AmministratoreBean getById(final int id) throws SQLException {
        return amministratoreDAO.getById(id);
    }

    public void rimuoviAmministratore(final int idAmministratore) throws SQLException {
        amministratoreDAO.rimuoviAmministratore(idAmministratore);
    }

}
