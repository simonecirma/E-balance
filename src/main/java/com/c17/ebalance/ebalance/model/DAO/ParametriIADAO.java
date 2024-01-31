package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;

import java.sql.SQLException;
import java.util.List;

public interface ParametriIADAO {
    List<ParametriIABean> visualizzaParametri() throws SQLException;

    List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException;

    List<InteragisceBean> ottieniParametriAttivi() throws SQLException;

    void aggiornaPianoPersonalizzato(String preferenzaSorgente, int percentualeUtilizzoPannelli, int percentualeUtilizzoSEN, String[] prioritaSorgenti) throws SQLException;

    boolean aggiornaPianoAttivo(String piano, int idAmministratore) throws SQLException;

    void aggiornaPercentualeSEN(int percentualeAggiunta) throws SQLException;
}
