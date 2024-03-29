package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.ReportBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 * Implementazione dell'interfaccia ReportDAO per l'accesso ai dati dei report.
 */
public class ReportDAOImpl implements ReportDAO {

    private static final Logger logger = Logger.getLogger(ReportDAOImpl.class.getName());
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

    private static final String TABLE_NAME_REPORT = "Report";


    /**
     * Recupera la lista di report ordinati per data di emissione in modo decrescente.
     *
     * @return Lista di oggetti ReportBean.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public List<ReportBean> visualizzaReport() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReportBean> report = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_REPORT + " ORDER BY DataEmissione DESC";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ReportBean bean = new ReportBean();
                bean.setDataEmissione(resultSet.getDate("DataEmissione"));
                bean.setNomeReport(resultSet.getString("NomeReport"));
                bean.setIdAmministratore(resultSet.getInt("IdAmministratore"));
                report.add(bean);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return report;
    }

    /**
     * Restituisce il numero totale di report presenti nel database.
     *
     * @return Numero totale di report.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public int ultimoReport() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int n = 0;
        try {
            con = ds.getConnection();
            String query = "SELECT COUNT(IdReport) AS count FROM " + TABLE_NAME_REPORT;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                n = rs.getInt("count");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return n;
    }


    /**
     * Aggiunge un nuovo report al database.
     *
     * @param report Oggetto ReportBean da aggiungere al database.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public void aggiungiReport(ReportBean report) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_REPORT + "(DataEmissione, IdAmministratore, NomeReport)"
                    + " VALUES(?, ?, ?)";
            ps = con.prepareStatement(query);

            java.util.Date utilDate;
            utilDate = report.getDataEmissione();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            ps.setDate(1, sqlDate);
            ps.setInt(2, report.getIdAmministratore());
            ps.setString(3, report.getNomeReport());
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
