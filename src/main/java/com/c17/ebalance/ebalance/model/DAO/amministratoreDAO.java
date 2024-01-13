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

        List<amministratoreBean> amministratori = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_AMMINISTRATORE;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                amministratoreBean bean = new amministratoreBean();
                bean.setEmail(rs.getString("email"));
                bean.setNome(rs.getString("nome"));
                bean.setCognome(rs.getString("cognome"));
                bean.setPassword(rs.getString("password"));

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
}
