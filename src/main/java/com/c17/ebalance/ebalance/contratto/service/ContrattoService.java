package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.contrattoBean;

import java.sql.*;
import java.util.*;

public interface contrattoService {
    contrattoBean visualizzaContratto() throws SQLException;
    List<contrattoBean> visualizzaStoricoContratti() throws SQLException;
    contrattoBean aggiornaContratto(contrattoBean contratto) throws SQLException;
    contrattoBean aggiungiContratto(contrattoBean contratto) throws SQLException;
}

