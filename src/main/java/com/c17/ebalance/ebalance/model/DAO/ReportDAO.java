package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ReportBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce i metodi per l'accesso ai dati dei report.
 */
public interface ReportDAO {

    /**
     * Restituisce una lista di oggetti ReportBean rappresentanti i report presenti nel sistema.
     *
     * @return Lista di ReportBean
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati.
     */
    List<ReportBean> visualizzaReport() throws SQLException;

    /**
     * Restituisce l'ID dell'ultimo report presente nel sistema.
     *
     * @return ID dell'ultimo report
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati.
     */
    int ultimoReport() throws SQLException;

    /**
     * Aggiunge un nuovo report al sistema.
     *
     * @param report Oggetto ReportBean rappresentante il report da aggiungere
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati.
     */
    void aggiungiReport(ReportBean report) throws SQLException;
}
