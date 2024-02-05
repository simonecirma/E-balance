package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia per la gestione dell'accesso ai dati relativi ai contratti nel sistema eBalance.
 * Definisce metodi per visualizzare, aggiornare e aggiungere contratti, nonché per verificare la presenza del primo contratto e ottenere il contratto attivo.
 * Fornisce anche un metodo per ottenere il prezzo di vendita corrente.
 * Le implementazioni di questa interfaccia dovrebbero gestire l'interazione con il database.
 */
public interface ContrattoDAO {

    /**
     * Restituisce il contratto attivo nel sistema.
     *
     * @return Un oggetto ContrattoBean rappresentante il contratto attivo.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    ContrattoBean visualizzaContratto() throws SQLException;

    /**
     * Restituisce una lista degli storici contratti nel sistema.
     *
     * @return Una lista di oggetti ContrattoBean rappresentanti gli storici contratti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<ContrattoBean> visualizzaStoricoContratti() throws SQLException;

    /**
     * Aggiorna un contratto esistente nel sistema.
     *
     * @param contratto Il contratto da aggiornare.
     * @return Un oggetto ContrattoBean rappresentante il contratto aggiornato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException;

    /**
     * Aggiunge un nuovo contratto nel sistema.
     *
     * @param contratto Il contratto da aggiungere.
     * @return Un oggetto ContrattoBean rappresentante il contratto aggiunto.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    ContrattoBean aggiungiContratto(final ContrattoBean contratto) throws SQLException;

    /**
     * Verifica se è presente il primo contratto nel sistema.
     *
     * @return True se è presente il primo contratto, altrimenti False.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    boolean verificaPrimoContratto() throws SQLException;

    /**
     * Restituisce il contratto attivo nel sistema per un determinato periodo.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Un oggetto ContrattoBean rappresentante il contratto attivo per il periodo specificato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    ContrattoBean getContrattoAttivo(final Date dataInizio, final Date dataFine) throws SQLException;

    /**
     * Restituisce il prezzo di vendita corrente nel sistema.
     *
     * @return Il prezzo di vendita corrente.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    float ottieniPrezzoVendita() throws SQLException;
}
