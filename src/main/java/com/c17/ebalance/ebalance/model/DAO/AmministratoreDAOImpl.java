package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

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

public class AmministratoreDAOImpl implements AmministratoreDAO{

    private static Logger logger = Logger.getLogger(AmministratoreDAOImpl.class.getName());
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

    public boolean verificaSuperAdmin() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result;

        String selectSQL = "SELECT * FROM " + TABLE_NAME_AMMINISTRATORE + " WHERE FlagTipo = 1";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            } else {
                result = false;
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return result;
    }
    private static final String TABLE_NAME_AMMINISTRATORE = "Amministratore";

    public AmministratoreBean login(final String email, final String password) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        AmministratoreBean bean = new AmministratoreBean();
        String sql = "SELECT * FROM " + AmministratoreDAOImpl.TABLE_NAME_AMMINISTRATORE + " WHERE Email = ? AND Password = ?";
        try {
            con = ds.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bean.setIdAmministratore(rs.getInt("IdAmministratore"));
                bean.setNome(rs.getString("Nome"));
                bean.setCognome(rs.getString("Cognome"));
                bean.setDataNascita(rs.getDate("DataNascita"));
                bean.setEmail(rs.getString("Email"));
                bean.setPassword(rs.getString("Password"));
                bean.setFlagTipo(rs.getBoolean("FlagTipo"));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }

        if (bean == null || bean.getEmail() == null || bean.getEmail().trim().isEmpty()) {
            return null;
        } else {
            return bean;
        }
    }

    public List<AmministratoreBean> visualizzaAmministratori() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<AmministratoreBean> amministratori = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_AMMINISTRATORE;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AmministratoreBean bean = new AmministratoreBean();
                bean.setIdAmministratore(resultSet.getInt("IdAmministratore"));
                bean.setEmail(resultSet.getString("Email"));
                bean.setNome(resultSet.getString("Nome"));
                bean.setCognome(resultSet.getString("Cognome"));
                bean.setPassword(resultSet.getString("Password"));
                bean.setFlagTipo(resultSet.getBoolean("FlagTipo"));
                amministratori.add(bean);
            }

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return amministratori;
    }

    public AmministratoreBean aggiornaAmministratore(final AmministratoreBean amministratore) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        AmministratoreBean amministratoreAggiornato = new AmministratoreBean();

        try {
            con = ds.getConnection();

            String query = "UPDATE " + TABLE_NAME_AMMINISTRATORE
                    + " SET Nome=?, Cognome=?, DataNascita=?, Email=?, Password=? "
                    + "WHERE IdAmministratore=?";
            ps = con.prepareStatement(query);

            ps.setString(1, amministratore.getNome());
            ps.setString(2, amministratore.getCognome());
            ps.setDate(3, amministratore.getDataNascita());
            ps.setString(4, amministratore.getEmail());
            ps.setString(5, amministratore.getPassword());
            ps.setInt(6, amministratore.getIdAmministratore());

            int righeAggiornate = ps.executeUpdate();
            if (righeAggiornate > 0) {
                amministratoreAggiornato = amministratore;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return amministratoreAggiornato;
    }

    public void aggiungiAmministratore(final AmministratoreBean amministratore) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();

            String query = "INSERT INTO " + TABLE_NAME_AMMINISTRATORE
                    + "(Nome, Cognome, DataNascita, Email, Password, FlagTipo) "
                    + "VALUES(?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(query);

            ps.setString(1, amministratore.getNome());
            ps.setString(2, amministratore.getCognome());
            ps.setDate(3, amministratore.getDataNascita());
            ps.setString(4, amministratore.getEmail());
            ps.setString(5, amministratore.getPassword());
            ps.setBoolean(6, amministratore.getFlagTipo());

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    public AmministratoreBean getById(final int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        AmministratoreBean amministratore = new AmministratoreBean();

        try {
            con = ds.getConnection();

            String query = "SELECT * FROM " + TABLE_NAME_AMMINISTRATORE + " WHERE IdAmministratore = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                amministratore.setNome(rs.getString("Nome"));
                amministratore.setCognome(rs.getString("Cognome"));
                amministratore.setDataNascita(rs.getDate("DataNascita"));
                amministratore.setEmail(rs.getString("Email"));
                amministratore.setPassword(rs.getString("Password"));
                amministratore.setFlagTipo(rs.getBoolean("FlagTipo"));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return amministratore;
    }

    public void rimuoviAmministratore(final int idAmministratore) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();

            String query = "DELETE FROM " + TABLE_NAME_AMMINISTRATORE + " WHERE IdAmministratore = ?";

            ps = con.prepareStatement(query);
            ps.setInt(1, idAmministratore);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

}
