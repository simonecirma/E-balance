package com.c17.ebalance.ebalance.contratto.controller;

import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ContrattoControllerTest {/*
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    ContrattoService contrattoService;

    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @InjectMocks
    private ContrattoController contrattoController;

    @BeforeEach
    void setUp() throws ServletException, SQLException {
        MockitoAnnotations.openMocks(this);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        contrattoService = Mockito.mock(ContrattoService.class);

        contrattoController = new ContrattoController(contrattoService, servletContext);
        contrattoController.init(servletConfig);
    }

    @Test
    void testDoGetActionAggiornaContratto() throws ServletException, IOException, SQLException, ParseException {
        // Setup mock data for request parameters
        when(request.getParameter("idContratto")).thenReturn("1");
        when(request.getParameter("nomeEnte")).thenReturn("EnteTest");
        when(request.getParameter("consumoMedioAnnuale")).thenReturn("100.0");
        when(request.getParameter("costoMedioUnitario")).thenReturn("50.0");
        when(request.getParameter("dataSottoscrizione")).thenReturn("2024-02-02");
        when(request.getParameter("durata")).thenReturn("3");
        when(request.getParameter("prezzoVendita")).thenReturn("200.0");
        when(request.getParameter("idAmministratore")).thenReturn("5");

        // Create a mock ContrattoBean
        ContrattoBean expectedContrattoBean = new ContrattoBean();
        expectedContrattoBean.setIdContratto(1);
        expectedContrattoBean.setNomeEnte("EnteTest");
        expectedContrattoBean.setConsumoMedioAnnuale(100.0F);
        expectedContrattoBean.setCostoMedioUnitario(50.0F);
        expectedContrattoBean.setDataSottoscrizione(Date.valueOf("2024-02-02"));
        expectedContrattoBean.setDurata(3);
        expectedContrattoBean.setPrezzoVendita(200.0F);
        expectedContrattoBean.setIdAmministatore(5);

        // Setup mock behavior for contrattoService
        when(contrattoService.aggiornaContratto(any(ContrattoBean.class))).thenReturn(expectedContrattoBean);

        // Call the method
        contrattoController.aggiornaContratto(request, response);

        // Verify interactions
        verify(contrattoService).aggiornaContratto(expectedContrattoBean);
        verify(request).getRequestDispatcher("/contratto.jsp");
        verify(dispatcher).forward(request, response);
    }*/
}