package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import com.c17.ebalance.ebalance.model.DAO.ContrattoDAO;

import java.sql.SQLException;
import java.util.List;

public class ContrattoServiceImpl implements ContrattoService {
    private ContrattoDAO contrattoDao = new ContrattoDAO();
    @Override
    public ContrattoBean visualizzaContratto() throws SQLException {
        return ContrattoDAO.visualizzaContratto();
    }

    @Override
    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException {
        return contrattoDao.visualizzaStoricoContratti();
    }

    @Override
    public ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException {
        return contrattoDao.aggiornaContratto(contratto);
    }

    @Override
    public ContrattoBean aggiungiContratto(final ContrattoBean contratto) throws SQLException {
        return contrattoDao.aggiungiContratto(contratto);
    }
}
