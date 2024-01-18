package com.c17.ebalance.ebalance.model.DAO;

import java.sql.Date;
import java.sql.SQLException;

public interface VenditaDAO {
    public float getEnergiaVendutaPerData(final Date dataInizio, final Date dataFine) throws SQLException;
    public float getRicavoTotalePerData(final Date dataInizio, final Date dataFine) throws SQLException;
}
