package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import java.sql.SQLException;
import java.util.List;


public interface ContrattoService {
    ContrattoBean visualizzaContratto() throws SQLException;
    List<ContrattoBean> visualizzaStoricoContratti() throws SQLException;
    void aggiornaContratto(ContrattoBean contratto) throws SQLException;
    void aggiungiContratto(ContrattoBean contrattoNuovo) throws SQLException;
    boolean verificaPrimoContratto() throws SQLException;
}

