package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

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
}
