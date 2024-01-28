package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeteoDAOImpl implements MeteoDAO {
    Calendar calendario = Calendar.getInstance();
    Date data;
    Random random = new Random();
    private static Logger logger = Logger.getLogger(ContrattoDAOImpl.class.getName());
    private static final String TABLE_NAME_METEO = "Meteo";
    private static final String TABLE_NAME_CONDIZIONI = "CondizioneMeteo";

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

    public List<MeteoBean> getCondizioniMeteo() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<MeteoBean> condizioni = new ArrayList<>();
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_METEO + " WHERE DataRilevazione >= ? ORDER BY DataRilevazione ASC, OraRilevazione ASC";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MeteoBean bean = new MeteoBean();
                bean.setIdMeteo(rs.getInt("IdMeteo"));
                bean.setDataRilevazione(rs.getDate("DataRilevazione"));
                bean.setOraRilevazione(rs.getTime("OraRilevazione"));
                bean.setVelocitaVento(rs.getFloat("VelocitaVento"));
                bean.setProbabilitaPioggia(rs.getInt("ProbabilitaPioggia"));
                bean.setCondizioniMetereologiche(rs.getString("CondizioniMetereologiche"));
                condizioni.add(bean);
            }
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
        return condizioni;
    }

    @Override
    public void insertPrevisioni(java.sql.Date sqlDate, Time sqlTime, float vel, int prob, String condizioneCasuale) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = ds.getConnection();

            String query = "INSERT INTO " + TABLE_NAME_METEO + "(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)" +
                        "VALUES(?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ps.setTime(2, sqlTime);
            ps.setFloat(3, vel);
            ps.setInt(4, prob);
            ps.setString(5, condizioneCasuale);
            ps.executeUpdate();

        }catch (Exception e) {
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

    @Override
    public List<String> getCondizione() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<String> condizioni=new ArrayList<>();
        try{
            con = ds.getConnection();
            String query ="SELECT * FROM " + TABLE_NAME_CONDIZIONI;
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                condizioni.add(rs.getString("Condizione"));
            }
        }catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return condizioni;
    }
}