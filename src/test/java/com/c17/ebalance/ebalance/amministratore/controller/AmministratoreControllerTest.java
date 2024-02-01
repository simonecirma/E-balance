package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.amministratore.service.AmministratoreService;
import com.c17.ebalance.ebalance.amministratore.service.ReportService;
import com.c17.ebalance.ebalance.amministratore.service.VenditaService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
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

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AmministratoreControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    AmministratoreService amministratoreService;

    @Mock
    ReportService reportService;


    @Mock
    VenditaService venditaService;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @InjectMocks
    private AmministratoreController amministratoreController;

    @BeforeEach
    void setUp() throws ServletException, SQLException {
        MockitoAnnotations.openMocks(this);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        amministratoreService = Mockito.mock(AmministratoreService.class);
        reportService = Mockito.mock(ReportService.class);
        venditaService = Mockito.mock(VenditaService.class);

        amministratoreController = new AmministratoreController(amministratoreService, reportService, venditaService, servletContext);
        amministratoreController.init(servletConfig);
    }

    @Test
    void testDoGetActionVerificaSuperAdmin() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("verificaSuperAdmin");
        when(amministratoreService.verificaSuperAdmin()).thenReturn(true);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        amministratoreController.doGet(request, response);

        verify(request).setAttribute("result", "Risulta già un sistema configurato, accedi!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testVerificaPresenzaEmail() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("verificaPresenzaEmail");
        when(request.getParameter("email")).thenReturn("m.ercolino1@studenti.unisa.it");
        when(amministratoreService.verificaPresenzaEmail("m.ercolino1@studenti.unisa.it")).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        amministratoreController.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).getWriter();

        writer.flush();
        String expectedJsonResponse = "{\"flagPresenza\": true}";
        String actualJsonResponse = stringWriter.toString().trim();
        assert actualJsonResponse.equals(expectedJsonResponse) : "JSON response mismatch";
    }

    @Test
    void testAggiornaAmministratore() throws IOException, SQLException, ParseException {
        when(request.getParameter("action")).thenReturn("aggiornaAmministratore");
        when(request.getParameter("nome")).thenReturn("Antonioo");
        when(request.getParameter("cognome")).thenReturn("Di Giorgio");
        when(request.getParameter("email")).thenReturn("a.digiorgio1@studenti.unisa.it");
        when(request.getParameter("password")).thenReturn("Antonio2024!");
        when(request.getParameter("dataNascita")).thenReturn("2000-12-05");
        when(request.getParameter("idAmministratore")).thenReturn("5");
        when(request.getParameter("flagTipo")).thenReturn("false");

        Date dataNascita = new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2000-12-05").getTime());

        when(amministratoreService.aggiornaAmministratore(any())).thenReturn(new AmministratoreBean());
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession(true)).thenReturn(session);
        amministratoreController.doGet(request, response);

        verify(amministratoreService).aggiornaAmministratore(any());
        verify(session).setAttribute(eq("nome"), eq("Antonioo"));
        verify(session).setAttribute(eq("cognome"), eq("Di Giorgio"));
        verify(session).setAttribute(eq("email"), eq("a.digiorgio1@studenti.unisa.it"));
        verify(session).setAttribute(eq("password"), eq("Antonio2024!"));
        verify(session).setAttribute(eq("dataNascita"), eq(dataNascita));
        verify(session).setAttribute(eq("idAmministratore"), eq(5));
        verify(session).setAttribute(eq("flagTipo"), eq(false));
    }

    @Test
    void testAggiungiAmministratore() throws ServletException, IOException, ParseException, SQLException {
        when(request.getParameter("action")).thenReturn("aggiungiAmministratore");
        when(request.getParameter("nome")).thenReturn("Antonio");
        when(request.getParameter("cognome")).thenReturn("Palma");
        when(request.getParameter("email")).thenReturn("antonio.palma1@studenti.unisa.it");
        when(request.getParameter("password")).thenReturn("Antonio2000!");
        when(request.getParameter("dataNascita")).thenReturn("2000-01-01");

        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
        java.sql.Date dataNascita = new java.sql.Date(utilDate.getTime());

        AmministratoreBean mockAdmin = new AmministratoreBean();
        mockAdmin.setNome("Antonio");
        mockAdmin.setCognome("Palma");
        mockAdmin.setEmail("antonio.palma1@studenti.unisa.it");
        mockAdmin.setPassword("Antonio2000!");
        mockAdmin.setDataNascita(dataNascita);
        mockAdmin.setFlagTipo(false);

        doNothing().when(amministratoreService).aggiungiAmministratore(any());

        when(request.getSession(true)).thenReturn(session);
        amministratoreController.doGet(request, response);

        verify(amministratoreService).aggiungiAmministratore(any());
        verify(response).sendRedirect(eq("AmministratoreController?action=gestisciAmministratori"));
    }

    @Test
    void testGestisciAmministratori() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("gestisciAmministratori");

        List<AmministratoreBean> mockAmministratori = new ArrayList<>();

        when(amministratoreService.visualizzaAmministratori()).thenReturn(mockAmministratori);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        amministratoreController.doGet(request, response);

        verify(request).setAttribute("amministratori", mockAmministratori);
        verify(request).getRequestDispatcher("/amministratori.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testRimuoviAmministratore() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("rimuoviAmministratore");
        when(request.getParameter("idAmministratore")).thenReturn("8");

        amministratoreController.doGet(request, response);

        verify(request).getParameter("idAmministratore");
        verify(amministratoreService).rimuoviAmministratore(8);
        verify(response).sendRedirect("AmministratoreController?action=gestisciAmministratori");
    }

    @Test
    void testVediReport() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("vediReport");

        List<ReportBean> reportList = new ArrayList<>();
        when(reportService.visualizzaReport()).thenReturn(reportList);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        amministratoreController.doGet(request, response);

        verify(reportService).visualizzaReport();
        verify(request).setAttribute("listReport", reportList);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testVendita() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("vendita");
        int idAmministratore = 0;

        amministratoreController.doGet(request, response);

        verify(request).getParameter("idAmministratore");
        verify(venditaService).effettuaVendita(idAmministratore);
        verify(response).sendRedirect("DatiController?action=generaDashboard");
    }

    @Test
    void testGeneraReport() throws ServletException, IOException, SQLException, ParseException {
        when(request.getParameter("action")).thenReturn("generaReport");

        ReportBean reportMock = mock(ReportBean.class);
        when(reportService.generaReport(request, response)).thenReturn(reportMock);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        amministratoreController.doGet(request, response);

        verify(reportService).aggiungiReport(reportMock);
        verify(request, times(1)).getRequestDispatcher("/report.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGetDefaultAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn(null);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        amministratoreController.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPost() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        amministratoreController.doGet(request, response);
    }
}
