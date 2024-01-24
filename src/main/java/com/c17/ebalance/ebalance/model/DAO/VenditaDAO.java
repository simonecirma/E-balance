package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface VenditaDAO {
    List<VenditaBean> getVendite(final Date dataInizio, final Date dataFine) throws SQLException;
    float getRicavoTotalePerData(final Date dataInizio, final Date dataFine) throws SQLException;
}
