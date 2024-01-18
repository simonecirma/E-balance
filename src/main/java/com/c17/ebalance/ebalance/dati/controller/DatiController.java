package com.c17.ebalance.ebalance.dati.controller;

import com.c17.ebalance.ebalance.dati.service.*;
import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.BatteriaBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DatiController", value = "/DatiController")
public class DatiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BatteriaService batteriaService = new BatteriaServiceImpl();
    private ConsumoService consumoService = new ConsumoServiceImpl();
    private ProduzioneService produzioneService = new ProduzioneServiceImpl();


    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        try {
            if (action != null) {

            } else {
                List<BatteriaBean> batteria = batteriaService.visualizzaBatteria();
                List<ConsumoEdificioBean> consumoEdificio = consumoService.visualizzaConsumo();
                List<SorgenteBean> sorgente = produzioneService.visualizzaProduzioneSorgente();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
