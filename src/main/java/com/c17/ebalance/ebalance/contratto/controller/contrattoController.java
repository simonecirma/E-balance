package com.c17.ebalance.ebalance.contratto.controller;

import com.c17.ebalance.ebalance.contratto.service.*;
import com.c17.ebalance.ebalance.model.entity.contrattoBean;
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
import java.util.List;

@WebServlet(name = "contrattoController", value = "/contrattoController")
public class contrattoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private contrattoService contrattoServ=new contrattoServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action=request.getParameter("action");

        try{
            if(action!=null){
                if(action.equalsIgnoreCase("aggiornaContratto")) {
                    aggiornaContratto(request, response);
                }
                if(action.equalsIgnoreCase("aggiungiContratto")){
                    aggiungiContratto(request, response);
                }
                if(action.equalsIgnoreCase("vediStorico")){
                    List<contrattoBean> contratti=contrattoServ.visualizzaStoricoContratti();
                    request.setAttribute("contratti", contratti);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
                    dispatcher.forward(request, response);
                }
                if(action.equalsIgnoreCase("vediContrattoCorrente")){
                    contrattoBean contratto=contrattoServ.visualizzaContratto();
                    request.setAttribute("contratto", contratto);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
                    dispatcher.forward(request, response);
                }
            }else{
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
                dispatcher.forward(request, response);
            }
        }catch(SQLException | ServletException e){
            throw new RuntimeException(e);
        }
    }

    public void aggiornaContratto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id=Integer.parseInt(request.getParameter("IdContratto"));
        String ente=request.getParameter("NomeEnte");
        float consumo=Float.parseFloat(request.getParameter("ConsumoMedioAnnuale"));
        float costo=Float.parseFloat(request.getParameter("CostoMedioUnitario"));
        Date data=Date.valueOf(request.getParameter("DataSottoscrizione"));
        int durata=Integer.parseInt(request.getParameter("Durata"));
        float prezzo=Float.parseFloat(request.getParameter("PrezzoVendita"));
        int admin=Integer.parseInt(request.getParameter("IdAmministratore"));

        contrattoBean bean=new contrattoBean();

        bean.setIdContratto(id);
        bean.setNomeEnte(ente);
        bean.setConsumoMedioAnnuale(consumo);
        bean.setCostoMedioUnitario(costo);
        bean.setDataSottoscrizione(data);
        bean.setDurata(durata);
        bean.setPrezzoVendita(prezzo);
        bean.setIdAmministatore(admin);
        try{
            contrattoServ.aggiornaContratto(bean);
        }catch(SQLException e){
            e.printStackTrace();
            return;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contratto.jsp");
        dispatcher.forward(request, response);
    }

    public void aggiungiContratto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        contrattoBean bean=new contrattoBean();
        bean.setNomeEnte(request.getParameter("NomeEnte"));
        bean.setConsumoMedioAnnuale(Float.parseFloat(request.getParameter("ConsumoMedioAnnuale")));
        bean.setCostoMedioUnitario(Float.parseFloat(request.getParameter("CostoMedioUnitario")));
        bean.setDataSottoscrizione(Date.valueOf(request.getParameter("DataSottoscrizione")));
        bean.setDurata(Integer.parseInt(request.getParameter("Durata")));
        bean.setPrezzoVendita(Float.parseFloat(request.getParameter("PrezzoVendita")));
        bean.setIdAmministatore(Integer.parseInt(request.getParameter("IdAmministratore")));
        try{
            contrattoServ.aggiungiContratto(bean);
        }catch(SQLException e){
            e.printStackTrace();
            return;
        }
        response.sendRedirect("contrattoController?action=vediStorico");
    }

    public void destroy() {
    }
}