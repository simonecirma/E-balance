package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.accesso.controller.AccessoController;
import com.c17.ebalance.ebalance.amministratore.service.*;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AmministratoreController", value = "/AmministratoreController")
public class AmministratoreController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AmministratoreService amministratoreService = new AmministratoreServiceImpl();
    private ReportService reportService = new ReportServiceImpl();

    private AccessoController accessoController = new AccessoController();

    private VenditaService venditaService = new VenditaServiceImpl();
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("verificaSuperAdmin")) {
                    if (amministratoreService.verificaSuperAdmin()) {
                        request.setAttribute("result", "Risulta già un sistema configurato, accedi!");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/amministratori.jsp");
                        dispatcher.forward(request, response);
                    }
                }
                if (action.equalsIgnoreCase("verificaPresenzaEmail")) {
                    String email = request.getParameter("email");
                    boolean flagPresenza = amministratoreService.verificaPresenzaEmail(email);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("{\"flagPresenza\": " + flagPresenza + "}");
                    out.flush();
                }
                if (action.equalsIgnoreCase("aggiornaAmministratore")) {
                    aggiornaAmministratore(request, response);
                }
                if (action.equalsIgnoreCase("aggiungiAmministratore")) {
                    aggiungiAmministratore(request, response);
                }
                if (action.equalsIgnoreCase("gestisciAmministratori")) {
                    List<AmministratoreBean> amministratori = amministratoreService.visualizzaAmministratori();
                    request.setAttribute("amministratori", amministratori);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/amministratori.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("rimuoviAmministratore")) {
                    int idAmministratore = Integer.parseInt(request.getParameter("idAmministratore"));
                    amministratoreService.rimuoviAmministratore(idAmministratore);
                    response.sendRedirect("AmministratoreController?action=gestisciAmministratori");
                }
                if (action.equalsIgnoreCase("vediReport")) {
                    visualizzazioneReport(request, response);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("vendita")) {
                    int idAmministratore = Integer.parseInt(request.getParameter("idAmministratore"));
                    venditaService.effettuaVendita(idAmministratore);
                    response.sendRedirect("DatiController?action=generaDashboard");
                }
                if (action.equalsIgnoreCase("generaReport")) {
                    ReportBean report = reportService.generaReport(request, response);
                    reportService.aggiungiReport(report);
                    visualizzazioneReport(request, response);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profilo.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ServletException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void aggiornaAmministratore(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException, ParseException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dataNascitaString = request.getParameter("dataNascita");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataNascita = new Date(dateFormat.parse(dataNascitaString).getTime());

        int idAmministratore = Integer.parseInt(request.getParameter("idAmministratore"));
        boolean flagTipo = Boolean.parseBoolean(request.getParameter("flagTipo"));

        AmministratoreBean amministratore = new AmministratoreBean();

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

    public void aggiungiAmministratore(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException, SQLException {

        AmministratoreBean amministratore = new AmministratoreBean();

        amministratore.setNome(request.getParameter("nome"));
        amministratore.setCognome(request.getParameter("cognome"));
        amministratore.setEmail(request.getParameter("email"));
        amministratore.setPassword(request.getParameter("password"));
        String dataNascitaString = request.getParameter("dataNascita");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dataNascita = new Date(dateFormat.parse(dataNascitaString).getTime());
            amministratore.setDataNascita(dataNascita);
        } catch (ParseException e) {
            return;
        }
        HttpSession session = request.getSession(true);
        if (session.getAttribute("email") != null) {
            amministratore.setFlagTipo(false);
        } else {
            amministratore.setFlagTipo(true);
        }

        try {
            amministratoreService.aggiungiAmministratore(amministratore);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        if (session.getAttribute("email") != null) {
            response.sendRedirect("AmministratoreController?action=gestisciAmministratori");
        } else {
            AmministratoreBean admin = accessoController.login(amministratore.getEmail(), amministratore.getPassword(), session);
            if (admin != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("AmministratoreController?action=verificaSuperAdmin");
            }
        }

    }

    private void visualizzazioneReport(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            List<ReportBean> listReport = reportService.visualizzaReport();
            request.setAttribute("listReport", listReport);
            List<AmministratoreBean> amministratori = new ArrayList<AmministratoreBean>();
            AmministratoreBean bean = new AmministratoreBean();
            for (ReportBean rep : listReport) {
                int i = rep.getIdAmministratore();
                bean = amministratoreService.getById(i);
                amministratori.add(bean);
            }
            request.setAttribute("amministratori", amministratori);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void destroy() {
    }
}
