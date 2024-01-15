package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class amministratoreDAO {

    static Logger logger = Logger.getLogger(amministratoreDAO.class.getName());
    private static DataSource ds;

    static
    {
        try
        {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/ebalance");

        }
        catch (NamingException e)
        {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private static final String TABLE_NAME_AMMINISTRATORE= "Amministratore";

    public static synchronized amministratoreBean login(String email, String password) throws SQLException
    {
        PreparedStatement ps = null;
        amministratoreBean bean = new amministratoreBean();
        String sql = "SELECT * FROM " + amministratoreDAO.TABLE_NAME_AMMINISTRATORE + " WHERE Email = ? AND Password = ?";
        try(Connection con = ds.getConnection())
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                bean.setIdAmministratore(rs.getInt("IdAmministratore"));
                bean.setNome(rs.getString("Nome"));
                bean.setCognome(rs.getString("Cognome"));
                bean.setDataNascita(rs.getDate("DataNascita"));
                bean.setEmail(rs.getString("Email"));
                bean.setPassword(rs.getString("Password"));
                bean.setFlagTipo(rs.getBoolean("FlagTipo"));
            }

        }
        catch(SQLException e)
        {
            logger.log(Level.WARNING, e.getMessage());
        }
        finally
        {
            if(ps != null)
            {
                ps.close();
            }
        }



        if(bean == null || bean.getEmail() == null || bean.getEmail().trim().isEmpty())
        {

            return null;
        }
        else
        {
            return bean;
        }

    }

    public List<amministratoreBean> visualizzaAmministratori() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<amministratoreBean> amministratori = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_AMMINISTRATORE;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                amministratoreBean bean = new amministratoreBean();
                bean.setEmail(resultSet.getString("Email"));
                bean.setNome(resultSet.getString("Nome"));
                bean.setCognome(resultSet.getString("Cognome"));
                bean.setPassword(resultSet.getString("Password"));
                amministratori.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return amministratori;
    }

    public amministratoreBean aggiornaAmministratore(amministratoreBean amministratore) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        amministratoreBean amministratoreAggiornato = new amministratoreBean();

        try {
            con = ds.getConnection();

            String query = "UPDATE " + TABLE_NAME_AMMINISTRATORE +
                    " SET Nome=?, Cognome=?, DataNascita=?, Email=?, Password=? " +
                    "WHERE IdAmministratore=?";
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

    public void aggiungiAmministratore(amministratoreBean amministratore) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();

            String query = "INSERT INTO " + TABLE_NAME_AMMINISTRATORE +
                    "(Nome, Cognome, DataNascita, Email, Password, FlagTipo) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";

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

    public amministratoreBean getById(int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        amministratoreBean amministratore = new amministratoreBean();

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
        }
        return amministratore;
    }
    }
