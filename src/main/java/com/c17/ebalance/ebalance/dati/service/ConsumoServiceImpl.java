package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ConsumoDAO;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementazione del servizio per la gestione dei dati di consumo nel sistema eBalance.
 * Utilizza un oggetto {@code ConsumoDAO} per l'accesso ai dati di consumo nel database.
 * Fornisce metodi per ottenere i consumi degli edifici, visualizzare lo storico dei consumi e ottenere il consumo per un periodo specifico.
 *
 * @author Il Tuo Nome
 * @version 1.0
 */
public class ConsumoServiceImpl implements ConsumoService {
    private ConsumoDAO consumoDAO = new ConsumoDAOImpl();

    /**
     * Ottiene i consumi degli edifici per il periodo corrente.
     *
     * @param consumoEdifici Un array contenente i consumi degli edifici da aggiornare.
     * @return Un array con i consumi aggiornati degli edifici.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public float[] ottieniConsumiEdifici(float consumoEdifici[]) throws SQLException {
        int numElementi = consumoEdifici.length;
        float consumoAttuale = consumoDAO.ottieniConsumiEdifici();
        if (consumoAttuale != consumoEdifici[numElementi - 1]) {
            for (int i = 1; i < numElementi; i++) {
                consumoEdifici[i - 1] = consumoEdifici[i];
            }
            consumoEdifici[numElementi - 1] = consumoAttuale;
        }
        return consumoEdifici;
    }

    /**
     * Restituisce lo storico dei consumi nel sistema.
     *
     * @return Una lista di oggetti {@code ArchivioConsumoBean}.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException {
        return consumoDAO.visualizzaStoricoConsumi();
    }


    /**
     * Ottiene il consumo totale per un periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Il consumo totale per il periodo specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public float getConsumoPerData(java.sql.Date dataInizio, java.sql.Date dataFine) throws SQLException {
        return consumoDAO.getConsumoPerData(dataInizio, dataFine);
    }
}
