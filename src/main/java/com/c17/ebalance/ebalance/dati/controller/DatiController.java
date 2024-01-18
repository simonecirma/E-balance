package com.c17.ebalance.ebalance.dati.controller;

import com.c17.ebalance.ebalance.dati.service.ConsumoService;
import com.c17.ebalance.ebalance.dati.service.ConsumoServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DatiController", value = "/DatiController")
public class DatiController extends HttpServlet {

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
