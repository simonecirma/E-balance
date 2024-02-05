package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia {@code ConsumoService} definisce il contratto per la gestione dei dati di consumo nel sistema eBalance.
 * Fornisce metodi per ottenere i consumi degli edifici, visualizzare lo storico dei consumi e ottenere il consumo per un periodo specifico.
 */
public interface ConsumoService {

    /**
     * Ottiene i consumi degli edifici per il periodo corrente.
     *
     * @param consumoEdifici Un array contenente i consumi degli edifici da aggiornare.
     * @return Un array con i consumi aggiornati degli edifici.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float[] ottieniConsumiEdifici(float consumoEdifici[]) throws SQLException;

    /**
     * Restituisce lo storico dei consumi nel sistema.
     *
     * @return Una lista di oggetti {@code ArchivioConsumoBean}.
     * @throws SQLException in caso di errori di accesso al database.
     */
    List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException;

    /**
     * Ottiene il consumo totale per un periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Il consumo totale per il periodo specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float getConsumoPerData(Date dataInizio, Date dataFine) throws SQLException;
}
