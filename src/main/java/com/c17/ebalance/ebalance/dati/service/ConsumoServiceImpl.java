package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ConsumoDAO;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ConsumoServiceImpl implements ConsumoService {
    private ConsumoDAO consumoDAO = new ConsumoDAOImpl();

    @Override
    public List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException {
        return consumoDAO.visualizzaConsumo();
    }

    @Override
    public float ottieniConsumiEdifici() throws SQLException {
        return consumoDAO.ottieniConsumiEdifici();
    }

    @Override
    public List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException {
        return consumoDAO.visualizzaStoricoConsumi();
    }


    public float getConsumoPerData(java.sql.Date dataInizio, java.sql.Date dataFine) throws SQLException{
        return consumoDAO.getConsumoPerData(dataInizio, dataFine);
    }
}
