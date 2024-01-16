package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import java.sql.SQLException;
import java.util.List;

public interface ContrattoDAO {
    public ContrattoBean visualizzaContratto() throws SQLException;
    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException;
    public ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException;
    public ContrattoBean aggiungiContratto(final ContrattoBean contratto) throws SQLException;
    public boolean verificaPrimoContratto() throws SQLException;

}
