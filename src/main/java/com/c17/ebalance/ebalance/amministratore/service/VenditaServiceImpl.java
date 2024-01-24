package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.DAO.VenditaDAO;
import com.c17.ebalance.ebalance.model.DAO.VenditaDAOImpl;
import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class VenditaServiceImpl implements VenditaService {
    private VenditaDAO venditaDAO = new VenditaDAOImpl();

    @Override
    public List<VenditaBean> getVendite(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getVendite(dataInizio, dataFine);
    }

    @Override
    public float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getRicavoTotalePerData(dataInizio, dataFine);
    }
}
