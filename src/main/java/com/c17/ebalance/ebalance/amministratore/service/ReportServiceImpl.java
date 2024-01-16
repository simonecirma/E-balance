package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.ReportDAO;
import com.c17.ebalance.ebalance.model.entity.reportBean;

import java.sql.SQLException;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private ReportDAO reportDAO = new ReportDAO();

    @Override
    public List<reportBean> visualizzaReport() throws SQLException {
        return reportDAO.visualizzaReport();
    }
}
