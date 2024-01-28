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

public class IAServiceImpl implements IAService {
    private ParametriIADAO parametriIA = new ParametriIADAOImpl();
    @Override
    public List<ParametriIABean> visualizzaParametri() throws SQLException {
        return parametriIA.visualizzaParametri();
    }

    @Override
    public List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException {
        return parametriIA.visualizzaInterazioneParametri();
    }

    @Override
    public List<InteragisceBean> ottieniParametriAttivi() throws SQLException {
        return parametriIA.ottieniParametriAttivi();
    }

    @Override
    public void aggiornaPianoPersonalizzato(String preferenzaSorgente, int percentualeUtilizzoPannelli, int percentualeUtilizzoSEN, String sortableListData) throws SQLException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, String>>>(){}.getType();
        List<Map<String, String>> priorities = gson.fromJson(sortableListData, type);
        String prioritaSorgenti[] = new String[10];
        int i = 0;
        for (Map<String, String> priority : priorities) {
            prioritaSorgenti[i] = priority.get("text");
            i++;
        }
        parametriIA.aggiornaPianoPersonalizzato(preferenzaSorgente, percentualeUtilizzoPannelli, percentualeUtilizzoSEN, prioritaSorgenti);
    }

    @Override
    public boolean aggiornaPianoAttivo(String piano, int idAmministratore) throws SQLException {
        return parametriIA.aggiornaPianoAttivo(piano, idAmministratore);
    }
}
