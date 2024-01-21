package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.VenditaDAO;
import com.c17.ebalance.ebalance.model.DAO.VenditaDAOImpl;

import java.sql.Date;
import java.sql.SQLException;

public class VenditaServiceImpl implements VenditaService {
    public VenditaDAO venditaDAO = new VenditaDAOImpl();

    @Override
    public float getEnergiaVendutaPerData(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getEnergiaVendutaPerData(dataInizio, dataFine);
    }

    @Override
    public float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getRicavoTotalePerData(dataInizio, dataFine);
    }
}
