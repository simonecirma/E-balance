package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
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

public class ParametriIADAOImpl implements ParametriIADAO {

    private static Logger logger = Logger.getLogger(ParametriIADAOImpl.class.getName());
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

    private static final String TABLE_NAME_PARAMETRI = "ParametriIA";
    private static final String TABLE_NAME_INTERAGISCE = "Interagisce";
    @Override
    public List<ParametriIABean> visualizzaParametri() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ParametriIABean> parametri = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_PARAMETRI;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ParametriIABean bean = new ParametriIABean();
                bean.setIdParametro(resultSet.getInt("IdParametro"));
                bean.setPiano(resultSet.getString("Piano"));
                bean.setFlagAttivazioneParametro(resultSet.getBoolean("FlagAttivazioneParametro"));
                bean.setIdAmministratore(resultSet.getInt("IdAmministratore"));
                parametri.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return parametri;
    }

    @Override
    public List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<InteragisceBean> interagisce = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_INTERAGISCE;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                InteragisceBean bean = new InteragisceBean();
                bean.setIdParametro(resultSet.getInt("IdParametro"));
                bean.setTipoSorgente(resultSet.getString("TipoSorgente"));
                bean.setFlagPreferenzaSorgente(resultSet.getBoolean("FlagPreferenzaSorgente"));
                bean.setPercentualeUtilizzoSorgente(resultSet.getInt("PercentualeUtilizzoSorgente"));
                bean.setPrioritaSorgente(resultSet.getInt("PrioritaSorgente"));
                interagisce.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return interagisce;
    }

    @Override
    public List<InteragisceBean> ottieniParametriAttivi() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<InteragisceBean> parametriAttivi = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_INTERAGISCE + " WHERE idParametro IN (SELECT idparametro FROM "
                + TABLE_NAME_PARAMETRI +" WHERE FlagAttivazioneParametro = TRUE) ORDER BY PrioritaSorgente";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                InteragisceBean bean = new InteragisceBean();
                bean.setIdParametro(resultSet.getInt("IdParametro"));
                bean.setTipoSorgente(resultSet.getString("TipoSorgente"));
                bean.setFlagPreferenzaSorgente(resultSet.getBoolean("FlagPreferenzaSorgente"));
                bean.setPercentualeUtilizzoSorgente(resultSet.getInt("PercentualeUtilizzoSorgente"));
                bean.setPrioritaSorgente(resultSet.getInt("PrioritaSorgente"));
                parametriAttivi.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return parametriAttivi;
    }
}
