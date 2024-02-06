package com.c17.ebalance.ebalance.contratto.controller;


import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.contratto.service.ContrattoServiceImpl;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * La classe {@code ContrattoController} è un servlet che gestisce le richieste relative ai contratti nel sistema eBalance.
 * Utilizza un'istanza di {@code ContrattoService} per eseguire operazioni di visualizzazione, aggiornamento e aggiunta di contratti.
 * Risponde alle richieste HTTP GET e POST provenienti dalla view e interagisce con la logica di business del sistema.
 * Inoltre, fornisce metodi per aggiornare e aggiungere contratti, oltre a visualizzare l'elenco degli storici contratti e il contratto attuale.
 */
@WebServlet(name = "ContrattoController", value = "/ContrattoController")
public class ContrattoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Istanza di ServletContext
     */
    private ServletContext servletContext;

    /**
     * Service per la gestione dei contratti. L'istanza {@code contrattoService} fornisce metodi
     * per operare su contratti nel sistema.
     */
    public ContrattoService contrattoService = new ContrattoServiceImpl();

    /**
     * Costruttore vuoto della classe.
     */
    public ContrattoController() {
    }

    /**
     * Costruttore della classe che accetta un'istanza di {@code ContrattoService} e un oggetto {@code ServletContext}.
     *
     * @param contrattoService L'istanza di {@code ContrattoService} da utilizzare.
     * @param servletContext   L'oggetto {@code ServletContext} da utilizzare.
     */
    public ContrattoController(ContrattoService contrattoService, ServletContext servletContext) {
        this.contrattoService = contrattoService;
        this.servletContext = servletContext;
    }

    /**
     * Gestisce le richieste HTTP GET relative ai contratti.
     *
     * @param request  L'oggetto {@code HttpServletRequest} rappresentante la richiesta HTTP.
     * @param response L'oggetto {@code HttpServletResponse} rappresentante la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("aggiornaContratto")) {
                    aggiornaContratto(request, response);
                    return;

                }
                if (action.equalsIgnoreCase("aggiungiContratto")) {
                    aggiungiContratto(request, response);
                    return;
                }
                List<ContrattoBean> contratti = contrattoService.visualizzaStoricoContratti();
                request.setAttribute("contratti", contratti);
                ContrattoBean contratto = contrattoService.visualizzaContratto();
                request.setAttribute("contratto", contratto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            } else {
                List<ContrattoBean> contratti = contrattoService.visualizzaStoricoContratti();
                request.setAttribute("contratti", contratti);
                ContrattoBean contratto = contrattoService.visualizzaContratto();
                request.setAttribute("contratto", contratto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gestisce le richieste HTTP POST relative ai contratti.
     *
     * @param request  L'oggetto {@code HttpServletRequest} rappresentante la richiesta HTTP.
     * @param response L'oggetto {@code HttpServletResponse} rappresentante la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    /**
     * Aggiorna un contratto nel sistema sulla base dei parametri ricevuti dalla richiesta HTTP.
     *
     * @param request  L'oggetto {@code HttpServletRequest} rappresentante la richiesta HTTP.
     * @param response L'oggetto {@code HttpServletResponse} rappresentante la risposta HTTP.
     * @throws ServletException in caso di errori durante la gestione della richiesta.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     * @throws SQLException     in caso di errori di accesso al database.
     */
    public void aggiornaContratto(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("idContratto"));
        String ente = request.getParameter("nomeEnte");
        float consumo = Float.parseFloat(request.getParameter("consumoMedioAnnuale"));
        float costo = Float.parseFloat(request.getParameter("costoMedioUnitario"));
        Date data = Date.valueOf(request.getParameter("dataSottoscrizione"));
        int durata = Integer.parseInt(request.getParameter("durata"));
        float prezzo = Float.parseFloat(request.getParameter("prezzoVendita"));
        int admin = Integer.parseInt(request.getParameter("idAmministratore"));


        ContrattoBean bean = new ContrattoBean();

        bean.setIdContratto(id);
        bean.setNomeEnte(ente);
        bean.setConsumoMedioAnnuale(consumo);
        bean.setCostoMedioUnitario(costo);
        bean.setDataSottoscrizione(data);
        bean.setDurata(durata);
        bean.setPrezzoVendita(prezzo);
        bean.setIdAmministatore(admin);
        try {
            contrattoService.aggiornaContratto(bean);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        List<ContrattoBean> contratti = contrattoService.visualizzaStoricoContratti();
        request.setAttribute("contratti", contratti);
        ContrattoBean contratto = contrattoService.visualizzaContratto();
        request.setAttribute("contratto", contratto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/contratto.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Aggiunge un nuovo contratto al sistema sulla base dei parametri ricevuti dalla richiesta HTTP.
     *
     * @param request  L'oggetto {@code HttpServletRequest} rappresentante la richiesta HTTP.
     * @param response L'oggetto {@code HttpServletResponse} rappresentante la risposta HTTP.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     * @throws ServletException in caso di errori durante la gestione della richiesta.
     * @throws SQLException     in caso di errori di accesso al database.
     */
    public void aggiungiContratto(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException, SQLException {
        ContrattoBean bean = new ContrattoBean();
        bean.setNomeEnte(request.getParameter("nomeEnte"));
        bean.setConsumoMedioAnnuale(Float.parseFloat(request.getParameter("consumoMedioAnnuale")));
        bean.setCostoMedioUnitario(Float.parseFloat(request.getParameter("costoMedioUnitario")));
        bean.setDataSottoscrizione(Date.valueOf(request.getParameter("dataSottoscrizione")));
        bean.setDurata(Integer.parseInt(request.getParameter("durata")));
        bean.setPrezzoVendita(Float.parseFloat(request.getParameter("prezzoVendita")));
        bean.setIdAmministatore(Integer.parseInt(request.getParameter("idAmministratore")));
        try {
            contrattoService.aggiungiContratto(bean);
            if (contrattoService.verificaPrimoContratto()) {
                response.sendRedirect("DatiController?action=generaDashboard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        List<ContrattoBean> contratti = contrattoService.visualizzaStoricoContratti();
        request.setAttribute("contratti", contratti);
        ContrattoBean contratto = contrattoService.visualizzaContratto();
        request.setAttribute("contratto", contratto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/contratto.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Distrugge l'oggetto servlet.
     */
    public void destroy() {
    }
}
