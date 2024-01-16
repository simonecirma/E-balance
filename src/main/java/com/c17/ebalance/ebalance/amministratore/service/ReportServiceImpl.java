package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.ReportDAO;
import com.c17.ebalance.ebalance.model.DAO.ReportDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ReportBean;

import java.sql.SQLException;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private ReportDAO reportDAO = new ReportDAOImpl();

    @Override
    public List<ReportBean> visualizzaReport() throws SQLException {
        return reportDAO.visualizzaReport();
    }
}
