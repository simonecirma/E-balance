package com.c17.ebalance.ebalance.dati.controller;

import com.c17.ebalance.ebalance.IA.controller.IAController;
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
    private BatteriaService batteriaService = new BatteriaServiceImpl();
    private ConsumoService consumoService = new ConsumoServiceImpl();
    private ProduzioneService produzioneService = new ProduzioneServiceImpl();
    private VenditaService venditaService = new VenditaServiceImpl();
    private ReportService reportService = new ReportServiceImpl();
    private AmministratoreService amministratoreService = new AmministratoreServiceImpl();

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
                    //List<SorgenteBean> sorgente = produzioneService.visualizzaProduzioneSorgente();
                    //request.setAttribute("sorgente", sorgente);
                    String sommaProduzione[][] = produzioneService.ottieniProduzione();
                    request.setAttribute("sommaProduzione", sommaProduzione);
                    /*List<ParametriIABean> parametriIA = iaController.ottieniParametri();
                    request.setAttribute("parametriIA", parametriIA);
                    List<InteragisceBean> interazioneParametri = iaController.ottieniInterazioneParametri();
                    request.setAttribute("interazioneParametri", interazioneParametri);*/
                    List<InteragisceBean> parametriAttivi = iaController.ottieniParametriAttivi();
                    request.setAttribute("parametriAttivi", parametriAttivi);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
                    dispatcher.forward(request, response);
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
                if (action.equalsIgnoreCase("energiaVenduta_ricavoTotale")) {
                    float energia = getEnergia(request, response);
                    request.setAttribute("energia", energia);
                    float ricavo = getRicavo(request, response);
                    request.setAttribute("ricavo", ricavo);

                    String servletPath = request.getServletContext().getRealPath("");
                    String totalPath = servletPath + File.separator + "output"  + ".pdf";

                    File pdfFile = PdfGenerator.generatePdf(energia, ricavo, totalPath);

                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=output.pdf");

                    try (OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(pdfFile)) {
                        byte[] buffer = new byte[4096];
                        int length;
                        while ((length = in.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                    }
                    pdfFile.delete();
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

    public float getEnergia(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Date dataInizio = java.sql.Date.valueOf(request.getParameter("dataInizio"));
        Date dataFine = java.sql.Date.valueOf(request.getParameter("dataFine"));
        float energia;
        energia=venditaService.getEnergiaVendutaPerData((java.sql.Date) dataInizio, (java.sql.Date) dataFine);
        return energia;
    }

    public float getRicavo(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Date dataInizio = java.sql.Date.valueOf(request.getParameter("dataInizio"));
        Date dataFine = java.sql.Date.valueOf(request.getParameter("dataFine"));
        float ricavo;
        ricavo=venditaService.getRicavoTotalePerData((java.sql.Date) dataInizio, (java.sql.Date) dataFine);
        return ricavo;
    }

    public void destroy() {
    }
}
