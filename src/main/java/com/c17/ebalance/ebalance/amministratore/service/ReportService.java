package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    List<ReportBean> visualizzaReport() throws SQLException;

    int ultimoReport() throws SQLException;

    void aggiungiReport(ReportBean bean) throws SQLException;

    ReportBean generaReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException;
}
