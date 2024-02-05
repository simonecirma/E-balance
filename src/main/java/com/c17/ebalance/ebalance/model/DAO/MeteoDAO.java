package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce i metodi per l'accesso ai dati meteorologici nel sistema eBalance.
 * Questa interfaccia fornisce operazioni per ottenere, inserire, e manipolare le informazioni meteorologiche.
 */
public interface MeteoDAO {

    /**
     * Restituisce la lista delle condizioni meteorologiche attuali.
     *
     * @return Una lista di oggetti MeteoBean rappresentanti le condizioni meteorologiche attuali.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<MeteoBean> getCondizioniMeteo() throws SQLException;

    /**
     * Inserisce previsioni meteorologiche nel sistema.
     *
     * @param sqlDate           La data delle previsioni.
     * @param orario            L'orario delle previsioni.
     * @param vel               La velocità del vento nelle previsioni.
     * @param prob              La probabilità di precipitazioni nelle previsioni.
     * @param condizioneCasuale Una condizione meteorologica casuale nelle previsioni.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void insertPrevisioni(java.sql.Date sqlDate, int orario, float vel, int prob, String condizioneCasuale) throws SQLException;

    /**
     * Restituisce la lista delle condizioni meteorologiche disponibili.
     *
     * @return Una lista di stringhe rappresentanti le condizioni meteorologiche disponibili.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<String> getCondizione() throws SQLException;

    /**
     * Verifica la presenza di previsioni meteorologiche per una data e un orario specifici.
     *
     * @param sqlDate La data delle previsioni.
     * @param orario  L'orario delle previsioni.
     * @return True se le previsioni sono presenti, altrimenti False.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    boolean verificaPresenza(Date sqlDate, int orario) throws SQLException;

    /**
     * Calcola e restituisce la media giornaliera delle condizioni meteorologiche.
     *
     * @return Una lista di oggetti MeteoBean rappresentanti la media giornaliera delle condizioni meteorologiche.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<MeteoBean> mediaGiornaliera() throws SQLException;

    /**
     * Restituisce il valore di un parametro meteorologico per una data e un orario specifici.
     *
     * @param sqlData La data delle previsioni.
     * @param i       L'indice del parametro desiderato.
     * @return Il valore del parametro.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    int getParametro(Date sqlData, int i) throws SQLException;

    /**
     * Aggiorna l'influenza di una fonte meteorologica specifica su una sorgente.
     *
     * @param id      L'ID della fonte meteorologica.
     * @param sorgente Il nuovo valore di influenza della fonte meteorologica.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void aggiornaInfluenzare(int id, int sorgente) throws  SQLException;
}
