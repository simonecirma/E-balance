package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.accesso.controller.AccessoController;
import com.c17.ebalance.ebalance.amministratore.service.*;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
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


/**
 * La classe {@code AmministratoreController} è una servlet che gestisce le operazioni
 * legate agli amministratori nel sistema eBalance, come l'aggiornamento, l'aggiunta e la visualizzazione dei report.
 * Interagisce con i servizi di amministrazione e di report per fornire funzionalità avanzate agli amministratori.
 */
@WebServlet(name = "AmministratoreController", value = "/AmministratoreController")
public class AmministratoreController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * Service per la gestione degli amministratori. L'istanza {@code amministratoreService} fornisce metodi
     * per operare sugli amministratori del sistema.
     */
    private AmministratoreService amministratoreService = new AmministratoreServiceImpl();
    /**
     * Service per la gestione dei report. L'istanza {@code reportDAO} fornisce metodi
     * per operare sui report nel sistema.
     */
    private ReportService reportService = new ReportServiceImpl();

    /**
     * Service per la gestione degli accessi. L'istanza {@code accessoController} fornisce metodi
     * per effettuare gli accessi al sistema.
     */
    private AccessoController accessoController = new AccessoController();

    /**
     * Service per la gestione della vendita. L'istanza {@code venditaService} fornisce metodi
     * per effettuare la vendita dell'energia.
     */
    private VenditaService venditaService = new VenditaServiceImpl();
    private ServletContext servletContext;

    /**
     * Costruttore predefinito per AmministratoreController.
     */
    public AmministratoreController() {
    }

    /**
     * Costruttore parametrizzato per AmministratoreController.
     *
     * @param amministratoreService Il servizio per le operazioni sugli amministratori.
     * @param reportService        Il servizio per le operazioni sui report.
     * @param venditaService       Il servizio per le operazioni di vendita.
     * @param servletContext       Il contesto della servlet associato a questo controller.
     */
    public AmministratoreController(AmministratoreService amministratoreService, ReportService reportService,
                                    VenditaService venditaService, ServletContext servletContext) {
        this.amministratoreService = amministratoreService;
        this.reportService = reportService;
        this.venditaService = venditaService;
        this.servletContext = servletContext;
    }

    /**
     * Gestisce le richieste HTTP di tipo GET relative alle operazioni degli amministratori e dei report.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("verificaSuperAdmin")) {
                    if (amministratoreService.verificaSuperAdmin()) {
                        request.setAttribute("result", "Risulta già un sistema configurato, accedi!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/amministratori.jsp");
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
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/amministratori.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("rimuoviAmministratore")) {
                    int idAmministratore = Integer.parseInt(request.getParameter("idAmministratore"));
                    amministratoreService.rimuoviAmministratore(idAmministratore);
                    response.sendRedirect("AmministratoreController?action=gestisciAmministratori");
                }
                if (action.equalsIgnoreCase("vediReport")) {
                    visualizzazioneReport(request, response);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("vendita")) {
                    String idAmministratoreParam = request.getParameter("idAmministratore");
                    int idAmministratore = idAmministratoreParam != null ? Integer.parseInt(idAmministratoreParam) : 0;
                    venditaService.effettuaVendita(idAmministratore);
                    response.sendRedirect("DatiController?action=generaDashboard");
                }
                if (action.equalsIgnoreCase("generaReport")) {
                    HttpSession session = request.getSession(false);
                    String servletPath = null;
                    if (request != null && request.getServletContext() != null) {
                        servletPath = request.getServletContext().getRealPath("");
                    }

                    Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
                    Date dataFine = Date.valueOf(request.getParameter("dataFine"));
                    ReportBean report = reportService.generaReport(dataInizio, dataFine, servletPath, session);
                    reportService.aggiungiReport(report);
                    visualizzazioneReport(request, response);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/profilo.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ServletException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gestisce le richieste HTTP di tipo POST relative alle operazioni degli amministratori e dei report.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }


    /**
     * Gestisce l'aggiornamento dei dati di un amministratore e reindirizza alla pagina del profilo.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     * @throws ServletException in caso di errori durante la gestione della richiesta.
     * @throws ParseException  in caso di errori durante il parsing della data di nascita.
     */
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/profilo.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Gestisce l'aggiunta di un nuovo amministratore e reindirizza alla pagina del profilo o della gestione amministratori.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     * @throws ServletException in caso di errori durante la gestione della richiesta.
     * @throws SQLException     in caso di errori durante le operazioni di accesso al database.
     */
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
        if (session != null) {
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
        if (session != null) {
            response.sendRedirect("AmministratoreController?action=gestisciAmministratori");
        } else {
            AmministratoreBean admin = accessoController.login(amministratore.getEmail(), amministratore.getPassword(), session);
            if (admin != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("AmministratoreController?action=verificaSuperAdmin");
            }
        }

    }

    /**
     * Gestisce la visualizzazione dei report e imposta gli attributi necessari per la visualizzazione nella pagina JSP.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     */
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

    /**
     * Distrugge la servlet quando viene chiamato.
     */
    public void destroy() {
    }
}
