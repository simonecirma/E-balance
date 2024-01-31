package com.c17.ebalance.ebalance.accesso.controller;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogoutControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private LogoutController logoutController;

    @Test
    void testDoGet() throws IOException, ServletException {
        MockitoAnnotations.openMocks(this);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        logoutController.doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    void testDoPost() throws IOException, ServletException {
        MockitoAnnotations.openMocks(this);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        logoutController.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("login.jsp");
    }
}
