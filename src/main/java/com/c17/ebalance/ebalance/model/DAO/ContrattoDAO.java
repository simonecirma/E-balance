package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ContrattoDAO {
    ContrattoBean visualizzaContratto() throws SQLException;

    List<ContrattoBean> visualizzaStoricoContratti() throws SQLException;

    ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException;

    ContrattoBean aggiungiContratto(final ContrattoBean contratto) throws SQLException;

    boolean verificaPrimoContratto() throws SQLException;

    ContrattoBean getContrattoAttivo(final Date dataInizio, final Date dataFine) throws SQLException;

    float ottieniPrezzoVendita() throws SQLException;
}
