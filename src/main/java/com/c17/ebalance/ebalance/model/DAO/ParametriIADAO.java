package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce i metodi per l'accesso ai dati relativi ai parametri dell'Intelligenza Artificiale nel sistema eBalance.
 * Questa interfaccia fornisce operazioni per visualizzare parametri, interazioni tra parametri e altre operazioni correlate.
 */
public interface ParametriIADAO {

    /**
     * Restituisce una lista di oggetti ParametriIABean rappresentanti i parametri dell'Intelligenza Artificiale.
     *
     * @return Lista di ParametriIABean contenente i parametri IA.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<ParametriIABean> visualizzaParametri() throws SQLException;

    /**
     * Restituisce una lista di oggetti InteragisceBean rappresentanti le interazioni tra i parametri dell'Intelligenza Artificiale.
     *
     * @return Lista di InteragisceBean contenente le interazioni tra parametri IA.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException;

    /**
     * Restituisce una lista di oggetti InteragisceBean rappresentanti le interazioni tra parametri IA attivi.
     *
     * @return Lista di InteragisceBean contenente le interazioni tra parametri IA attivi.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<InteragisceBean> ottieniParametriAttivi() throws SQLException;

    /**
     * Aggiorna il piano personalizzato dei parametri IA con le nuove preferenze.
     *
     * @param preferenzaSorgente        Preferenza della sorgente di dati.
     * @param percentualeUtilizzoPannelli Percentuale di utilizzo dei pannelli solari.
     * @param percentualeUtilizzoSEN     Percentuale di utilizzo dei sensori SEN.
     * @param prioritaSorgenti          Array di stringhe rappresentanti la priorità delle sorgenti di dati.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void aggiornaPianoPersonalizzato(String preferenzaSorgente, int percentualeUtilizzoPannelli, int percentualeUtilizzoSEN, String[] prioritaSorgenti) throws SQLException;

    /**
     * Aggiorna il piano attivo per l'Intelligenza Artificiale con un nuovo piano specificato.
     *
     * @param piano             Nuovo piano attivo.
     * @param idAmministratore ID dell'amministratore associato al piano.
     * @return True se l'aggiornamento ha avuto successo, altrimenti False.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    boolean aggiornaPianoAttivo(String piano, int idAmministratore) throws SQLException;

    /**
     * Aggiorna la percentuale di utilizzo del SEN nei parametri IA.
     *
     * @param percentualeAggiunta Percentuale da aggiungere alla percentuale di utilizzo SEN corrente.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void aggiornaPercentualeSEN(int percentualeAggiunta) throws SQLException;
}
