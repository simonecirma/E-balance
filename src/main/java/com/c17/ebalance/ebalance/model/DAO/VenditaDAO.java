package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia per l'accesso ai dati delle vendite.
 */
public interface VenditaDAO {

    /**
     * Restituisce la lista delle vendite comprese tra due date specificate.
     *
     * @param dataInizio Data di inizio del periodo.
     * @param dataFine   Data di fine del periodo.
     * @return Lista di oggetti VenditaBean.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<VenditaBean> getVendite(final Date dataInizio, final Date dataFine) throws SQLException;

    /**
     * Restituisce il ricavo totale delle vendite comprese tra due date specificate.
     *
     * @param dataInizio Data di inizio del periodo.
     * @param dataFine   Data di fine del periodo.
     * @return Il ricavo totale delle vendite.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    float getRicavoTotalePerData(final Date dataInizio, final Date dataFine) throws SQLException;

    /**
     * Effettua una nuova vendita inserendo i dati nel database.
     *
     * @param bean Oggetto VenditaBean rappresentante la vendita.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void effetuaVendita(VenditaBean bean) throws SQLException;
}
