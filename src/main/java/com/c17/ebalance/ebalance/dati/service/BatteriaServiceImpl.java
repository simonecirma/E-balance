package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.BatteriaDAO;
import com.c17.ebalance.ebalance.model.DAO.BatteriaDAOImpl;
import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

public class BatteriaServiceImpl implements BatteriaService {
    private BatteriaDAO batteriaDAO = new BatteriaDAOImpl();

    @Override
    public List<BatteriaBean> visualizzaBatteria() throws SQLException {
        return batteriaDAO.visualizzaBatteria();
    }
}
