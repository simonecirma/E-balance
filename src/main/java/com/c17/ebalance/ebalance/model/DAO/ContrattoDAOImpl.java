package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.ContrattoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 * Implementazione dell'interfaccia ContrattoDAO che gestisce l'accesso ai dati relativi ai contratti nel sistema eBalance.
 * Questa classe utilizza un DataSource per interagire con il database e implementa i metodi definiti nell'interfaccia.
 */
public class ContrattoDAOImpl implements ContrattoDAO {

    private static Logger logger = Logger.getLogger(ContrattoDAOImpl.class.getName());
    private static final String TABLE_NAME_CONTRATTO = "Contratto";

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

    /**
     * Restituisce il contratto attivo nel sistema.
     *
     * @return Un oggetto ContrattoBean rappresentante il contratto attivo.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public ContrattoBean visualizzaContratto() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_CONTRATTO + " ORDER BY DataSottoscrizione DESC LIMIT 1";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bean.setIdContratto(rs.getInt("IdContratto"));
                bean.setNomeEnte(rs.getString("NomeEnte"));
                bean.setConsumoMedioAnnuale(rs.getFloat("ConsumoMedioAnnuale"));
                bean.setCostoMedioUnitario(rs.getFloat("CostoMedioUnitario"));
                bean.setDataSottoscrizione(rs.getDate("DataSottoscrizione"));
                bean.setDurata(rs.getInt("Durata"));
                bean.setPrezzoVendita(rs.getFloat("PrezzoVendita"));
                bean.setIdAmministatore(rs.getInt("IdAmministratore"));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return bean;
    }


    /**
     * Restituisce una lista degli storici contratti nel sistema.
     *
     * @return Una lista di oggetti ContrattoBean rappresentanti gli storici contratti.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<ContrattoBean> contratti = new ArrayList<>();

        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_CONTRATTO + " WHERE IdContratto < (SELECT MAX(IdContratto) "
                    + "FROM " + TABLE_NAME_CONTRATTO + ") ORDER BY IdContratto DESC";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ContrattoBean bean = new ContrattoBean();
                bean.setIdContratto(rs.getInt("IdContratto"));
                bean.setNomeEnte(rs.getString("NomeEnte"));
                bean.setConsumoMedioAnnuale(rs.getFloat("ConsumoMedioAnnuale"));
                bean.setCostoMedioUnitario(rs.getFloat("CostoMedioUnitario"));
                bean.setDataSottoscrizione(rs.getDate("DataSottoscrizione"));
                bean.setDurata(rs.getInt("Durata"));
                bean.setPrezzoVendita(rs.getFloat("PrezzoVendita"));
                bean.setIdAmministatore(rs.getInt("IdAmministratore"));
                contratti.add(bean);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return contratti;
    }

    /**
     * Aggiorna un contratto esistente nel sistema.
     *
     * @param contratto Il contratto da aggiornare.
     * @return Un oggetto ContrattoBean rappresentante il contratto aggiornato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();
        try {
            con = ds.getConnection();
            String query = "UPDATE " + TABLE_NAME_CONTRATTO
                    + " SET NomeEnte= ?, ConsumoMedioAnnuale=?, CostoMedioUnitario=?, "
                    + "DataSottoscrizione=?, Durata=?, PrezzoVendita=?, IdAmministratore=? "
                    + "WHERE IdContratto=?";
            ps = con.prepareStatement(query);

            ps.setString(1, contratto.getNomeEnte());
            ps.setFloat(2, contratto.getConsumoMedioAnnuale());
            ps.setFloat(3, contratto.getCostoMedioUnitario());
            ps.setDate(4, (Date) contratto.getDataSottoscrizione());
            ps.setInt(5, contratto.getDurata());
            ps.setFloat(6, contratto.getPrezzoVendita());
            ps.setInt(7, contratto.getIdAmministatore());
            ps.setInt(8, contratto.getIdContratto());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                bean = contratto;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return bean;
    }

    /**
     * Aggiunge un nuovo contratto nel sistema.
     *
     * @param contratto Il contratto da aggiungere.
     * @return Un oggetto ContrattoBean rappresentante il contratto aggiunto.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public ContrattoBean aggiungiContratto(final ContrattoBean contratto) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();

        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_CONTRATTO + "( NomeEnte, ConsumoMedioAnnuale, CostoMedioUnitario, "
                    + "DataSottoscrizione, Durata, PrezzoVendita, IdAmministratore)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, contratto.getNomeEnte());
            ps.setFloat(2, contratto.getConsumoMedioAnnuale());
            ps.setFloat(3, contratto.getCostoMedioUnitario());
            ps.setDate(4, (Date) contratto.getDataSottoscrizione());
            ps.setInt(5, contratto.getDurata());
            ps.setFloat(6, contratto.getPrezzoVendita());
            ps.setInt(7, contratto.getIdAmministatore());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                bean = contratto;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return bean;
    }

    /**
     * Verifica se è presente il primo contratto nel sistema.
     *
     * @return True se è presente il primo contratto, altrimenti False.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public boolean verificaPrimoContratto() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int cont = 0;
        boolean result;

        String selectSQL = "SELECT * FROM " + TABLE_NAME_CONTRATTO;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cont++;
            }
            if (cont <= 1) {
                result = true;
            } else {
                result = false;
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

        return result;
    }

    /**
     * Restituisce il contratto attivo nel sistema per un determinato periodo.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Un oggetto ContrattoBean rappresentante il contratto attivo per il periodo specificato.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public ContrattoBean getContrattoAttivo(final Date dataInizio, final Date dataFine) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ContrattoBean bean = new ContrattoBean();

        String sql = "SELECT * FROM " + TABLE_NAME_CONTRATTO + " WHERE DataSottoscrizione <= ? "
                + "AND DATE_ADD(DataSottoscrizione, INTERVAL Durata MONTH) >= ? ";
        try {
            con = ds.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, dataFine);
            ps.setDate(2, dataInizio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bean.setIdContratto(rs.getInt("IdContratto"));
                bean.setNomeEnte(rs.getString("NomeEnte"));
                bean.setConsumoMedioAnnuale(rs.getFloat("ConsumoMedioAnnuale"));
                bean.setCostoMedioUnitario(rs.getFloat("CostoMedioUnitario"));
                bean.setDataSottoscrizione(rs.getDate("DataSottoscrizione"));
                bean.setDurata(rs.getInt("Durata"));
                bean.setPrezzoVendita(rs.getFloat("PrezzoVendita"));
                bean.setIdAmministatore(rs.getInt("IdAmministratore"));
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
        return bean;
    }

    /**
     * Restituisce il prezzo di vendita corrente nel sistema.
     *
     * @return Il prezzo di vendita corrente.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public float ottieniPrezzoVendita() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        float prezzoVendita = 0.00f;

        String selectSQL = "SELECT PrezzoVendita FROM " + TABLE_NAME_CONTRATTO
                + " ORDER BY DataSottoscrizione DESC LIMIT 1 ";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                prezzoVendita = resultSet.getFloat("PrezzoVendita");
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
        return prezzoVendita;
    }
}
