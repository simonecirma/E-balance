package com.c17.ebalance.ebalance.amministratore.controller;

import com.c17.ebalance.ebalance.amministratore.service.AmministratoreService;
import com.c17.ebalance.ebalance.amministratore.service.VenditaService;
import com.c17.ebalance.ebalance.amministratore.service.ReportService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class AmministratoreControllerTest {

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    private AmministratoreService amministratoreService;

    @Mock
    private ReportService reportService;

    @Mock
    private VenditaService venditaService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private AmministratoreController amministratoreController;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        amministratoreController.init(servletConfig);
    }

    @Test
    void testAggiungiAmministratore() {
        try {
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            HttpSession session = mock(HttpSession.class);
            RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

            when(request.getSession(true)).thenReturn(session);
            when(session.getAttribute("email")).thenReturn("mocked-email");

            when(request.getParameter("nome")).thenReturn("TestNome");
            when(request.getParameter("cognome")).thenReturn("TestCognome");
            when(request.getParameter("password")).thenReturn("TestPassword");
            when(request.getParameter("dataNascita")).thenReturn("2000-01-01");
            when(request.getParameter("idAmministratore")).thenReturn("1");
            when(request.getParameter("flagTipo")).thenReturn("1");

            when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

            amministratoreController.doGet(request, response);

            verify(amministratoreService, times(1)).aggiungiAmministratore(any(AmministratoreBean.class));

            verify(amministratoreService, times(1)).verificaSuperAdmin();

            assertNotNull(request.getAttribute("amministratore"));

            verify(requestDispatcher).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    void testAggiornaAmministratore() {
        try {
            when(request.getParameter("nome")).thenReturn("TestNome");
            when(request.getParameter("cognome")).thenReturn("TestCognome");
            when(request.getParameter("email")).thenReturn("TestEmail");
            when(request.getParameter("password")).thenReturn("TestCognome");
            when(request.getParameter("dataNascita")).thenReturn("01-01-2000");
            when(request.getParameter("idAmministratore")).thenReturn("1");
            when(request.getParameter("flagTipo")).thenReturn("1");

            AmministratoreBean amministratoreAggiornato = new AmministratoreBean();

            amministratoreController.aggiornaAmministratore(request, response);

            verify(amministratoreService, times(1)).aggiornaAmministratore(any(AmministratoreBean.class));

            assertEquals(amministratoreAggiornato, request.getAttribute("amministratore"));

            ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
            verify(requestDispatcher).forward(request, response);
            verify(requestDispatcher).forward(any(), any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Captor
    private ArgumentCaptor<ReportBean> reportBeanCaptor;

    @Test
    void testGeneraReport() {
        try {
            // Crea un mock per RequestDispatcher
            RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

            // Configura il comportamento del mock
            when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

            when(request.getParameter("action")).thenReturn("generaReport");
            ReportBean reportGenerato = new ReportBean();
            when(reportService.generaReport(any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(reportGenerato);

            amministratoreController.doGet(request, response);

            // Verifica che il metodo forward sia chiamato correttamente
            verify(requestDispatcher, times(1)).forward(request, response);

            verify(reportService, times(1)).aggiungiReport(reportBeanCaptor.capture());
            verify(reportService, times(1)).visualizzaReport();

            assertEquals(reportGenerato, reportBeanCaptor.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVendita() {
        try {
            when(request.getParameter("action")).thenReturn("vendita");
            when(request.getAttribute("idAmministratore")).thenReturn(1);

            amministratoreController.doGet(request, response);

            verify(venditaService, times(1)).effettuaVendita(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}