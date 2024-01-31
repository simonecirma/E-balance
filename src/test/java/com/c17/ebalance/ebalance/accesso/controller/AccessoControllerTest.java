package com.c17.ebalance.ebalance.accesso.controller;
import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
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
    private RequestDispatcher dispatcher;

    @Mock
    AccessoService accessoService;
    @InjectMocks
    private AccessoController accessoController;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        accessoService = Mockito.mock(AccessoService.class);

        accessoController = new AccessoController(accessoService, servletContext);
        accessoController.init(servletConfig);
    }



    @Test
    void testValidLogin() throws IOException, SQLException {
        when(request.getParameter("email")).thenReturn("m.ercolino1@studenti.unisa.it");
        when(request.getParameter("password")).thenReturn("Matteo2024!");
        when(request.getSession(true)).thenReturn(session);

        AmministratoreBean amministratore = new AmministratoreBean();
        when(accessoService.login("m.ercolino1@studenti.unisa.it", "Matteo2024!")).thenReturn(amministratore);

        accessoController.doGet(request, response);

        Mockito.verify(response).sendRedirect("DatiController?action=generaDashboard");
    }

    @ParameterizedTest
    @CsvSource({
            "m.ercolino@studenti.unisa.it, Matteo2024, Credenziali sbagliate riprova!",
            "m.ercolino1@studenti.unisa.it, Matteo2023, Credenziali sbagliate riprova!",
            "luca@argentero.it, luca2023, Credenziali sbagliate riprova!"
    })
    void testInvalidLogin(String email, String password, String expectedMessage)
            throws IOException, SQLException, ServletException {
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession(true)).thenReturn(session);
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        when(accessoService.login(email, password)).thenReturn(null);

        accessoController.doGet(request, response);

        Mockito.verify(request).setAttribute("result", expectedMessage);
        Mockito.verify(dispatcher).forward(request, response);
    }


    @Test
    void testLoginSetsSession() throws SQLException {
        AmministratoreBean amministratore = new AmministratoreBean();
        amministratore.setEmail("m.ercolino1@studenti.unisa.it");
        when(accessoService.login("m.ercolino1@studenti.unisa.it", "Matteo2024!")).thenReturn(amministratore);

        accessoController.login("m.ercolino1@studenti.unisa.it", "Matteo2024!", session);


        Mockito.verify(session).setAttribute("email", amministratore.getEmail());
        Mockito.verify(session).setAttribute("password", amministratore.getPassword());
        Mockito.verify(session).setAttribute("nome", amministratore.getNome());
        Mockito.verify(session).setAttribute("cognome", amministratore.getCognome());
        Mockito.verify(session).setAttribute("flagTipo", amministratore.getFlagTipo());
        Mockito.verify(session).setAttribute("idAmministratore", amministratore.getIdAmministratore());
        Mockito.verify(session).setAttribute("dataNascita", amministratore.getDataNascita());



        Mockito.verifyNoMoreInteractions(accessoService);
    }




    @Test
    void testLoginDoesNotSetSession() throws SQLException {
        when(accessoService.login("m.ercolino@studenti.unisa.it", "Carlo2024")).thenReturn(null);

        accessoController.login("m.ercolino@studenti.unisa.it", "Carlo2024", session);

        Mockito.verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.any());
    }


}
