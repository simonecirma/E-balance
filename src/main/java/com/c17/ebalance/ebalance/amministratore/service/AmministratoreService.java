package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;
import java.util.List;

public interface AmministratoreService {
    Boolean verificaSuperAdmin() throws SQLException;
    List<AmministratoreBean> visualizzaAmministratori() throws SQLException;
    AmministratoreBean aggiornaAmministratore(AmministratoreBean amministratore) throws SQLException;
    void aggiungiAmministratore(AmministratoreBean amministratore) throws SQLException;
    AmministratoreBean getById(int id) throws SQLException;
    void rimuoviAmministratore(int idAmministratore) throws SQLException;

    boolean verificaPresenzaEmail(String email) throws SQLException;
}
