package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.BatteriaBean;
import com.c17.ebalance.ebalance.model.entity.PercentualeBatteriaBean;
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
    private static final String TABLE_NAME_UTILIZZA = "Utilizza";
    private static final String TABLE_NAME_CARICARE = "Caricare";


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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return batteria;
    }

    @Override
    public float ottieniPercentualeBatterie() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        PercentualeBatteriaBean percentuale = new PercentualeBatteriaBean();
        String selectSQL = "SELECT  ROUND(SUM(PercentualeCarica)/3,2) AS Percentuale FROM " + TABLE_NAME_BATTERIA
                + " WHERE FlagStatoBatteria = 1 ";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                percentuale.setPercentualeBatteria(resultSet.getFloat("Percentuale"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return percentuale.getPercentualeBatteria();
    }

    public int ottieniNumBatterieAttive() throws SQLException {
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return NumBatterie;
    }

    @Override
    public float aggiornaBatteria(float energia, int numBatterie) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;

        float percentualeCaricaAttuale = 0.00f;
        float capacitaMax = 0.00f;
        float percentualeNuova = 0.00f;
        float percentualeEccesso = 0.00f;

        String selectSQL = "SELECT PercentualeCarica, CapacitaMax FROM " + TABLE_NAME_BATTERIA + " WHERE IdBatteria = ? AND FlagStatoBatteria = 1";
        String updateSQL = "UPDATE " + TABLE_NAME_BATTERIA + " SET PercentualeCarica  = ? WHERE IdBatteria = ?";


        try {
            connection = ds.getConnection();
            for (int i = 1; i <= numBatterie; i++) {
                preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setFloat(1, i);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    percentualeCaricaAttuale = resultSet.getFloat("PercentualeCarica");
                    capacitaMax = resultSet.getFloat("CapacitaMax");
                }

                percentualeNuova = (((percentualeCaricaAttuale * (capacitaMax / 100)) + (energia)) / (capacitaMax / 100));
                if (percentualeNuova < 0) {
                    percentualeEccesso = percentualeNuova;
                    percentualeNuova = 0;
                } else if (percentualeNuova > 100) {
                    percentualeEccesso = percentualeNuova - 100;
                    percentualeNuova = 100;
                }

                preparedStatement2 = connection.prepareStatement(updateSQL);
                preparedStatement2.setFloat(1, percentualeNuova);
                preparedStatement2.setInt(2, i);
                preparedStatement2.executeUpdate();


                if (percentualeEccesso != 0.00f) {
                    for (int j = 1; j <= numBatterie; j++) {
                        if (j != i) {
                            preparedStatement3 = connection.prepareStatement(selectSQL);
                            preparedStatement3.setFloat(1, j);
                            resultSet = preparedStatement3.executeQuery();
                            while (resultSet.next()) {
                                percentualeCaricaAttuale = resultSet.getFloat("PercentualeCarica");
                                capacitaMax = resultSet.getFloat("CapacitaMax");
                            }

                            float nuovaPercentuale = 0.00f;

                            if (percentualeCaricaAttuale < 99 && percentualeCaricaAttuale > 1) {
                                if (percentualeEccesso > 0) {
                                    nuovaPercentuale = Math.min(100, percentualeCaricaAttuale + percentualeEccesso);
                                    percentualeEccesso = percentualeCaricaAttuale + percentualeEccesso - 100;
                                } else if (percentualeEccesso < 0) {
                                    nuovaPercentuale = Math.max(0, percentualeCaricaAttuale + percentualeEccesso);
                                    percentualeEccesso = percentualeCaricaAttuale + percentualeEccesso;
                                }
                                preparedStatement4 = connection.prepareStatement(updateSQL);
                                preparedStatement4.setFloat(1, nuovaPercentuale);
                                preparedStatement4.setInt(2, j);
                                preparedStatement4.executeUpdate();

                                if (percentualeEccesso == 0.00f) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (preparedStatement2 != null) {
                preparedStatement2.close();
            }
            if (preparedStatement3 != null) {
                preparedStatement3.close();
            }
            if (preparedStatement4 != null) {
                preparedStatement4.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return percentualeEccesso;
    }
}
