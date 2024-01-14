package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.amministratoreBean;

import java.sql.SQLException;
import java.util.List;

public interface amministratoreService {
    List<amministratoreBean> visualizzaAmministratori() throws SQLException;

    amministratoreBean aggiornaAmministratore(amministratoreBean amministratore) throws SQLException;
    void aggiungiAmministratore(amministratoreBean amministratore) throws SQLException;

}
