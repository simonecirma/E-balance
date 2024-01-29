package com.c17.ebalance.ebalance.accesso.controller;

import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AccessoControllerTest {
    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher dispatcher;

    @Mock
    AccessoService accessoService;
    @InjectMocks
    private AccessoController accessoController;

    @BeforeEach
    void setUp() throws ServletException {
        // Inizializzazione dei mock e impostazione del comportamento desiderato
        MockitoAnnotations.openMocks(this);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        accessoService = Mockito.mock(AccessoService.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);

        // Utilizza il costruttore modificato con accessoService e servletContext
        accessoController = new AccessoController(accessoService, servletContext);
        accessoController.init(servletConfig);
    }


    @Test
    void testValidLoginRedirectsToDashboard() throws IOException, SQLException, ServletException {
        // Categoria: Login valido
        when(request.getParameter("email")).thenReturn("valid@example.com");
        when(request.getParameter("password")).thenReturn("validPassword");
        when(request.getSession(true)).thenReturn(session);

        AmministratoreBean amministratore = new AmministratoreBean();
        amministratore.setEmail("valid@example.com");
        when(accessoService.login("valid@example.com", "validPassword")).thenReturn(amministratore);

        accessoController.doGet(request, response);

        // Verifica che il redirect sia effettuato verso la pagina della dashboard
        Mockito.verify(response).sendRedirect("DatiController?action=generaDashboard");
    }

    @ParameterizedTest
    @CsvSource({
            "invalid@example.com, invalidPassword, Credenziali sbagliate, riprova!",
            "valid@example.com, wrongPassword, Credenziali sbagliate, riprova!",
            "nonexistent@example.com, nonExistentPassword, Credenziali sbagliate, riprova!"
    })
    void testInvalidLoginDisplaysErrorMessage(String email, String password, String expectedMessage, String unused)
            throws IOException, SQLException, ServletException {
        // Categoria: Login non valido
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession(true)).thenReturn(session);
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        when(accessoService.login(email, password)).thenReturn(null);

        accessoController.doGet(request, response);

        // Verifica che il messaggio di errore sia impostato correttamente nella richiesta
        Mockito.verify(request).setAttribute("result", expectedMessage);
        // Verifica che la richiesta venga inoltrata alla pagina di login
        Mockito.verify(dispatcher).forward(request, response);
    }


    @Test
    void testLoginSetsSessionAttributesOnValidLogin() throws SQLException {
        // Categoria: Login valido
        when(session.getAttribute("email")).thenReturn(null); // Simula una sessione senza autenticazione
        when(accessoService.login("valid@example.com", "validPassword"))
                .thenReturn(new AmministratoreBean());

        accessoController.login("valid@example.com", "validPassword", session);

        // Verifica che gli attributi della sessione siano impostati correttamente
        Mockito.verify(session).setAttribute(Mockito.eq("email"), Mockito.eq("valid@example.com"));
        Mockito.verify(session).setAttribute(Mockito.eq("password"), Mockito.eq("validPassword"));
        // Altre verifiche per gli altri attributi...
    }

    @Test
    void testLoginDoesNotSetSessionAttributesOnInvalidLogin() throws SQLException {
        // Categoria: Login non valido
        when(session.getAttribute("email")).thenReturn(null); // Simula una sessione senza autenticazione
        when(accessoService.login("invalid@example.com", "invalidPassword")).thenReturn(null);

        accessoController.login("invalid@example.com", "invalidPassword", session);

        // Verifica che gli attributi della sessione non siano impostati in caso di login non valido
        Mockito.verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.any());
    }


}
