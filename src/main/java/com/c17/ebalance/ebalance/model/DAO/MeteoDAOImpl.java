package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeteoDAOImpl implements MeteoDAO {
    Calendar calendario = Calendar.getInstance();
    Date data;
    Random random = new Random();
    private static Logger logger = Logger.getLogger(ContrattoDAOImpl.class.getName());
    private static final String TABLE_NAME_METEO = "Meteo";

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
    public void insertPrevisioni() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String[] condizioni = {"Nevoso", "Nuvoloso", "Piovoso", "Sereno", "Soleggiato", "Ventilato"};
        try{
            con = ds.getConnection();
            data = calendario.getTime();
            java.sql.Date sqlDate = new java.sql.Date(data.getTime());
            java.sql.Time sqlTime = new java.sql.Time(data.getTime());
            for(int i=0; i<12; i++){
                float vel = random.nextFloat();
                int prob = random.nextInt(101);
                int indiceCasuale = random.nextInt(condizioni.length-1);
                String condizioneCasuale = condizioni[indiceCasuale];

                String query = "INSERT INTO " + TABLE_NAME_METEO + "(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)" +
                        "VALUES(?, ?, ?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setDate(1, sqlDate);
                ps.setTime(2, sqlTime);
                ps.setFloat(3, vel);
                ps.setInt(4, prob);
                ps.setString(5, condizioneCasuale);
                ps.executeUpdate();

                calendario.add(Calendar.HOUR_OF_DAY, 6);
                data = calendario.getTime();
                sqlDate = new java.sql.Date(data.getTime());
                sqlTime = new java.sql.Time(data.getTime());
            }
            Thread.sleep(10000); // Ritardo di 10 secondi
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
}