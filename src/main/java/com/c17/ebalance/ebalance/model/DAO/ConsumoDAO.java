package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati relativi al consumo energetico nel sistema eBalance.
 * Le implementazioni di questa interfaccia devono gestire l'interazione con il database.
 */
public interface ConsumoDAO {

    /**
     * Restituisce una lista di consumi degli edifici presenti nel sistema.
     *
     * @return Una lista di oggetti ConsumoEdificioBean.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException;

    /**
     * Restituisce la somma totale dei consumi energetici di tutti gli edifici nel sistema.
     *
     * @return La somma totale dei consumi energetici.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    float ottieniConsumiEdifici() throws SQLException;

    /**
     * Restituisce una lista di archivi dei consumi energetici nel sistema.
     *
     * @return Una lista di oggetti ArchivioConsumoBean.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException;

    /**
     * Simula il consumo energetico di un edificio in una data specifica.
     *
     * @param consumoOrario Il consumo energetico orario simulato.
     * @param IdEdificio    L'identificatore dell'edificio su cui simulare il consumo.
     * @param data          La data in cui simulare il consumo.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void simulaConsumo(float consumoOrario, int IdEdificio, Date data) throws SQLException;

    /**
     * Restituisce il numero totale di edifici nel sistema.
     *
     * @return Il numero totale di edifici.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    int ottieniNumEdifici() throws SQLException;

    /**
     * Restituisce il consumo energetico totale nel sistema per un periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Il consumo energetico totale nel sistema per il periodo specificato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    float getConsumoPerData(final Date dataInizio, final Date dataFine) throws SQLException;

}
