package com.c17.ebalance.ebalance.dati.controller;

import com.c17.ebalance.ebalance.IA.controller.IAController;
import com.c17.ebalance.ebalance.IA.service.IAService;
import com.c17.ebalance.ebalance.IA.service.IAServiceImpl;
import com.c17.ebalance.ebalance.amministratore.service.AmministratoreService;
import com.c17.ebalance.ebalance.amministratore.service.AmministratoreServiceImpl;
import com.c17.ebalance.ebalance.amministratore.service.ReportService;
import com.c17.ebalance.ebalance.amministratore.service.ReportServiceImpl;
import com.c17.ebalance.ebalance.dati.service.*;
import com.c17.ebalance.ebalance.model.entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.IOException;

@WebServlet(name = "DatiController", value = "/DatiController")
public class DatiController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IAController iaController = new IAController();
    private IAService iaService = new IAServiceImpl();
    private BatteriaService batteriaService = new BatteriaServiceImpl();
    private ConsumoService consumoService = new ConsumoServiceImpl();
    private ProduzioneService produzioneService = new ProduzioneServiceImpl();
    private ReportService reportService = new ReportServiceImpl();
    private AmministratoreService amministratoreService = new AmministratoreServiceImpl();

    @Override
    public void init() throws ServletException {
        new Thread(this::eseguiSimulazioneConsumi).start();
        new Thread(this::eseguiSimulazioneProduzione).start();
        new Thread(this::eseguiSimulazioneProduzioneSEN).start();
    }

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("generaDashboard")) {
                    //List<BatteriaBean> batteria = batteriaService.visualizzaBatteria();
                    //request.setAttribute("batteria", batteria);
                    float percentualeBatterie = batteriaService.ottieniPercetualeBatteria();
                    request.setAttribute("percentualeBatterie", percentualeBatterie);
                    //List<ConsumoEdificioBean> consumoEdificio = consumoService.visualizzaConsumo();
                    //request.setAttribute("consumoEdificio", consumoEdificio);
                    float consumoEdifici = consumoService.ottieniConsumiEdifici();
                    request.setAttribute("consumoEdifici", consumoEdifici);
                    List<ArchivioConsumoBean> archivioConsumo = consumoService.visualizzaStoricoConsumi();
                    request.setAttribute("archivioConsumo", archivioConsumo);
                    //List<SorgenteBean> sorgente = produzioneService.visualizzaProduzioneSorgente();
                    //request.setAttribute("sorgente", sorgente);
                    String sommaProduzione[][] = produzioneService.ottieniProduzione();
                    request.setAttribute("sommaProduzione", sommaProduzione);
                    List<ParametriIABean> parametriIA = iaController.ottieniParametri();
                    request.setAttribute("parametriIA", parametriIA);
                    List<InteragisceBean> interazioneParametri = iaController.ottieniInterazioneParametri();
                    request.setAttribute("interazioneParametri", interazioneParametri);
                    List<InteragisceBean> parametriAttivi = iaController.ottieniParametriAttivi();
                    request.setAttribute("parametriAttivi", parametriAttivi);
                    List<TipoSorgenteBean> tipoSorgente = produzioneService.ottieniTipoSorgente();
                    request.setAttribute("tipoSorgente", tipoSorgente);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("selezionaPiano")) {
                    HttpSession session = request.getSession(true);
                    String piano = request.getParameter("piano");
                    if (piano.equalsIgnoreCase("SalvaguardiaAmbientale")) {
                        iaService.aggiornaPianoAttivo(piano, (int) session.getAttribute("idAmministratore"));
                    } else if (piano.equalsIgnoreCase("EfficienzaEconomica")) {
                        iaService.aggiornaPianoAttivo(piano, (int) session.getAttribute("idAmministratore"));
                    } else if (piano.equalsIgnoreCase("Personalizzato")) {
                        String preferenzaSorgente = request.getParameter("preferenzaSorgente");
                        int percentualeUtilizzoPannelli = Integer.parseInt(request.getParameter("Pannello fotovoltaico"));
                        int percentualeUtilizzoSEN = Integer.parseInt(request.getParameter("Servizio Elettrico Nazionale"));
                        String sortableListData = request.getParameter("sortableListData");
                        iaService.aggiornaPianoPersonalizzato(preferenzaSorgente, percentualeUtilizzoPannelli, percentualeUtilizzoSEN, sortableListData);
                        System.out.println(session.getAttribute("idAmministratore"));
                        if (!iaService.aggiornaPianoAttivo(piano, (int) session.getAttribute("idAmministratore"))) {
                            request.setAttribute("result", "errore aggiornamento piano");
                        }
                    }
                    response.sendRedirect("DatiController?action=generaDashboard");

                }
                if (action.equalsIgnoreCase("vediReport")) {
                    List<ReportBean> report = reportService.visualizzaReport();
                    request.setAttribute("report", report);
                    List<AmministratoreBean> amm = new ArrayList<AmministratoreBean>();
                    AmministratoreBean bean = new AmministratoreBean();
                    for (ReportBean rep : report) {
                        int i = rep.getIdAmministratore();
                        bean = amministratoreService.getById(i);
                        amm.add(bean);
                    }

                    request.setAttribute("amm", amm);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
                if (action.equalsIgnoreCase("bilancioTotale")) {
                    ReportBean report = reportService.generaReport(request, response);
                    reportService.aggiungiReport(report);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/report.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServletException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    protected void eseguiSimulazioneConsumi() {
        while (true) {
            try {
                consumoService.simulaConsumo();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void eseguiSimulazioneProduzione() {
        while (true) {
            try {
                produzioneService.simulaProduzione();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void eseguiSimulazioneProduzioneSEN() {
        while (true) {
            try {
                produzioneService.simulaProduzioneSEN();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void destroy() {
    }
}
