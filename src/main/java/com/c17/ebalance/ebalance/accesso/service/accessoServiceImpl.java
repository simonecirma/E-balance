package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.entity.amministratoreBean;
import com.c17.ebalance.ebalance.model.DAO.amministratoreDAO;

import java.sql.SQLException;

public class accessoServiceImpl implements accessoService{

    amministratoreDAO amminstratoreDAO = new amministratoreDAO();
    @Override
    public amministratoreBean login(String email, String password) throws SQLException {
        return amministratoreDAO.login(email, password);
    }
}
