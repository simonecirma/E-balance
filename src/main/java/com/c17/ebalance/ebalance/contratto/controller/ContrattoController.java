package com.c17.ebalance.ebalance.contratto.controller;

import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.contratto.service.ContrattoServiceImpl;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ContrattoController", value = "/ContrattoController")
public class ContrattoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContrattoService contrattoService = new ContrattoServiceImpl();

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("aggiornaContratto")) {
                    aggiornaContratto(request, response);

                }
                if (action.equalsIgnoreCase("aggiungiContratto")) {
                    aggiungiContratto(request, response);

                }
                List<ContrattoBean> contratti = contrattoService.visualizzaStoricoContratti();
                request.setAttribute("contratti", contratti);
                ContrattoBean contratto = contrattoService.visualizzaContratto();
                request.setAttribute("contratto", contratto);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            } else {
                List<ContrattoBean> contratti = contrattoService.visualizzaStoricoContratti();
                request.setAttribute("contratti", contratti);
                ContrattoBean contratto = contrattoService.visualizzaContratto();
                request.setAttribute("contratto", contratto);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void aggiornaContratto(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
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

    }

    public void aggiungiContratto(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

    }

    public void destroy() {
    }
}
