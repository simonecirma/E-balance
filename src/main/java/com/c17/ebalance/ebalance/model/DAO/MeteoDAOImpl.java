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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeteoDAOImpl implements MeteoDAO {
    private static Logger logger = Logger.getLogger(ContrattoDAOImpl.class.getName());
    private static final String TABLE_NAME_CONTRATTO = "Meteo";

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
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_CONTRATTO + " ORDER BY DataRilevazione DESC, OraRilevazione DESC";
            ps = con.prepareStatement(query);
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
}