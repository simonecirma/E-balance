package com.c17.ebalance.ebalance.accesso.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;

import java.io.IOException;

/**
 * La classe {@code LogoutController} è una servlet che gestisce la disconnessione dell'amministratore
 * dall'applicazione eBalance. Invalida la sessione corrente e reindirizza l'utente alla pagina di login.
 */
@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
    /**
     * Gestisce le richieste HTTP di tipo GET per la disconnessione dell'amministratore.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws ServletException in caso di errori durante la gestione della richiesta.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }

    /**
     * Gestisce le richieste HTTP di tipo POST per la disconnessione dell'amministratore.
     *
     * @param request  L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response L'oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws ServletException in caso di errori durante la gestione della richiesta.
     * @throws IOException      in caso di errori di I/O durante la gestione della richiesta.
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
