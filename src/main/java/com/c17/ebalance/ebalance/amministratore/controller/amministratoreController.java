package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.amministratore.service.*;
import com.c17.ebalance.ebalance.model.entity.amministratoreBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profilo.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Aggiornamento amministratore
        String action = request.getParameter("action");

        if (action != null && action.equals("aggiornaAmministratore")) {
            aggiornaAmministratore(request, response);
        } else {
            response.sendRedirect("profilo.jsp");
        }
    }

    private void aggiornaAmministratore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        amministratoreBean amministratore = new amministratoreBean();
        amministratore.setNome(nome);
        amministratore.setCognome(cognome);
        amministratore.setEmail(email);
        amministratore.setPassword(password);

        try {
            amministratoreService.aggiornaAmministratore(amministratore);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("nome", nome);
        session.setAttribute("cognome", cognome);
        session.setAttribute("email", email);
        session.setAttribute("password", password);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profilo.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}