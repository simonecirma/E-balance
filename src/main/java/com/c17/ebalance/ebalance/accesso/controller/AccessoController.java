package com.c17.ebalance.ebalance.accesso.controller;

import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.accesso.service.AccessoServiceImpl;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * La classe {@code AccessoController} è una servlet che gestisce l'autenticazione
 * dell'amministratore per l'applicazione eBalance. Interagisce con il servizio {@code AccessoService}
 * per eseguire operazioni di login e gestisce le sessioni degli amministratori.
 */
@WebServlet(name = "AccessoController", value = "/AccessoController")
public class AccessoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Service per la gestione degli accessi. L'istanza {@code accessoService} fornisce metodi
     * per effettuare l'accesso al sistema.
     */
    private AccessoService accessoService = new AccessoServiceImpl();
    private ServletContext servletContext;

    /**
     * Costruttore predefinito per AccessoController.
     */
    public AccessoController() {
    }

    /**
     * Costruttore parametrizzato per AccessoController.
     *
     * @param accessoService  L'implementazione di AccessoService da utilizzare.
     * @param servletContext Il contesto della servlet associato a questo controller.
     */
    public AccessoController(AccessoService accessoService, ServletContext servletContext) {
        this.accessoService = accessoService;
        this.servletContext = servletContext;
    }

    /**
     * Gestisce le richieste HTTP di tipo GET per l'autenticazione dell'amministratore.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws IOException in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        AmministratoreBean amministratore = new AmministratoreBean();
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        HttpSession session = request.getSession(true);
        try {
            amministratore = login(email, pass, session);
            if (amministratore != null) {
                response.sendRedirect("DatiController?action=generaDashboard");
            } else {
                request.setAttribute("result", "Credenziali sbagliate riprova!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ServletException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Gestisce le richieste HTTP di tipo POST per l'autenticazione dell'amministratore.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws IOException in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    /**
     * Esegue l'operazione di login e restituisce l'oggetto AmministratoreBean se le credenziali sono valide.
     *
     * @param email   L'indirizzo email dell'amministratore.
     * @param pass    La password dell'amministratore.
     * @param session La sessione HTTP associata alla richiesta.
     * @return L'oggetto AmministratoreBean dell'amministratore autenticato, o null se le credenziali sono sbagliate.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public AmministratoreBean login(final String email, final String pass, final HttpSession session) throws SQLException {
        AmministratoreBean admin = new AmministratoreBean();
        admin = accessoService.login(email, pass);
        if (admin != null) {
            session.setAttribute("email", admin.getEmail());
            session.setAttribute("password", admin.getPassword());
            session.setAttribute("nome", admin.getNome());
            session.setAttribute("cognome", admin.getCognome());
            session.setAttribute("flagTipo", admin.getFlagTipo());
            session.setAttribute("idAmministratore", admin.getIdAmministratore());
            session.setAttribute("dataNascita", admin.getDataNascita());
        }
        return admin;
    }

    /**
     * Distrugge la servlet quando viene chiamato.
     */
    public void destroy() {
    }
}
