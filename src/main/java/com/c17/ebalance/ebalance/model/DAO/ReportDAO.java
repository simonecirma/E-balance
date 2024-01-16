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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class ReportDAO {

    static Logger logger = Logger.getLogger(ReportDAO.class.getName());
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
    private static final String TABLE_NAME_REPORT= "Report";

    public List<reportBean> visualizzaReport() throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<reportBean> report = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_REPORT;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reportBean bean = new reportBean();
                bean.setDataEmissione(resultSet.getDate("DataEmissione"));
                bean.setIdAmministratore(resultSet.getInt("IdAmministratore"));
                report.add(bean);
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
        return report;
    }

}
