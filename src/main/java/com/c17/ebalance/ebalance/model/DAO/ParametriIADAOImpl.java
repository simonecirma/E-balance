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
        String selectSQL = "SELECT * FROM " + TABLE_NAME_INTERAGISCE + " ORDER BY PrioritaSorgente";

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

    @Override
    public void aggiornaPianoPersonalizzato(String preferenzaSorgente, int percentualeUtilizzoPannelli, int percentualeUtilizzoSEN, String[] prioritaSorgenti) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;

        List<InteragisceBean> interagisce = new ArrayList<>();
        String selectSQL = "UPDATE " + TABLE_NAME_INTERAGISCE + " SET FlagPreferenzaSorgente = ?, PercentualeUtilizzoSorgente = ?,"
                + " PrioritaSorgente = ? WHERE IdParametro = '3' AND TipoSorgente = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            if (preferenzaSorgente.equalsIgnoreCase("Pannello fotovoltaico")) {
                preparedStatement.setBoolean(1, true);
            } else {
                preparedStatement.setBoolean(1, false);
            }
            preparedStatement.setInt(2, percentualeUtilizzoPannelli);
            if (prioritaSorgenti[0].equalsIgnoreCase("Pannello fotovoltaico")) {
                preparedStatement.setInt(3, 1);
            } else {
                preparedStatement.setInt(3, 2);
            }
            preparedStatement.setString(4, "Pannello fotovoltaico");
            preparedStatement.executeUpdate();
            preparedStatement2 = connection.prepareStatement(selectSQL);
            if (preferenzaSorgente.equalsIgnoreCase("Servizio Elettrico Nazionale")) {
                preparedStatement2.setBoolean(1, true);
            } else {
                preparedStatement2.setBoolean(1, false);
            }
            preparedStatement2.setInt(2, percentualeUtilizzoSEN);
            if (prioritaSorgenti[0].equalsIgnoreCase("Servizio Elettrico Nazionale")) {
                preparedStatement2.setInt(3, 1);
            } else {
                preparedStatement2.setInt(3, 2);
            }
            preparedStatement2.setString(4, "Servizio Elettrico Nazionale");
            preparedStatement2.executeUpdate();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (preparedStatement2 != null) {
                    preparedStatement2.close();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    @Override
    public boolean aggiornaPianoAttivo(String piano, int idAmministratore) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        boolean result;
        String selectSQL = "UPDATE " + TABLE_NAME_PARAMETRI + " SET FlagAttivazioneParametro = 0, IdAmministratore = ? WHERE FlagAttivazioneParametro = 1";
        String selectSQL1 = "UPDATE " + TABLE_NAME_PARAMETRI + " SET FlagAttivazioneParametro = 1, IdAmministratore = ? WHERE Piano = ?";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idAmministratore);
            preparedStatement.executeUpdate();
            preparedStatement2 = connection.prepareStatement(selectSQL1);
            preparedStatement2.setInt(1, idAmministratore);
            if (piano.equalsIgnoreCase("SalvaguardiaAmbientale")) {
                preparedStatement2.setString(2, "Salvaguardia Ambientale");
            } else if (piano.equalsIgnoreCase("EfficienzaEconomica")) {
                preparedStatement2.setString(2, "Efficienza Economica");
            } else if (piano.equalsIgnoreCase("Personalizzato")) {
                preparedStatement2.setString(2, "Personalizzato");
            }
            int row = preparedStatement2.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
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
    }
}
