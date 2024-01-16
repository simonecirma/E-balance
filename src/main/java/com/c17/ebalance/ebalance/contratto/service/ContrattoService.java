package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.contrattoBean;

import java.sql.SQLException;
import java.util.List;


public interface ContrattoService {
    contrattoBean visualizzaContratto() throws SQLException;
    List<contrattoBean> visualizzaStoricoContratti() throws SQLException;
    contrattoBean aggiornaContratto(contrattoBean contratto) throws SQLException;
    contrattoBean aggiungiContratto(contrattoBean contratto) throws SQLException;
}

