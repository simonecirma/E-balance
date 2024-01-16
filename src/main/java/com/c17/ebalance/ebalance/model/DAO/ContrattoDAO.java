package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class ContrattoDAO {

    private static Logger logger = Logger.getLogger(AmministratoreDAO.class.getName());

    private static final String TABLE_NAME_CONTRATTO = "Contratto";

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

    public static ContrattoBean visualizzaContratto() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM "  + TABLE_NAME_CONTRATTO + " ORDER BY DataSottoscrizione DESC LIMIT 1";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bean.setIdContratto(rs.getInt("IdContratto"));
                bean.setNomeEnte(rs.getString("NomeEnte"));
                bean.setConsumoMedioAnnuale(rs.getFloat("ConsumoMedioAnnuale"));
                bean.setCostoMedioUnitario(rs.getFloat("CostoMedioUnitario"));
                bean.setDataSottoscrizione(rs.getDate("DataSottoscrizione"));
                bean.setDurata(rs.getInt("Durata"));
                bean.setPrezzoVendita(rs.getFloat("PrezzoVendita"));
                bean.setIdAmministatore(rs.getInt("IdAmministratore"));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }

    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<ContrattoBean> contratti = new ArrayList<>();

        try {
            con = ds.getConnection();
            String query = "SELECT * FROM "  + TABLE_NAME_CONTRATTO + " WHERE IdContratto < (SELECT MAX(IdContratto) "
                    + "FROM " + TABLE_NAME_CONTRATTO + ") ORDER BY IdContratto DESC";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ContrattoBean bean = new ContrattoBean();
                bean.setIdContratto(rs.getInt("IdContratto"));
                bean.setNomeEnte(rs.getString("NomeEnte"));
                bean.setConsumoMedioAnnuale(rs.getFloat("ConsumoMedioAnnuale"));
                bean.setCostoMedioUnitario(rs.getFloat("CostoMedioUnitario"));
                bean.setDataSottoscrizione(rs.getDate("DataSottoscrizione"));
                bean.setDurata(rs.getInt("Durata"));
                bean.setPrezzoVendita(rs.getFloat("PrezzoVendita"));
                bean.setIdAmministatore(rs.getInt("IdAmministratore"));
                contratti.add(bean);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return contratti;
    }

    public ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();
        try {
            con = ds.getConnection();
            String query = "UPDATE Contratto "
                    + "SET NomeEnte= ?, ConsumoMedioAnnuale=?, CostoMedioUnitario=?, "
                    + "DataSottoscrizione=?, Durata=?, PrezzoVendita=?, IdAmministratore=? "
                    + "WHERE IdContratto=?";
            ps = con.prepareStatement(query);

            ps.setString(1, contratto.getNomeEnte());
            ps.setFloat(2, contratto.getConsumoMedioAnnuale());
            ps.setFloat(3, contratto.getCostoMedioUnitario());
            ps.setDate(4, (Date) contratto.getDataSottoscrizione());
            ps.setInt(5, contratto.getDurata());
            ps.setFloat(6, contratto.getPrezzoVendita());
            ps.setInt(7, contratto.getIdAmministatore());
            ps.setInt(8, contratto.getIdContratto());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                bean = contratto;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }

    public ContrattoBean aggiungiContratto(final ContrattoBean contratto) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();

        try {
            con = ds.getConnection();
            String query = "INSERT INTO Contratto( NomeEnte, ConsumoMedioAnnuale, CostoMedioUnitario, "
                    + "DataSottoscrizione, Durata, PrezzoVendita, IdAmministratore)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, contratto.getNomeEnte());
            ps.setFloat(2, contratto.getConsumoMedioAnnuale());
            ps.setFloat(3, contratto.getCostoMedioUnitario());
            ps.setDate(4, (Date) contratto.getDataSottoscrizione());
            ps.setInt(5, contratto.getDurata());
            ps.setFloat(6, contratto.getPrezzoVendita());
            ps.setInt(7, contratto.getIdAmministatore());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                bean = contratto;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }
}
