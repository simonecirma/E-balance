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

/**
 * Implementazione dell'interfaccia ParametriIADAO che fornisce metodi per l'accesso ai dati
 * relativi ai parametri dell'Intelligenza Artificiale nel sistema eBalance.
 */
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

    /**
     * Recupera la lista di tutti i parametri IA presenti nel sistema.
     *
     * @return Una lista di oggetti ParametriIABean rappresentanti i parametri IA.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
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

    /**
     * Recupera la lista di tutte le interazioni tra parametri IA e sorgenti energetiche.
     *
     * @return Una lista di oggetti InteragisceBean rappresentanti le interazioni tra parametri IA e sorgenti energetiche.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
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

    /**
     * Recupera la lista di tutte le interazioni attive tra parametri IA e sorgenti energetiche.
     *
     * @return Una lista di oggetti InteragisceBean rappresentanti le interazioni attive tra parametri IA e sorgenti energetiche.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    @Override
    public List<InteragisceBean> ottieniParametriAttivi() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<InteragisceBean> parametriAttivi = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_INTERAGISCE + " WHERE idParametro IN (SELECT idparametro FROM "
                + TABLE_NAME_PARAMETRI + " WHERE FlagAttivazioneParametro = TRUE) ORDER BY PrioritaSorgente";

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

    /**
     * Aggiorna il piano personalizzato dei parametri IA con le nuove preferenze.
     *
     * @param preferenzaSorgente         La sorgente energetica preferita.
     * @param percentualeUtilizzoPannelli La percentuale di utilizzo dei pannelli fotovoltaici.
     * @param percentualeUtilizzoSEN      La percentuale di utilizzo del Servizio Elettrico Nazionale.
     * @param prioritaSorgenti           Un array di stringhe contenente le priorità delle sorgenti energetiche.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
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

    /**
     * Aggiorna il piano attivo dei parametri IA.
     *
     * @param piano          Il piano da attivare.
     * @param idAmministratore L'ID dell'amministratore che effettua l'aggiornamento.
     * @return True se l'aggiornamento ha successo, false altrimenti.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
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

    /**
     * Aggiorna la percentuale di utilizzo del Servizio Elettrico Nazionale.
     *
     * @param percentualeAggiunta La percentuale da aggiungere o sottrarre.
     * @throws SQLException In caso di problemi durante l'accesso al database.
     */
    @Override
    public void aggiornaPercentualeSEN(int percentualeAggiunta) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        int idParametro = 0;
        float percentualeUtilizzo = 0.00f;
        float percentualeNuova = 0.00f;

        String selectSQL = "SELECT IdParametro FROM " + TABLE_NAME_PARAMETRI + " WHERE FlagAttivazioneParametro = 1";

        String selectSQL2 = "SELECT PercentualeUtilizzoSorgente FROM " + TABLE_NAME_INTERAGISCE
                + " WHERE IdParametro = ? AND TipoSorgente = 'Servizio Elettrico Nazionale'";

        String upadteSQL = "UPDATE " + TABLE_NAME_INTERAGISCE
                + " SET PercentualeUtilizzoSorgente =  ? "
                + " WHERE IdParametro = ? AND TipoSorgente = 'Servizio Elettrico Nazionale'";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idParametro = resultSet.getInt("IdParametro");
            }

            preparedStatement2 = connection.prepareStatement(selectSQL2);
            preparedStatement2.setInt(1, idParametro);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                percentualeUtilizzo = resultSet2.getFloat("PercentualeUtilizzoSorgente");
            }

            if (percentualeAggiunta > 0) {
                percentualeNuova = Math.min(100, percentualeUtilizzo + percentualeAggiunta);
            } else {
                percentualeNuova = Math.max(0, percentualeUtilizzo + percentualeAggiunta);
            }

            preparedStatement3 = connection.prepareStatement(upadteSQL);
            preparedStatement3.setFloat(1, percentualeNuova);
            preparedStatement3.setInt(2, idParametro);
            preparedStatement3.executeUpdate();
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
