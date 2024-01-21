package com.c17.ebalance.ebalance.dati.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public interface BilancioService {
    void generaReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException;
}
