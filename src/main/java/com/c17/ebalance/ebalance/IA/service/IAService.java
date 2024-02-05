package com.c17.ebalance.ebalance.IA.service;


import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;

import java.sql.SQLException;
import java.util.List;


/**
 * Interfaccia che definisce i servizi relativi all'Intelligenza Artificiale (IA) nel sistema eBalance.
 * Questa interfaccia fornisce metodi per ottenere informazioni sui parametri IA, interazioni con le sorgenti, parametri attivi IA,
 * e per aggiornare i piani personalizzati e attivi.
 */
public interface IAService {

    /**
     * Restituisce la lista dei parametri IA.
     *
     * @return La lista dei parametri IA.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<ParametriIABean> visualizzaParametri() throws SQLException;

    /**
     * Restituisce la lista delle interazioni tra parametri IA e le sorgenti.
     *
     * @return La lista delle interazioni tra parametri IA e le sorgenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException;


    /**
     * Restituisce la lista dei parametri IA attivi.
     *
     * @return La lista dei parametri IA attivi.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<InteragisceBean> ottieniParametriAttivi() throws SQLException;


    /**
     * Aggiorna il piano personalizzato dell'amministratore.
     *
     * @param preferenzaSorgente       La preferenza della sorgente (es. Pannello Fotovoltaico, Servizio Elettrico Nazionale, etc.).
     * @param percentualeUtilizzoPannelli La percentuale di utilizzo dei pannelli fotovoltaici.
     * @param percentualeUtilizzoSEN  La percentuale di utilizzo del Servizio Elettrico Nazionale.
     * @param sortableListData        I dati relativi all'ordinamento della lista.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    void aggiornaPianoPersonalizzato(String preferenzaSorgente, int percentualeUtilizzoPannelli, int percentualeUtilizzoSEN, String sortableListData) throws SQLException;

    /**
     * Aggiorna il piano attivo dei Parametri IA effettuato da un amministratore specifico.
     *
     * @param piano          Il piano attivo da impostare.
     * @param idAmministratore L'ID dell'amministratore a cui associare il piano.
     * @return true se l'aggiornamento ha avuto successo, false altrimenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    boolean aggiornaPianoAttivo(String piano, int idAmministratore) throws SQLException;
}
