package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public interface BilancioService {
    ReportBean generaReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException;
}
