package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.amministratore.service.*;
import com.c17.ebalance.ebalance.model.entity.amministratoreBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "amministratoreController", value = "/amministratoreController")
public class amministratoreController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private amministratoreService amministratoreService = new amministratoreServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            List<amministratoreBean> amministratori = amministratoreService.visualizzaAmministratori();

            request.setAttribute("amministratori", amministratori);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}