package com.c17.ebalance.ebalance.IA.service;

import com.c17.ebalance.ebalance.model.DAO.ParametriIADAO;
import com.c17.ebalance.ebalance.model.DAO.ParametriIADAOImpl;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Implementazione del servizio IA (Intelligenza Artificiale) che fornisce operazioni
 * per la gestione dei parametri IA e delle interazioni con le sorgenti nel sistema eBalance.
 */
public class IAServiceImpl implements IAService {
    private ParametriIADAO parametriIA = new ParametriIADAOImpl();


    /**
     * Restituisce la lista dei parametri IA.
     *
     * @return La lista dei parametri IA.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<ParametriIABean> visualizzaParametri() throws SQLException {
        return parametriIA.visualizzaParametri();
    }

    /**
     * Restituisce la lista delle interazioni tra parametri IA e le sorgenti.
     *
     * @return La lista delle interazioni tra parametri IA e le sorgenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException {
        return parametriIA.visualizzaInterazioneParametri();
    }

    /**
     * Restituisce la lista dei parametri IA attivi.
     *
     * @return La lista dei parametri IA attivi.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<InteragisceBean> ottieniParametriAttivi() throws SQLException {
        return parametriIA.ottieniParametriAttivi();
    }

    /**
     * Aggiorna il piano personalizzato dell'utente con le preferenze specificate.
     *
     * @param preferenzaSorgente       La preferenza della sorgente (es. Pannello Fotovoltaico, Servizio Elettrico Nazionale, etc.).
     * @param percentualeUtilizzoPannelli La percentuale di utilizzo dei pannelli fotovoltaici.
     * @param percentualeUtilizzoSEN  La percentuale di utilizzo del Servizio Elettrico Nazionale.
     * @param sortableListData        I dati relativi all'ordinamento della lista.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public void aggiornaPianoPersonalizzato(String preferenzaSorgente, int percentualeUtilizzoPannelli, int percentualeUtilizzoSEN, String sortableListData) throws SQLException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> priorities = gson.fromJson(sortableListData, type);
        String prioritaSorgenti[] = new String[10];
        int i = 0;
        for (Map<String, String> priority : priorities) {
            prioritaSorgenti[i] = priority.get("text");
            i++;
        }
        parametriIA.aggiornaPianoPersonalizzato(preferenzaSorgente, percentualeUtilizzoPannelli, percentualeUtilizzoSEN, prioritaSorgenti);
    }

    /**
     * Aggiorna il piano attivo dei parametri IA effettuato da un amministratore specifico.
     *
     * @param piano          Il piano attivo da impostare.
     * @param idAmministratore L'ID dell'amministratore a cui associare il piano.
     * @return true se l'aggiornamento ha avuto successo, false altrimenti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public boolean aggiornaPianoAttivo(String piano, int idAmministratore) throws SQLException {
        return parametriIA.aggiornaPianoAttivo(piano, idAmministratore);
    }
}
