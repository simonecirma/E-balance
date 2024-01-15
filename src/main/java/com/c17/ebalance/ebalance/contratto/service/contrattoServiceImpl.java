package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.contrattoBean;
import com.c17.ebalance.ebalance.model.DAO.contrattoDAO;

import java.sql.SQLException;
import java.util.List;

public class contrattoServiceImpl implements contrattoService{
    private contrattoDAO contrattoDao=new contrattoDAO();
    @Override
    public contrattoBean visualizzaContratto() throws SQLException{
        return contrattoDao.visualizzaContratto();
    }

    @Override
    public List<contrattoBean> visualizzaStoricoContratti() throws SQLException {
        return contrattoDao.visualizzaStoricoContratti();
    }

    @Override
    public contrattoBean aggiornaContratto(contrattoBean contratto) throws SQLException {
        return contrattoDao.aggiornaContratto(contratto);
    }

    @Override
    public contrattoBean aggiungiContratto(contrattoBean contratto) throws SQLException {
        return contrattoDao.aggiungiContratto(contratto);
    }
}
