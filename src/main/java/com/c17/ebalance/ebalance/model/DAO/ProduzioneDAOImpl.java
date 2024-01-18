package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;

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
}
