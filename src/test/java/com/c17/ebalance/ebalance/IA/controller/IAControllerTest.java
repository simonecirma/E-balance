package com.c17.ebalance.ebalance.IA.controller;
import com.c17.ebalance.ebalance.IA.service.IAService;

import com.c17.ebalance.ebalance.accesso.controller.AccessoController;
import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class IAControllerTest {

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

    @Mock
    private IAService iaService;

    @InjectMocks
    private IAController iaController;

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
    public void testDoGet() throws IOException, SQLException {
        List<ParametriIABean> mockParametriIA = new ArrayList<>();
        List<InteragisceBean> mockInterazioneParametri = new ArrayList<>();
        List<InteragisceBean> mockParametriAttivi = new ArrayList<>();

        when(iaService.visualizzaParametri()).thenReturn(mockParametriIA);
        when(iaService.visualizzaInterazioneParametri()).thenReturn(mockInterazioneParametri);
        when(iaService.ottieniParametriAttivi()).thenReturn(mockParametriAttivi);

        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        iaController.doGet(mockRequest, mockResponse);

        Mockito.verify(iaService).visualizzaParametri();

        Mockito.verify(iaService,times(1)).visualizzaInterazioneParametri();

        Mockito.verify(iaService).ottieniParametriAttivi();

        assertEquals(mockParametriIA, iaController.ottieniParametri());
        assertEquals(mockInterazioneParametri, iaController.ottieniInterazioneParametri());
        assertEquals(mockParametriAttivi, iaController.ottieniParametriAttivi());
    }
}
