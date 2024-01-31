package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

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

    private static final String TABLE_NAME_VENDITA = "Vendita";

    public List<VenditaBean> getVendite(final Date dataInizio, final Date dataFine) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<VenditaBean> vendite = new ArrayList<>();
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_VENDITA
                    + " WHERE DataVendita BETWEEN ? AND ? ";
            ps = con.prepareStatement(query);
            ps.setDate(1, dataInizio);
            ps.setDate(2, dataFine);
            rs = ps.executeQuery();
            while (rs.next()) {
                VenditaBean bean = new VenditaBean();
                bean.setIdVendita(rs.getInt("IdAmministratore"));
                bean.setEnergiaVenduta(rs.getFloat("EnergiaVenduta"));
                bean.setDataVendita(rs.getDate("DataVendita"));
                bean.setRicavoTotale(rs.getFloat("RicavoTotale"));
                bean.setIdAmministratore(rs.getInt("IdAmministratore"));
                vendite.add(bean);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return vendite;
    }

    public float getRicavoTotalePerData(final Date dataInizio, final Date dataFine) throws SQLException {
        float ricavo = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT SUM(RicavoTotale) "
                    + "AS RicavoTOT FROM " + TABLE_NAME_VENDITA
                    + " WHERE DataVendita BETWEEN ? AND ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, dataInizio);
            ps.setDate(2, dataFine);
            rs = ps.executeQuery();
            while (rs.next()) {
                ricavo = rs.getFloat("RicavoTOT");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ricavo;
    }

    @Override
    public void effetuaVendita(VenditaBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String insertSQL = " INSERT INTO " + TABLE_NAME_VENDITA
                + " (EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)\n"
                + " VALUES(?, ?, ?, ?) ";
        try {
            con = ds.getConnection();
            ps = con.prepareStatement(insertSQL);
            ps.setFloat(1, bean.getEnergiaVenduta());
            ps.setDate(2, bean.getDataVendita());
            ps.setFloat(3, bean.getRicavoTotale());
            ps.setFloat(4, bean.getIdAmministratore());
            ps.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
