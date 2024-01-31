package com.c17.ebalance.ebalance.dati.controller;

import com.c17.ebalance.ebalance.IA.controller.IAController;
import com.c17.ebalance.ebalance.IA.service.IAService;
import com.c17.ebalance.ebalance.IA.service.IAServiceImpl;
import com.c17.ebalance.ebalance.dati.service.BatteriaService;
import com.c17.ebalance.ebalance.dati.service.BatteriaServiceImpl;
import com.c17.ebalance.ebalance.dati.service.ConsumoService;
import com.c17.ebalance.ebalance.dati.service.ConsumoServiceImpl;
import com.c17.ebalance.ebalance.dati.service.MeteoService;
import com.c17.ebalance.ebalance.dati.service.MeteoServiceImpl;
import com.c17.ebalance.ebalance.dati.service.ProduzioneService;
import com.c17.ebalance.ebalance.dati.service.ProduzioneServiceImpl;
import com.c17.ebalance.ebalance.dati.service.SimulazioneService;
import com.c17.ebalance.ebalance.dati.service.SimulazioneServiceImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoAttualeBean;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.MeteoBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
import com.c17.ebalance.ebalance.model.entity.PercentualeBatteriaBean;
import com.c17.ebalance.ebalance.model.entity.ProduzioneAttualeBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;
import com.c17.ebalance.ebalance.utility.Observer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import java.io.IOException;


@WebServlet(name = "DatiController", value = "/DatiController")
public class DatiController extends HttpServlet implements Observer {
    private static final long serialVersionUID = 1L;
    private IAController iaController = new IAController();
    private IAService iaService = new IAServiceImpl();
    private BatteriaService batteriaService = new BatteriaServiceImpl();
    private ConsumoService consumoService = new ConsumoServiceImpl();
    private ProduzioneService produzioneService = new ProduzioneServiceImpl();
    private SimulazioneService simulazioneService = new SimulazioneServiceImpl();
    private MeteoService meteoService = new MeteoServiceImpl();

    boolean percentualeBatteriaUpdate = false;

    boolean produzioneAttualeUpdate = false;

    boolean consumoAttualeUpdate = false;

    boolean archivioConsumiUpdate = false;

    boolean meteoUpdate = false;

    boolean parametriAttiviUpdate = false;

    float[] consumoEdifici = new float[]{633.02f, 633.02f, 633.02f, 633.02f,
            633.02f, 633.02f, 633.02f, 633.02f, 633.02f, 633.02f};

    float[] produzioneSorgente = new float[]{324.02f, 324.02f, 324.02f, 324.02f,
            324.02f, 324.02f, 324.02f, 324.02f, 324.02f, 324.02f};

    ArchivioConsumoBean archivioConsumi;

    ConsumoAttualeBean consumoAttuale;

    PercentualeBatteriaBean percentualeBatteria;

    ProduzioneAttualeBean produzioneAttuale;

    MeteoBean meteo;

    InteragisceBean parametriAttivi;

    @Override
    public void init() throws ServletException {
        archivioConsumi = new ArchivioConsumoBean();
        consumoAttuale = new ConsumoAttualeBean();
        percentualeBatteria = new PercentualeBatteriaBean();
        produzioneAttuale = new ProduzioneAttualeBean();
        meteo = new MeteoBean();

        parametriAttivi = new InteragisceBean();
        archivioConsumi.addObserver(this);
        consumoAttuale.addObserver(this);
        percentualeBatteria.addObserver(this);
        produzioneAttuale.addObserver(this);
        meteo.addObserver(this);
        parametriAttivi.addObserver(this);

        new Thread(this::simulazioneEnergia).start();
        new Thread(this::simulazionePrevisioni).start();

    }

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("dashboardObserver")) {
                    try {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"percentualeBatteriaUpdate\": \"" + percentualeBatteriaUpdate + "\", "
                                + "\"produzioneAttualeUpdate\": \"" + produzioneAttualeUpdate + "\", "
                                + "\"consumoAttualeUpdate\": \"" + consumoAttualeUpdate + "\", "
                                + "\"archivioConsumiUpdate\": \"" + archivioConsumiUpdate + "\", "
                                + "\"meteoUpdate\": \"" + meteoUpdate + "\", "
                                + "\"parametriAttiviUpdate\": \"" + parametriAttiviUpdate + "\"}");
                        percentualeBatteriaUpdate = false;
                        produzioneAttualeUpdate = false;
                        consumoAttualeUpdate = false;
                        archivioConsumiUpdate = false;
                        meteoUpdate = false;
                        parametriAttiviUpdate = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (action.equalsIgnoreCase("generaDashboard")) {
                    consumoEdifici = consumoService.ottieniConsumiEdifici(consumoEdifici);
                    request.setAttribute("consumoEdifici", consumoEdifici);

                    float percentualeBatterie = batteriaService.ottieniPercetualeBatteria();
                    request.setAttribute("percentualeBatterie", percentualeBatterie);

                    List<ArchivioConsumoBean> archivioConsumo = consumoService.visualizzaStoricoConsumi();
                    request.setAttribute("archivioConsumo", archivioConsumo);

                    List<MeteoBean> condizioniGiornaliere = meteoService.getCondizioniMeteo();
                    request.setAttribute("condizioniMeteo", condizioniGiornaliere);
                    List<MeteoBean> condizioniSettimanali = meteoService.getCondizioniSettimanali();
                    request.setAttribute("condizioniSettimanali", condizioniSettimanali);

                    produzioneSorgente = produzioneService.ottieniProduzioneProdotta(produzioneSorgente);
                    request.setAttribute("produzioneSorgente", produzioneSorgente);
                    float produzioneSEN = produzioneService.ottieniProduzioneSEN();
                    request.setAttribute("produzioneSEN", produzioneSEN);

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
                        if (!iaService.aggiornaPianoAttivo(piano, (int) session.getAttribute("idAmministratore"))) {
                            request.setAttribute("result", "errore aggiornamento piano");
                        }
                    }
                    response.sendRedirect("DatiController?action=generaDashboard");

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

    protected void simulazioneEnergia() {
        while (true) {
            try {
                simulazioneService.simulazioneEnergia();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void simulazionePrevisioni() {
        try {
            simulazioneService.insertPrevisioni();
            while (true) {
                try {
                    simulazioneService.modificaPrevisioni();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void destroy() {
        archivioConsumi.removeObserver(this);
        consumoAttuale.removeObserver(this);
        percentualeBatteria.removeObserver(this);
        produzioneAttuale.removeObserver(this);
        parametriAttivi.removeObserver(this);
        meteo.removeObserver(this);
    }

    @Override
    public void update(String nomeMetodo) {
        if (nomeMetodo.equalsIgnoreCase("setPercentualeBatteria")) {
            percentualeBatteriaUpdate = true;
        }
        if (nomeMetodo.equalsIgnoreCase("setProduzioneAttuale")) {
            produzioneAttualeUpdate = true;
        }
        if (nomeMetodo.equalsIgnoreCase("setConsumoAttuale")) {
            consumoAttualeUpdate = true;
        }
        if (nomeMetodo.equalsIgnoreCase("setIdConsumo")
                || nomeMetodo.equalsIgnoreCase("setDataConsumo")
                || nomeMetodo.equalsIgnoreCase("setConsumoGiornaliero")
                || nomeMetodo.equalsIgnoreCase("setIdEdificio")) {
            archivioConsumiUpdate = true;
        }
        if (nomeMetodo.equalsIgnoreCase("setIdMeteo")
                || nomeMetodo.equalsIgnoreCase("setDataRilevazione")
                || nomeMetodo.equalsIgnoreCase("setOraRilevazione")
                || nomeMetodo.equalsIgnoreCase("setVelocitaVento")
                || nomeMetodo.equalsIgnoreCase("setProbabilitaPioggia")
                || nomeMetodo.equalsIgnoreCase("setCondizioniMetereologiche")) {
            meteoUpdate = true;

        }
        if (nomeMetodo.equalsIgnoreCase("setIdParametro")
                || nomeMetodo.equalsIgnoreCase("setTipoSorgente")
                || nomeMetodo.equalsIgnoreCase("setFlagPreferenzaSorgente")
                || nomeMetodo.equalsIgnoreCase("setPercentualeUtilizzoSorgente")
                || nomeMetodo.equalsIgnoreCase("setPrioritaSorgente")) {
            parametriAttiviUpdate = true;
        }
    }

}
