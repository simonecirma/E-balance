package com.c17.ebalance.ebalance.model.DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenditaDAOImpl implements VenditaDAO {
    private static final Logger logger = Logger.getLogger(ReportDAOImpl.class.getName());
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/ebalance");

        } catch (NamingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public float getEnergiaVendutaPerData(final Date dataInizio, final Date dataFine) throws SQLException {
        float energia = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "SELECT SUM (EnergiaVenduta) "
                    + "AS EnergiaTOTVenduta FROM Vendita "
                    + "WHERE DataVendita BETWEEN ? AND ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, dataInizio);
            ps.setDate(2, dataFine);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                energia = rs.getFloat("EnergiaTOTVenduta");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return energia;
    }

    public float getRicavoTotalePerData(final Date dataInizio, final Date dataFine) throws SQLException {
        float ricavo = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "SELECT SUM (RicavoTotale) "
                    + "AS RicavoTOT FROM Vendita "
                    + "WHERE DataVendita BETWEEN ? AND ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, dataInizio);
            ps.setDate(2, dataFine);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ricavo = rs.getFloat("RicavoTOT");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return ricavo;
    }
}
