package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface VenditaService {
    List<VenditaBean> getVendite(Date dataInizio, Date dataFine) throws SQLException;

    float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException;
}
