package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.amministratoreDAO;
import com.c17.ebalance.ebalance.model.entity.amministratoreBean;

import java.sql.SQLException;
import java.util.List;

public class amministratoreServiceImpl implements amministratoreService {

    private amministratoreDAO amministratoreDAO = new amministratoreDAO();

    @Override
    public List<amministratoreBean> visualizzaAmministratori() throws SQLException {
        return amministratoreDAO.visualizzaAmministratori();
    }

    @Override
    public amministratoreBean aggiornaAmministratore(amministratoreBean amministratore) throws SQLException {
        return amministratoreDAO.aggiornaAmministratore(amministratore);
    }
}
