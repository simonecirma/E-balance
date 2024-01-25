package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.MeteoDAO;
import com.c17.ebalance.ebalance.model.DAO.MeteoDAOImpl;
import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.SQLException;
import java.util.List;

public class MeteoServiceImpl implements MeteoService{
    private MeteoDAO meteoDAO = new MeteoDAOImpl();

    @Override
    public List<MeteoBean> getCondizioniMeteo() throws SQLException {
        return meteoDAO.getCondizioniMeteo();
    }
}
