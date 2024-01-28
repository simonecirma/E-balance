package com.c17.ebalance.ebalance.contratto.controller;

import com.c17.ebalance.ebalance.amministratore.controller.AmministratoreController;
import com.c17.ebalance.ebalance.amministratore.service.AmministratoreService;
import com.c17.ebalance.ebalance.contratto.controller.ContrattoController;
import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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


@Nested
class ContrattoControllerTest {

    @Mock
    private ContrattoService contrattoService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private ContrattoController contrattoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAggiornaContratto() throws ServletException, IOException, SQLException {
        try {
            when(request.getParameter("idContratto")).thenReturn("TestId");
            when(request.getParameter("nomeEnte")).thenReturn("TestEnte");
            when(request.getParameter("consumoMedioAnnuale")).thenReturn("TestConsumoMedioAnnuale");
            when(request.getParameter("costoMedioUnitario")).thenReturn("TestCostoMedioUnitario");
            when(request.getParameter("dataSottoscrizione")).thenReturn("TestDataSottoscrizione");
            when(request.getParameter("durata")).thenReturn("TestDurata");
            when(request.getParameter("prezzoVendita")).thenReturn("TestPrezzoVendita");
            when(request.getParameter("idAmministratore")).thenReturn("TestIdAmministratore");

            ContrattoBean contrattoAggiornato = new ContrattoBean();

            contrattoController.aggiornaContratto(request, response);

            verify(contrattoService, times(1)).aggiornaContratto(any(ContrattoBean.class));

            assertEquals(contrattoAggiornato, request.getAttribute("contratto"));

            ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
            verify(requestDispatcher).forward(request, response);
            verify(requestDispatcher).forward(any(), any());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    void testAggiungiContratto() throws IOException, ServletException, SQLException {
        try {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getParameter("idContratto")).thenReturn("TestId");
            when(request.getParameter("nomeEnte")).thenReturn("TestEnte");
            when(request.getParameter("consumoMedioAnnuale")).thenReturn("TestConsumoMedioAnnuale");
            when(request.getParameter("costoMedioUnitario")).thenReturn("TestCostoMedioUnitario");
            when(request.getParameter("dataSottoscrizione")).thenReturn("TestDataSottoscrizione");
            when(request.getParameter("durata")).thenReturn("TestDurata");
            when(request.getParameter("prezzoVendita")).thenReturn("TestPrezzoVendita");
            when(request.getParameter("idAmministratore")).thenReturn("TestIdAmministratore");

            HttpServletResponse response = mock(HttpServletResponse.class);

            ServletConfig servletConfig = mock(ServletConfig.class);
            when(servletConfig.getServletContext()).thenReturn(mock(ServletContext.class));

            RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
            when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

            ServletContext servletContext = mock(ServletContext.class);
            when(servletContext.getAttribute("javax.servlet.config")).thenReturn(servletConfig);

            when(request.getServletContext()).thenReturn(servletContext);

            ContrattoBean contrattoAggiunto = new ContrattoBean();
            when(contrattoService.verificaPrimoContratto()).thenReturn(true);
            when(contrattoService.visualizzaContratto()).thenReturn(contrattoAggiunto);

            contrattoController.aggiungiContratto(request, response);

            verify(contrattoService, times(1)).aggiungiContratto(any(ContrattoBean.class));
            verify(contrattoService, times(1)).verificaPrimoContratto();

            assertEquals(contrattoAggiunto, request.getAttribute("contratto"));

            ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
            verify(requestDispatcher).forward(request, response);
            verify(requestDispatcher).forward(any(), any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
