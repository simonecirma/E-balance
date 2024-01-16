package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAO;
import com.c17.ebalance.ebalance.model.entity.amministratoreBean;

import java.sql.SQLException;
import java.util.List;

public class AmministratoreServiceImpl implements AmministratoreService {

    private AmministratoreDAO amministratoreDAO = new AmministratoreDAO();

    @Override
    public List<amministratoreBean> visualizzaAmministratori() throws SQLException {
        return amministratoreDAO.visualizzaAmministratori();
    }

    @Override
    public amministratoreBean aggiornaAmministratore(final amministratoreBean amministratore) throws SQLException {
        return amministratoreDAO.aggiornaAmministratore(amministratore);
    }
    public void aggiungiAmministratore(final amministratoreBean amministratore) throws SQLException {
         amministratoreDAO.aggiungiAmministratore(amministratore);
    }
    public amministratoreBean getById(final int id) throws SQLException {
        return amministratoreDAO.getById(id);
    }
}
