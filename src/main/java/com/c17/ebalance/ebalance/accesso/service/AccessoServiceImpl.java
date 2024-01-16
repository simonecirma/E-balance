package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.entity.amministratoreBean;
import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAO;

import java.sql.SQLException;

public class AccessoServiceImpl implements AccessoService {

    private AmministratoreDAO amministratoreDAO = new AmministratoreDAO();
    @Override
    public amministratoreBean login(final String email, final String password) throws SQLException {
        return amministratoreDAO.login(email, password);
    }
}
