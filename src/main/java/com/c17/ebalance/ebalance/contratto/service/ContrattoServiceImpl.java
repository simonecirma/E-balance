package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.contrattoBean;
import com.c17.ebalance.ebalance.model.DAO.contrattoDAO;

import java.sql.SQLException;
import java.util.List;

public class ContrattoServiceImpl implements ContrattoService {
    private contrattoDAO contrattoDao = new contrattoDAO();
    @Override
    public contrattoBean visualizzaContratto() throws SQLException {
        return contrattoDAO.visualizzaContratto();
    }

    @Override
    public List<contrattoBean> visualizzaStoricoContratti() throws SQLException {
        return contrattoDao.visualizzaStoricoContratti();
    }

    @Override
    public contrattoBean aggiornaContratto(final contrattoBean contratto) throws SQLException {
        return contrattoDao.aggiornaContratto(contratto);
    }

    @Override
    public contrattoBean aggiungiContratto(final contrattoBean contratto) throws SQLException {
        return contrattoDao.aggiungiContratto(contratto);
    }
}
