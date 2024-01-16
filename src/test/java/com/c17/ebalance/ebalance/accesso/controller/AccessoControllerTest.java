package com.c17.ebalance.ebalance.accesso.controller;

import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class AccessoControllerTest {

    @Mock
    private AccessoService accessoService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private AccessoController accessoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoGet_SuccessfulLogin() throws IOException, ServletException, SQLException {
        // Simula i parametri della richiesta
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("password");

        // Simula il comportamento del metodo di accessoService.login per un login riuscito
        AmministratoreBean amministratore = new AmministratoreBean();
        when(accessoService.login("test@example.com", "password")).thenReturn(amministratore);

        // Simula il comportamento di getSession
        when(request.getSession(true)).thenReturn(session);

        // Chiama il metodo doGet
        accessoController.doGet(request, response);

        // Verifica che il metodo di accessoService.login sia chiamato correttamente
        verify(accessoService, times(1)).login("test@example.com", "password");

        // Verifica che la sessione venga impostata correttamente
        verify(session, times(1)).setAttribute("email", amministratore.getEmail());
        verify(session, times(1)).setAttribute("password", amministratore.getPassword());
        verify(session, times(1)).setAttribute("nome", amministratore.getNome());
        verify(session, times(1)).setAttribute("cognome", amministratore.getCognome());
        verify(session, times(1)).setAttribute("flagTipo", amministratore.getFlagTipo());
        verify(session, times(1)).setAttribute("idAmministratore", amministratore.getIdAmministratore());
        verify(session, times(1)).setAttribute("dataNascita", amministratore.getDataNascita());

        // Verifica che il dispatcher sia chiamato con il parametro corretto
        verify(request.getServletContext(), times(1)).getRequestDispatcher("/dashboard.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void testDoGet_FailedLogin_InvalidPassword() throws IOException, ServletException, SQLException {
        // Simula i parametri della richiesta
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("wrongpassword");

        // Simula il comportamento del metodo di accessoService.login per un login non riuscito
        when(accessoService.login("test@example.com", "wrongpassword")).thenReturn(null);

        // Simula il comportamento di getRequestDispatcher
        when(request.getServletContext().getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

        // Chiama il metodo doGet
        accessoController.doGet(request, response);

        // Verifica che il metodo di accessoService.login sia chiamato correttamente
        verify(accessoService, times(1)).login("test@example.com", "wrongpassword");

        // Verifica che l'attributo "result" sia impostato correttamente
        verify(request, times(1)).setAttribute("result", "Credenziali sbagliate, riprova!");

        // Verifica che il dispatcher sia chiamato con il parametro corretto
        verify(request.getServletContext(), times(1)).getRequestDispatcher("/login.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void testDoGet_FailedLogin_InvalidEmail() throws IOException, ServletException, SQLException {
        // Simula i parametri della richiesta con un'email non esistente
        when(request.getParameter("email")).thenReturn("nonexistent@example.com");
        when(request.getParameter("password")).thenReturn("password");

        // Simula il comportamento del metodo di accessoService.login per un login non riuscito
        when(accessoService.login("nonexistent@example.com", "password")).thenReturn(null);

        // Simula il comportamento di getRequestDispatcher
        when(request.getServletContext().getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

        // Chiama il metodo doGet
        accessoController.doGet(request, response);

        // Verifica che il metodo di accessoService.login sia chiamato correttamente
        verify(accessoService, times(1)).login("nonexistent@example.com", "password");

        // Verifica che l'attributo "result" sia impostato correttamente
        verify(request, times(1)).setAttribute("result", "Credenziali sbagliate, riprova!");

        // Verifica che il dispatcher sia chiamato con il parametro corretto
        verify(request.getServletContext(), times(1)).getRequestDispatcher("/login.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void testDoPost() throws IOException, ServletException {
        // Simula il passaggio dei parametri di richiesta al metodo doGet
        accessoController.doPost(request, response);

        // Verifica che il metodo doGet sia chiamato correttamente
        verify(accessoController, times(1)).doGet(request, response);
    }
}
