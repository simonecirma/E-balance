package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati per la produzione di energia.
 */
public interface ProduzioneDAO {

    /**
     * Restituisce una lista di bean contenenti informazioni sulla produzione complessiva di energia.
     *
     * @return Lista di ArchivioProduzioneBean.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException;

    /**
     * Restituisce una lista di bean contenenti informazioni sulla produzione per ciascuna sorgente energetica.
     *
     * @return Lista di SorgenteBean.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException;

    /**
     * Ottiene la produzione totale del Servizio Elettrico Nazionale (SEN).
     *
     * @return Produzione totale del SEN.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    float ottieniProduzioneSEN() throws SQLException;

    /**
     * Ottiene la produzione totale complessiva.
     *
     * @return Produzione totale complessiva.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    float ottieniProduzioneProdotta() throws SQLException;

    /**
     * Restituisce una lista di bean contenenti informazioni sui tipi di sorgenti energetiche disponibili.
     *
     * @return Lista di TipoSorgenteBean.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException;

    /**
     * Simula la produzione di energia per una sorgente energetica in una data specifica.
     *
     * @param idSorgente         L'ID della sorgente energetica.
     * @param produzioneSimulata La quantità di energia da simulare.
     * @param data               La data della simulazione.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    void simulaProduzione(int idSorgente, float produzioneSimulata, Date data) throws SQLException;

    /**
     * Ottiene il numero totale di sorgenti energetiche.
     *
     * @return Numero totale di sorgenti energetiche.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    int ottieniSorgenti() throws SQLException;

    /**
     * Simula la produzione di energia necessaria dal Servizio Elettrico Nazionale (SEN) in una data specifica.
     *
     * @param produzioneNecessaria La quantità di energia necessaria dal SEN.
     * @param data                 La data della simulazione.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    void simulaProduzioneSEN(float produzioneNecessaria, Date data) throws SQLException;

    /**
     * Calcola la quantità di energia rinnovabile prodotta nel periodo compreso tra due date specifiche.
     *
     * @param dataInizio Data di inizio periodo.
     * @param dataFine   Data di fine periodo.
     * @return Quantità di energia rinnovabile prodotta nel periodo specificato.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    float energiaRinnovabileProdottaPerData(final Date dataInizio, final Date dataFine) throws SQLException;
}
