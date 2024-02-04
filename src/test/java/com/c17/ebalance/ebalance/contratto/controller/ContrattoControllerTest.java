package com.c17.ebalance.ebalance.contratto.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ContrattoControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private ContrattoService contrattoService;

    @InjectMocks
    private ContrattoController contrattoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contrattoController = new ContrattoController(contrattoService, null);
    }

    @Test
    void testDoGetActionAggiornaContratto() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("aggiornaContratto");

        when(request.getParameter("idContratto")).thenReturn("1");
        when(request.getParameter("nomeEnte")).thenReturn("Ente Test");
        when(request.getParameter("consumoMedioAnnuale")).thenReturn("100.0");
        when(request.getParameter("costoMedioUnitario")).thenReturn("50.0");
        when(request.getParameter("dataSottoscrizione")).thenReturn("2022-02-04");
        when(request.getParameter("durata")).thenReturn("2");
        when(request.getParameter("prezzoVendita")).thenReturn("75.0");
        when(request.getParameter("idAmministratore")).thenReturn("3");

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        contrattoController.doGet(request, response);

        verify(contrattoService).aggiornaContratto(any(ContrattoBean.class));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGetActionAggiungiContratto() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("aggiungiContratto");

        when(request.getParameter("nomeEnte")).thenReturn("Nuovo Ente");
        when(request.getParameter("consumoMedioAnnuale")).thenReturn("120.0");
        when(request.getParameter("costoMedioUnitario")).thenReturn("60.0");
        when(request.getParameter("dataSottoscrizione")).thenReturn("2022-02-05");
        when(request.getParameter("durata")).thenReturn("3");
        when(request.getParameter("prezzoVendita")).thenReturn("90.0");
        when(request.getParameter("idAmministratore")).thenReturn("4");

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(contrattoService.verificaPrimoContratto()).thenReturn(false);

        contrattoController.doGet(request, response);

        verify(contrattoService).aggiungiContratto(any(ContrattoBean.class));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGetDefaultAction() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn(null);

        List<ContrattoBean> mockContratti = new ArrayList<>();
        when(contrattoService.visualizzaStoricoContratti()).thenReturn(mockContratti);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        contrattoController.doGet(request, response);

        verify(request, times(2)).setAttribute(anyString(), any());
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void testDoGetWithAction() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("otherAction");

        List<ContrattoBean> mockContratti = new ArrayList<>();
        when(contrattoService.visualizzaStoricoContratti()).thenReturn(mockContratti);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        contrattoController.doGet(request, response);

        verify(request, times(2)).setAttribute(anyString(), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPost() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        contrattoController.doPost(request, response);
        verify(contrattoService, never()).aggiungiContratto(any());
        verify(response, never()).sendRedirect(anyString());
    }
}
