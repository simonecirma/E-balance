package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.BatteriaBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class BatteriaDAOImpl implements BatteriaDAO {

    private static Logger logger = Logger.getLogger(BatteriaDAOImpl.class.getName());
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
    private static final String TABLE_NAME_BATTERIA = "Batteria";
    private static final String TABLE_NAME_UTILIZZA= "Utilizza";
    private static final String TABLE_NAME_CARICARE= "Caricare";


    public List<BatteriaBean> visualizzaBatteria() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BatteriaBean> batteria = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_BATTERIA;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BatteriaBean bean = new BatteriaBean();
                bean.setIdBatteria(resultSet.getInt("IdBatteria"));
                bean.setFlagStatoBatteria(resultSet.getBoolean("FlagStatoBatteria"));
                bean.setCapacitaMax(resultSet.getFloat("CapacitaMax"));
                bean.setPercentualeCarica(resultSet.getFloat("PercentualeCarica"));
                batteria.add(bean);
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
        return batteria;
    }

    @Override
    public float ottieniPercentualeBatterie() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        float percentuale = 0.02f;
        String selectSQL = "SELECT  ROUND(SUM(PercentualeCarica)/3,2) AS Percentuale FROM " + TABLE_NAME_BATTERIA
                + " WHERE FlagStatoBatteria = 1 ";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                percentuale = resultSet.getFloat("Percentuale");
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
        return percentuale;
    }

    public int  ottieniNumBatterieAttive() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int NumBatterie = 0;
        String selectSQL = "SELECT COUNT(IdBatteria) AS NumBatterie FROM " + TABLE_NAME_BATTERIA + " WHERE FlagStatoBatteria = true";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                NumBatterie = resultSet.getInt("NumBatterie");
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
        return NumBatterie;
    }

    @Override
    public void aggiornaConsumiBatteria(float consumoOrario, int idEdificio) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME_BATTERIA + " SET PercentualeCarica  = (((PercentualeCarica*(CapacitaMax/100)) "
                + "+ (?))/(CapacitaMax/100)) WHERE IdBatteria IN (SELECT IdBatteria FROM " + TABLE_NAME_UTILIZZA + " WHERE IdEdificio = ?)";


        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setFloat(1, consumoOrario);
            preparedStatement.setInt(2, idEdificio);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    }

    @Override
    public void aggiornaProduzioneBatteria(float produzioneOraria, int idSorgente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME_BATTERIA + " SET PercentualeCarica  = (((PercentualeCarica*(CapacitaMax/100)) "
                + "+ (?))/(CapacitaMax/100)) WHERE IdBatteria IN (SELECT IdBatteria FROM " + TABLE_NAME_CARICARE + " WHERE IdSorgente = ?)";


        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setFloat(1, produzioneOraria);
            preparedStatement.setInt(2, idSorgente);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    }

}
