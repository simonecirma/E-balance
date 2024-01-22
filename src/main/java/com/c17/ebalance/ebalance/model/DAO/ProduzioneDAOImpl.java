package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduzioneDAOImpl implements ProduzioneDAO {

    private static Logger logger = Logger.getLogger(ProduzioneDAOImpl.class.getName());
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
    private static final String TABLE_NAME_ARCHIVIO = "ArchivioProduzione";
    private static final String TABLE_NAME_SORGENTE = "Sorgente";
    private static final String TABLE_NAME_TIPO_SORGENTE = "TipoSorgente";
    private static final String TABLE_NAME_CONSUMO = "ConsumoEdificio";

    public List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ArchivioProduzioneBean> produzione = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_ARCHIVIO;


        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ArchivioProduzioneBean bean = new ArchivioProduzioneBean();
                bean.setIdProduzione(resultSet.getInt("IdProduzione"));
                bean.setDataProduzione(resultSet.getDate("DataProduzione"));
                bean.setProduzioneGiornaliera(resultSet.getFloat("ProduzioneGiornaliera"));
                produzione.add(bean);
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
        return produzione;
    }


    @Override
    public List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<SorgenteBean> produzione = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_SORGENTE;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SorgenteBean bean = new SorgenteBean();
                bean.setIdSorgente(resultSet.getInt("IdSorgente"));
                bean.setProduzioneAttuale(resultSet.getFloat("ProduzioneAttuale"));
                bean.setDataInstallazione(resultSet.getDate("DataInstallazione"));
                bean.setTipologia(resultSet.getString("Tipologia"));
                bean.setFlagAttivazioneSorgente(resultSet.getBoolean("FlagAttivazioneSorgente"));
                bean.setFlagStatoSorgente(resultSet.getBoolean("FlagStatoSorgente"));
                produzione.add(bean);
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
        return produzione;
    }

    @Override
    public String[][] ottieniProduzione() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String selectSQL = "SELECT  ROUND(SUM(ProduzioneAttuale),2) AS Produzione, Tipologia FROM " +TABLE_NAME_SORGENTE
                            + " GROUP BY Tipologia";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int numeroElementi = resultSet.getRow();
            String produzione[][] = new String[numeroElementi][numeroElementi];
            int i = 0;
            resultSet.beforeFirst();
            while (resultSet.next()) {
                produzione[i][0] = String.valueOf(resultSet.getFloat("Produzione"));
                produzione[i][1] = resultSet.getString("Tipologia");
                i++;
            }
            return produzione;
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
    public List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<TipoSorgenteBean> tipoSorgente = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_TIPO_SORGENTE;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TipoSorgenteBean bean = new TipoSorgenteBean();
                bean.setTipo(resultSet.getString("Tipo"));
                tipoSorgente.add(bean);
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
        return tipoSorgente;
    }

    @Override
    public void simulaProduzione(int idSorgente, float produzioneSimulata, Date data) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        PreparedStatement preparedStatement5 = null;

        String selectSQL = "SELECT FlagAttivazioneSorgente, FlagStatoSorgente FROM " + TABLE_NAME_SORGENTE
                        + " WHERE IdSorgente = ?";
        String updateSQL = "UPDATE " + TABLE_NAME_SORGENTE + " SET ProduzioneAttuale = ? WHERE IdSorgente = ?";
        String selectSQL2 = "SELECT * FROM " + TABLE_NAME_ARCHIVIO + " WHERE IdSorgente = ? AND DataProduzione = ?";
        String insertSQL = "INSERT INTO " + TABLE_NAME_ARCHIVIO + " (DataProduzione, ProduzioneGiornaliera, IdSorgente) VALUES (?, 0, ?)";
        String updateSQL2 = "UPDATE " + TABLE_NAME_ARCHIVIO + " SET ProduzioneGiornaliera = ProduzioneGiornaliera + ? WHERE IdSorgente = ? AND DataProduzione = ?";

        try {
            connection = ds.getConnection();

            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idSorgente);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean("FlagStatoSorgente") && resultset.getBoolean("FlagAttivazioneSorgente")) {
                    preparedStatement2 = connection.prepareStatement(updateSQL);
                    preparedStatement2.setFloat(1, produzioneSimulata);
                    preparedStatement2.setInt(2, idSorgente);
                    preparedStatement2.executeUpdate();

                    preparedStatement3 = connection.prepareStatement(selectSQL2);
                    preparedStatement3.setInt(1, idSorgente);
                    preparedStatement3.setDate(2, data);
                    ResultSet resultSet2 = preparedStatement3.executeQuery();

                    if (!resultSet2.next()) {
                        preparedStatement4 = connection.prepareStatement(insertSQL);
                        preparedStatement4.setDate(1, data);
                        preparedStatement4.setInt(2, idSorgente);
                        preparedStatement4.executeUpdate();
                    }

                    preparedStatement5 = connection.prepareStatement(updateSQL2);
                    preparedStatement5.setFloat(1, produzioneSimulata);
                    preparedStatement5.setInt(2, idSorgente);
                    preparedStatement5.setDate(3, data);
                    preparedStatement5.executeUpdate();
                }
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatement2 != null) preparedStatement2.close();
                if (preparedStatement3 != null) preparedStatement3.close();
                if (preparedStatement4 != null) preparedStatement4.close();
                if (preparedStatement5 != null) preparedStatement5.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }

    @Override
    public int ottieniSorgenti() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int numSorgente = 0;
        String selectSQL = "SELECT COUNT(IdSorgente) AS IdSorgente FROM " + TABLE_NAME_SORGENTE;
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                numSorgente = resultSet.getInt("IdSorgente");
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
        return numSorgente;
    }

    @Override
    public float ottieniProduzioneNecessaria() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        float produzioneNecessaria = 0.0f;
        String selectSQL = "SELECT ROUND(SUM(ConsumoAttuale),2) - (SELECT ROUND(SUM(ProduzioneAttuale),2) FROM " + TABLE_NAME_SORGENTE
                + " WHERE IdSorgente != 1) AS ProduzioneNecessaria FROM " + TABLE_NAME_CONSUMO;
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                produzioneNecessaria = resultSet.getInt("ProduzioneNecessaria");
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
        return produzioneNecessaria;
    }

    @Override
    public void simulaProduzioneSEN(float produzioneNecessaria, Date data) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        PreparedStatement preparedStatement5 = null;

        String selectSQL = "SELECT FlagAttivazioneSorgente, FlagStatoSorgente FROM " + TABLE_NAME_SORGENTE
                + " WHERE IdSorgente = 1";
        String updateSQL = "UPDATE " + TABLE_NAME_SORGENTE + " SET ProduzioneAttuale = ? WHERE IdSorgente = 1";
        String selectSQL2 = "SELECT * FROM " + TABLE_NAME_ARCHIVIO + " WHERE IdSorgente = 1 AND DataProduzione = ?";
        String insertSQL = "INSERT INTO " + TABLE_NAME_ARCHIVIO + " (DataProduzione, ProduzioneGiornaliera, IdSorgente) VALUES (?, 0, 1)";
        String updateSQL2 = "UPDATE " + TABLE_NAME_ARCHIVIO + " SET ProduzioneGiornaliera = ProduzioneGiornaliera + ? WHERE IdSorgente = 1 AND DataProduzione = ?";

        try {
            connection = ds.getConnection();

            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean("FlagStatoSorgente") && resultset.getBoolean("FlagAttivazioneSorgente")) {
                    preparedStatement2 = connection.prepareStatement(updateSQL);
                    preparedStatement2.setFloat(1, produzioneNecessaria);
                    preparedStatement2.executeUpdate();

                    preparedStatement3 = connection.prepareStatement(selectSQL2);
                    preparedStatement3.setDate(1, data);
                    ResultSet resultSet2 = preparedStatement3.executeQuery();

                    if (!resultSet2.next()) {
                        preparedStatement4 = connection.prepareStatement(insertSQL);
                        preparedStatement4.setDate(1, data);
                        preparedStatement4.executeUpdate();
                    }

                    preparedStatement5 = connection.prepareStatement(updateSQL2);
                    preparedStatement5.setFloat(1, produzioneNecessaria);
                    preparedStatement5.setDate(2, data);
                    preparedStatement5.executeUpdate();
                }
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatement2 != null) preparedStatement2.close();
                if (preparedStatement3 != null) preparedStatement3.close();
                if (preparedStatement4 != null) preparedStatement4.close();
                if (preparedStatement5 != null) preparedStatement5.close();
            } finally {
                if (connection != null) connection.close();
            }
        }

    }

}
