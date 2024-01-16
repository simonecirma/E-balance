package com.c17.ebalance.ebalance.contratto.controller;

import com.c17.ebalance.ebalance.contratto.controller.ContrattoController;
import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContrattoControllerTest {

    @Mock
    private ContrattoService contrattoService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private ContrattoController contrattoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoGet() throws IOException, ServletException, SQLException {
        when(request.getParameter("action")).thenReturn("aggiornaContratto");
        when(request.getServletContext().getRequestDispatcher("/contratto.jsp")).thenReturn(requestDispatcher);

        List<ContrattoBean> contratti = new ArrayList<>();
        ContrattoBean contratto = new ContrattoBean();
        when(contrattoService.visualizzaStoricoContratti()).thenReturn(contratti);
        when(contrattoService.visualizzaContratto()).thenReturn(contratto);

        contrattoController.doGet(request, response);

        verify(contrattoService, times(1)).aggiornaContratto(any(ContrattoBean.class));
        verify(contrattoService, times(1)).visualizzaStoricoContratti();
        verify(contrattoService, times(1)).visualizzaContratto();
        verify(request.getServletContext(), times(1)).getRequestDispatcher("/contratto.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void testAggiornaContratto() throws ServletException, IOException, SQLException {
        when(request.getParameter("idContratto")).thenReturn("1");
        when(request.getParameter("nomeEnte")).thenReturn("TestEnte");
        when(request.getParameter("consumoMedioAnnuale")).thenReturn("100.0");
        when(request.getParameter("costoMedioUnitario")).thenReturn("10.0");
        when(request.getParameter("dataSottoscrizione")).thenReturn("2022-01-01");
        when(request.getParameter("durata")).thenReturn("12");
        when(request.getParameter("prezzoVendita")).thenReturn("120.0");
        when(request.getParameter("idAmministratore")).thenReturn("1");

        contrattoController.aggiornaContratto(request, response);

        verify(contrattoService, times(1)).aggiornaContratto(any(ContrattoBean.class));
    }

    @Test
    void testAggiungiContratto() throws IOException, ServletException, SQLException {
        when(request.getParameter("nomeEnte")).thenReturn("TestEnte");
        when(request.getParameter("consumoMedioAnnuale")).thenReturn("100.0");
        when(request.getParameter("costoMedioUnitario")).thenReturn("10.0");
        when(request.getParameter("dataSottoscrizione")).thenReturn("2022-01-01");
        when(request.getParameter("durata")).thenReturn("12");
        when(request.getParameter("prezzoVendita")).thenReturn("120.0");
        when(request.getParameter("idAmministratore")).thenReturn("1");

        ContrattoBean contrattoAggiunto = new ContrattoBean();
        when(contrattoService.verificaPrimoContratto()).thenReturn(true);
        when(contrattoService.visualizzaContratto()).thenReturn(contrattoAggiunto);

        contrattoController.aggiungiContratto(request, response);

        verify(contrattoService, times(1)).aggiungiContratto(any(ContrattoBean.class));
        verify(contrattoService, times(1)).verificaPrimoContratto();

        assertEquals(contrattoAggiunto, request.getAttribute("contratto"));

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpServletResponse> responseCaptor = ArgumentCaptor.forClass(HttpServletResponse.class);

        verify(requestDispatcher).forward(request, responseCaptor.capture());
        verify(requestDispatcher).forward(any(), responseCaptor.capture());

        assertEquals("/dashboard.jsp", argument.getValue());
        assertEquals(response, responseCaptor.getValue());
    }

}
