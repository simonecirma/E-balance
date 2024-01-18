package com.c17.ebalance.ebalance.dati.service;

import java.sql.Date;
import java.sql.SQLException;

public interface VenditaService {
    float getEnergiaVendutaPerData(Date dataInizio, Date dataFine) throws SQLException;

    float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException;
}
