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
class ContrattoControllerTest {
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
        when(request.getParameter("action")).thenReturn("aggiornaContratto");
        when(request.getParameter("idContratto")).thenReturn("1");
        when(request.getParameter("nomeEnte")).thenReturn("Enel Energiaa");
        when(request.getParameter("consumoMedioAnnuale")).thenReturn("1000000");
        when(request.getParameter("costoMedioUnitario")).thenReturn("0.05");
        when(request.getParameter("dataSottoscrizione")).thenReturn("2000-01-01");
        when(request.getParameter("durata")).thenReturn("120");
        when(request.getParameter("prezzoVendita")).thenReturn("0.03");
        when(request.getParameter("idAmministratore")).thenReturn("3");

        Date dataSottoscrizione = new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01").getTime());

        when(contrattoService.aggiornaContratto(any())).thenReturn(new ContrattoBean());
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession(true)).thenReturn(session);
        contrattoController.doGet(request, response);

        verify(contrattoService).aggiornaContratto(any());
        verify(request).setAttribute(eq("idContratto"), eq("1"));
        verify(request).setAttribute(eq("nomeEnte"), eq("Enel Energiaa"));
        verify(request).setAttribute(eq("consumoMedioAnnuale"), eq("1000000"));
        verify(request).setAttribute(eq("costoMedioUnitario"), eq("0.05"));
        verify(request).setAttribute(eq("dataSottoscrizione"), eq(dataSottoscrizione));
        verify(request).setAttribute(eq("durata"), eq(120));
        verify(request).setAttribute(eq("prezzoVendita"), eq(0.03));
        verify(request).setAttribute(eq("idAmministratore"), eq(3));
    }
}