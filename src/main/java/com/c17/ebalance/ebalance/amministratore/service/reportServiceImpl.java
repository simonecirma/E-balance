package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.*;
import com.c17.ebalance.ebalance.model.entity.reportBean;

import java.sql.SQLException;
import java.util.List;

public class reportServiceImpl implements reportService{
    private reportDAO reportDAO = new reportDAO();

    @Override
    public List<reportBean> visualizzaReport() throws SQLException {
        return reportDAO.visualizzaReport();
    }
}
