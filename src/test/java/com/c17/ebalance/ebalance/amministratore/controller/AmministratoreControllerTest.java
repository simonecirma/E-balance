package com.c17.ebalance.ebalance.amministratore.controller;

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
class AmministratoreControllerTest {

    @Mock
    private AmministratoreService amministratoreService;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAggiornaAmministratore() throws ServletException, IOException, SQLException {
        try{
            when(request.getParameter("nome")).thenReturn("TestNome");
            when(request.getParameter("cognome")).thenReturn("TestCognome");
            when(request.getParameter("email")).thenReturn("TestEmail");
            when(request.getParameter("password")).thenReturn("TestCognome");
            when(request.getParameter("dataNascita")).thenReturn("TestData");
            when(request.getParameter("idAmministratore")).thenReturn("TestId");
            when(request.getParameter("flagTipo")).thenReturn("TestTipo");

            AmministratoreBean amministratoreAggiornato = new AmministratoreBean();

            amministratoreController.aggiornaAmministratore(request, response);

            verify(amministratoreService, times(1)).aggiornaAmministratore(any(AmministratoreBean.class));

            assertEquals(amministratoreAggiornato, request.getAttribute("amministratore"));

            ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
            verify(requestDispatcher).forward(request, response);
            verify(requestDispatcher).forward(any(), any());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    void testAggiungiAmministratore() throws IOException, ServletException, SQLException {
        try {
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getParameter("nome")).thenReturn("TestNome");
            when(request.getParameter("cognome")).thenReturn("TestCognome");
            when(request.getParameter("email")).thenReturn("TestEmail");
            when(request.getParameter("password")).thenReturn("TestCognome");
            when(request.getParameter("dataNascita")).thenReturn("TestData");
            when(request.getParameter("idAmministratore")).thenReturn("TestID");
            when(request.getParameter("flagTipo")).thenReturn("TestTipo");

            HttpServletResponse response = mock(HttpServletResponse.class);

            ServletConfig servletConfig = mock(ServletConfig.class);
            when(servletConfig.getServletContext()).thenReturn(mock(ServletContext.class));

            RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
            when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

            ServletContext servletContext = mock(ServletContext.class);
            when(servletContext.getAttribute("javax.servlet.config")).thenReturn(servletConfig);

            when(request.getServletContext()).thenReturn(servletContext);

            AmministratoreBean amministratoreAggiunto = new AmministratoreBean();

            amministratoreController.aggiungiAmministratore(request, response);

            verify(amministratoreService, times(1)).aggiungiAmministratore(any(AmministratoreBean.class));
            verify(amministratoreService, times(1)).verificaSuperAdmin();

            assertEquals(amministratoreAggiunto, request.getAttribute("amministratore"));

            ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
            verify(requestDispatcher).forward(request, response);
            verify(requestDispatcher).forward(any(), any());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}