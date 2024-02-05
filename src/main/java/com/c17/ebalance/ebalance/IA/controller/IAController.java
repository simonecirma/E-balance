package com.c17.ebalance.ebalance.IA.controller;

import com.c17.ebalance.ebalance.IA.service.IAService;
import com.c17.ebalance.ebalance.IA.service.IAServiceImpl;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller per la gestione delle richieste legate all'Intelligenza Artificiale (IA) nel sistema eBalance.
 * Questa servlet fornisce endpoint per ottenere informazioni sui parametri IA, interazioni con le sorgenti e parametri attivi.
 */
@WebServlet(name = "IAController", value = "/IAController")
public class IAController extends HttpServlet {
    private IAService IAService = new IAServiceImpl();

    /**
     * Gestisce le richieste HTTP GET per ottenere informazioni sui parametri, interazioni e parametri attivi dell'IA.
     *
     * @param request  L'oggetto HttpServletRequest che contiene la richiesta del client.
     * @param response L'oggetto HttpServletResponse che contiene la risposta al client.
     * @throws IOException  Se si verifica un errore durante l'invio o la ricezione dei dati HTTP.
     */
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        try {
            List<ParametriIABean> parametriIA = ottieniParametri();
            List<InteragisceBean> interazioneParametri = ottieniInterazioneParametri();
            List<InteragisceBean> parametriAttivi = ottieniParametriAttivi();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gestisce le richieste HTTP POST reindirizzandole a {@link #doGet(HttpServletRequest, HttpServletResponse)}.
     *
     * @param request  L'oggetto HttpServletRequest che contiene la richiesta del client.
     * @param response L'oggetto HttpServletResponse che contiene la risposta al client.
     * @throws IOException  Se si verifica un errore durante l'invio o la ricezione dei dati HTTP.
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    /**
     * Ottiene la lista dei parametri IA.
     *
     * @return La lista dei parametri IA.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public List<ParametriIABean> ottieniParametri() throws SQLException {
        return IAService.visualizzaParametri();
    }

    /**
     * Ottiene la lista delle interazioni tra parametri IA e le sorgenti.
     *
     * @return La lista delle interazioni tra parametri IA e le sorgenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public List<InteragisceBean> ottieniInterazioneParametri() throws SQLException {
        return IAService.visualizzaInterazioneParametri();
    }

    /**
     * Ottiene la lista dei parametri IA attivi.
     *
     * @return La lista dei parametri IA attivi.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public List<InteragisceBean> ottieniParametriAttivi() throws SQLException {
        return IAService.ottieniParametriAttivi();
    }


    /**
     * Metodo chiamato dalla servlet container per distruggere la servlet.
     */
    public void destroy() {
    }
}