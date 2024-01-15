package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.amministratore.service.*;
import com.c17.ebalance.ebalance.model.entity.amministratoreBean;
import com.c17.ebalance.ebalance.model.entity.reportBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "amministratoreController", value = "/amministratoreController")
public class amministratoreController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private amministratoreService amministratoreService = new amministratoreServiceImpl();
    private reportService reportService = new reportServiceImpl();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");

        try {
            if(action != null) {
                if (action.equalsIgnoreCase("aggiornaAmministratore")) {
                    aggiornaAmministratore(request, response);
                }
                if (action.equalsIgnoreCase("aggiungiAmministratore")) {
                    aggiungiAmministratore(request, response);
                }
                if (action.equalsIgnoreCase("gestisciAmministratori")) {
                    List<amministratoreBean> amministratori = amministratoreService.visualizzaAmministratori();
                    request.setAttribute("amministratori", amministratori);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/amministratori.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("vediReport")) {
                    List<reportBean> report = reportService.visualizzaReport();
                    request.setAttribute("report", report);
                    List<amministratoreBean> amm = new ArrayList<amministratoreBean>();
                    amministratoreBean bean = new amministratoreBean();
                    for (reportBean rep : report) {
                        int i = rep.getIdAmministratore();
                        bean = amministratoreService.getById(i);
                        amm.add(bean);
                    }
                    request.setAttribute("amm", amm);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
                }
            else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profilo.jsp");
                dispatcher.forward(request, response);
            }


        } catch (SQLException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void aggiornaAmministratore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Date dataNascita = Date.valueOf(request.getParameter("dataNascita"));
        int idAmministratore = Integer.parseInt(request.getParameter("idAmministratore"));
        boolean flagTipo = Boolean.parseBoolean(request.getParameter("flagTipo"));

        amministratoreBean amministratore = new amministratoreBean();

        amministratore.setNome(nome);
        amministratore.setCognome(cognome);
        amministratore.setEmail(email);
        amministratore.setPassword(password);
        amministratore.setDataNascita(dataNascita);
        amministratore.setIdAmministratore(idAmministratore);
        amministratore.setFlagTipo(flagTipo);

        try {
            amministratoreService.aggiornaAmministratore(amministratore);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("nome", nome);
        session.setAttribute("cognome", cognome);
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        session.setAttribute("dataNascita", dataNascita);
        session.setAttribute("idAmministratore", idAmministratore);
        session.setAttribute("flagTipo", flagTipo);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profilo.jsp");
        dispatcher.forward(request, response);
    }

    private void aggiungiAmministratore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        amministratoreBean amministratore = new amministratoreBean();

        amministratore.setNome(request.getParameter("nome"));
        amministratore.setCognome(request.getParameter("cognome"));
        amministratore.setEmail(request.getParameter("email"));
        amministratore.setPassword(request.getParameter("password"));
        amministratore.setDataNascita(Date.valueOf(request.getParameter("dataNascita")));
        HttpSession session = request.getSession(true);
        if(session.getAttribute("email")!=null)
        {
            amministratore.setFlagTipo(false);
        }
        else
        {
            amministratore.setFlagTipo(true);
        }


        try {
            amministratoreService.aggiungiAmministratore(amministratore);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        response.sendRedirect("amministratoreController?action=gestisciAmministratori");


    }

    public void destroy() {
    }
}