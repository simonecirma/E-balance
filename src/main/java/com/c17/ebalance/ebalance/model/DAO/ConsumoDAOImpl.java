package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumoDAOImpl implements ConsumoDAO {

    private static Logger logger = Logger.getLogger(ConsumoDAOImpl.class.getName());
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
    private static final String TABLE_NAME_CONSUMO = "ConsumoEdificio";
    private static final String TABLE_NAME_ARCHIVIO = "ArchivioConsumo";

    public List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ConsumoEdificioBean> consumo = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_CONSUMO;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ConsumoEdificioBean bean = new ConsumoEdificioBean();
                bean.setIdEdificio(resultSet.getInt("IdEdificio"));
                bean.setNomeEdificio(resultSet.getString("NomeEdificio"));
                bean.setConsumoAttuale(resultSet.getFloat("ConsumoAttuale"));
                consumo.add(bean);
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
        return consumo;
    }

    @Override
    public float ottieniConsumiEdifici() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        float consumoEdifici = 0.0f;
        String selectSQL = "SELECT ROUND(SUM(ConsumoAttuale),2) AS Consumo FROM " + TABLE_NAME_CONSUMO;
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                consumoEdifici = resultSet.getFloat("Consumo");
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
        return consumoEdifici;
    }

    @Override
    public List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ArchivioConsumoBean> archivioConsumi = new ArrayList<>();
        String selectSQL = " SELECT Consumo, DataConsumo " +
                " FROM ( SELECT SUM(ConsumoGiornaliero) AS Consumo, DataConsumo FROM "  + TABLE_NAME_ARCHIVIO
                + " GROUP BY DataConsumo ORDER BY Consumo DESC limit 10 ) AS Subquery" +
                " ORDER BY DataConsumo";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ArchivioConsumoBean bean = new ArchivioConsumoBean();
                bean.setConsumoGiornaliero(resultSet.getInt("Consumo"));
                bean.setDataConsumo(resultSet.getDate("DataConsumo"));
                archivioConsumi.add(bean);
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
        return archivioConsumi;
    }

    @Override
    public void simulaConsumo(float consumoOrario, int IdEdificio, Date data) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;

        String updateSQL = "UPDATE " + TABLE_NAME_CONSUMO + " SET ConsumoAttuale = ? WHERE IdEdificio = ?";
        String selectSQL = "SELECT * FROM " + TABLE_NAME_ARCHIVIO + " WHERE IdEdificio = ? AND DataConsumo = ?";
        String insertSQL = "INSERT INTO " + TABLE_NAME_ARCHIVIO + " (DataConsumo, ConsumoGiornaliero, IdEdificio) VALUES (?, 0, ?)";
        String updateSQL2 = "UPDATE " + TABLE_NAME_ARCHIVIO + " SET ConsumoGiornaliero = ConsumoGiornaliero + ? WHERE IdEdificio = ? AND DataConsumo = ?";

        try {
            connection = ds.getConnection();

            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setFloat(1, consumoOrario);
            preparedStatement.setInt(2, IdEdificio);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement2 = connection.prepareStatement(selectSQL);
            preparedStatement2.setInt(1, IdEdificio);
            preparedStatement2.setDate(2, data);
            ResultSet resultSet = preparedStatement2.executeQuery();

            if (!resultSet.next()) {
                preparedStatement3 = connection.prepareStatement(insertSQL);
                preparedStatement3.setDate(1, data);
                preparedStatement3.setInt(2, IdEdificio);
                preparedStatement3.executeUpdate();
                preparedStatement3.close();
            }

            preparedStatement4 = connection.prepareStatement(updateSQL2);
            preparedStatement4.setFloat(1, consumoOrario);
            preparedStatement4.setInt(2, IdEdificio);
            preparedStatement4.setDate(3, data);
            preparedStatement4.executeUpdate();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatement2 != null) preparedStatement2.close();
                if (preparedStatement3 != null) preparedStatement3.close();
                if (preparedStatement4 != null) preparedStatement4.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }


    @Override
    public int ottieniNumEdifici() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int numEdificio = 0;
        String selectSQL = "SELECT COUNT(IdEdificio) AS IdEdificio FROM " + TABLE_NAME_CONSUMO;
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                numEdificio = resultSet.getInt("IdEdificio");
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
        return numEdificio;
    }
}
