package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia {@code ReportService} definisce il contratto per la gestione dei report nel sistema eBalance.
 * Fornisce metodi per la visualizzazione, l'aggiunta e la generazione di nuovi report.
 */
public interface ReportService {

    /**
     * Restituisce una lista di tutti i report presenti nel sistema.
     *
     * @return Una lista di oggetti {@code ReportBean}.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<ReportBean> visualizzaReport() throws SQLException;

    /**
     * Aggiunge un nuovo report al sistema.
     *
     * @param bean L'oggetto {@code ReportBean} rappresentante il nuovo report.
     * @throws SQLException in caso di errori di accesso al database.
     */
    void aggiungiReport(ReportBean bean) throws SQLException;

    /**
     * Genera un nuovo report sulla base delle informazioni fornite.
     *
     * @param dataInizio    La data di inizio del periodo per il report.
     * @param dataFine      La data di fine del periodo per il report.
     * @param servletPath   Il percorso del servlet che richiede la generazione del report.
     * @param session       L'oggetto HttpSession associato alla richiesta.
     * @return L'oggetto {@code ReportBean} rappresentante il nuovo report generato.
     * @throws IOException  in caso di errori di I/O durante la generazione del report.
     * @throws SQLException in caso di errori di accesso al database.
     */
    ReportBean generaReport(Date dataInizio, Date dataFine, String servletPath, HttpSession session) throws IOException, SQLException;
}
