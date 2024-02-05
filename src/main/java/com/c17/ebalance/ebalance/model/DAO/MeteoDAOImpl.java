package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione dell'interfaccia MeteoDAO che gestisce l'accesso ai dati meteorologici nel sistema eBalance.
 * Utilizza un'origine dati (DataSource) per la connessione al database.
 */
public class MeteoDAOImpl implements MeteoDAO {
    Calendar calendario = Calendar.getInstance();
    Date data;
    Random random = new Random();
    private static Logger logger = Logger.getLogger(ContrattoDAOImpl.class.getName());
    private static final String TABLE_NAME_METEO = "Meteo";
    private static final String TABLE_NAME_CONDIZIONI = "CondizioneMeteo";
    private static final String TABLE_NAME_INFLUENZARE = "Influenzare";

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
     * Ottiene le condizioni meteorologiche dal database per le rilevazioni future.
     *
     * @return Lista di oggetti MeteoBean rappresentanti le condizioni meteorologiche future.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    public List<MeteoBean> getCondizioniMeteo() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<MeteoBean> condizioni = new ArrayList<>();
        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_METEO + " WHERE DataRilevazione >= ? ORDER BY DataRilevazione ASC, OraRilevazione ASC";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MeteoBean bean = new MeteoBean();
                bean.setIdMeteo(rs.getInt("IdMeteo"));
                bean.setDataRilevazione(rs.getDate("DataRilevazione"));
                bean.setOraRilevazione(rs.getTime("OraRilevazione"));
                bean.setVelocitaVento(rs.getFloat("VelocitaVento"));
                bean.setProbabilitaPioggia(rs.getInt("ProbabilitaPioggia"));
                bean.setCondizioniMetereologiche(rs.getString("CondizioniMetereologiche"));
                condizioni.add(bean);
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
        return condizioni;
    }

    /**
     * Inserisce previsioni meteorologiche nel database per una data e un orario specifici.
     *
     * @param sqlDate           Data delle previsioni.
     * @param orario            Orario delle previsioni (0, 6, 12, 18).
     * @param vel               Velocità del vento.
     * @param prob              Probabilità di pioggia.
     * @param condizioneCasuale Condizione meteorologica casuale.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public void insertPrevisioni(java.sql.Date sqlDate, int orario, float vel, int prob, String condizioneCasuale) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        Time sqlTime = null;
        if (orario == 0) {
            sqlTime = Time.valueOf("00:00:00");
        } else if (orario == 6) {
            sqlTime = Time.valueOf("06:00:00");
        } else if (orario == 12) {
            sqlTime = Time.valueOf("12:00:00");
        } else if (orario == 18) {
            sqlTime = Time.valueOf("18:00:00");
        }
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_METEO + "(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)"
                    + "VALUES(?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ps.setTime(2, sqlTime);
            ps.setFloat(3, vel);
            ps.setInt(4, prob);
            ps.setString(5, condizioneCasuale);
            ps.executeUpdate();
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
    }

    /**
     * Ottiene la lista di tutte le possibili condizioni meteorologiche.
     *
     * @return Lista di stringhe rappresentanti le condizioni meteorologiche.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<String> getCondizione() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<String> condizioni = new ArrayList<>();
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_CONDIZIONI;
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                condizioni.add(rs.getString("Condizione"));
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
        return condizioni;
    }

    /**
     * Verifica la presenza di rilevazioni meteorologiche per una data e un orario specifici.
     *
     * @param sqlDate Data delle rilevazioni.
     * @param orario  Orario delle rilevazioni (0, 6, 12, 18).
     * @return True se ci sono rilevazioni presenti, altrimenti False.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public boolean verificaPresenza(java.sql.Date sqlDate, int orario) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        Time sqlTime = null;
        boolean res = false;
        if (orario == 0) {
            sqlTime = Time.valueOf("00:00:00");
        } else if (orario == 6) {
            sqlTime = Time.valueOf("06:00:00");
        } else if (orario == 12) {
            sqlTime = Time.valueOf("12:00:00");
        } else if (orario == 18) {
            sqlTime = Time.valueOf("18:00:00");
        }
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_METEO + " WHERE DataRilevazione = ? AND OraRilevazione = ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ps.setTime(2, sqlTime);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = true;
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
        return res;
    }

    /**
     * Calcola la media giornaliera della velocità del vento e della probabilità di pioggia.
     *
     * @return Lista di oggetti MeteoBean rappresentanti le medie giornaliere.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<MeteoBean> mediaGiornaliera() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<MeteoBean> condizioni = new ArrayList<>();

        data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        try {
            con = ds.getConnection();
            String query = "SELECT AVG(VelocitaVento) AS mediaVel, AVG(ProbabilitaPioggia) AS mediaPioggia, DataRilevazione FROM " + TABLE_NAME_METEO
                + " WHERE DataRilevazione >= ? GROUP BY DataRilevazione"
                + " ORDER BY DataRilevazione ASC LIMIT 7";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MeteoBean bean = new MeteoBean();
                bean.setDataRilevazione(rs.getDate("DataRilevazione"));
                bean.setVelocitaVento(rs.getFloat("mediaVel"));
                bean.setProbabilitaPioggia(rs.getInt("mediaPioggia"));

                if(rs.getInt("mediaPioggia") <= 10) {
                    bean.setCondizioniMetereologiche("Soleggiato");
                } else if (rs.getInt("mediaPioggia") > 10 && rs.getInt("mediaPioggia") <= 20) {
                    bean.setCondizioniMetereologiche("Sereno");
                } else if (rs.getInt("mediaPioggia") > 20 && rs.getInt("mediaPioggia") <= 30) {
                    bean.setCondizioniMetereologiche("Ventilato");
                } else if (rs.getInt("mediaPioggia") > 30 && rs.getInt("mediaPioggia") <= 40) {
                    bean.setCondizioniMetereologiche("Nuvoloso");
                } else if (rs.getInt("mediaPioggia") > 40 && rs.getInt("mediaPioggia") <= 50) {
                    bean.setCondizioniMetereologiche("Nevoso");
                }else if (rs.getInt("mediaPioggia") > 50) {
                    bean.setCondizioniMetereologiche("Piovoso");
                }
                condizioni.add(bean);
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
        return condizioni;
    }

    /**
     * Ottiene un parametro specifico per una data e un orario specifici.
     *
     * @param sqlData Data delle rilevazioni.
     * @param i       Orario delle rilevazioni (0, 6, 12, 18).
     * @return ID del parametro per la data e l'orario specifici.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public int getParametro(java.sql.Date sqlData, int i) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
        Time sqlTime = null;
        if (i == 0) {
            sqlTime = Time.valueOf("00:00:00");
        } else if (i == 6) {
            sqlTime = Time.valueOf("06:00:00");
        } else if (i == 12) {
            sqlTime = Time.valueOf("12:00:00");
        } else if (i == 18) {
            sqlTime = Time.valueOf("18:00:00");
        }
        try {
            con = ds.getConnection();
            String query = "SELECT IdMeteo FROM " + TABLE_NAME_METEO + " WHERE DataRilevazione = ? AND OraRilevazione = ?";
            ps = con.prepareStatement(query);
            ps.setDate(1, sqlData);
            ps.setTime(2, sqlTime);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("IdMeteo");
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
        return id;
    }

    /**
     * Aggiorna l'associazione tra una rilevazione meteorologica e una sorgente.
     *
     * @param id      ID della rilevazione meteorologica.
     * @param sorgente ID della sorgente di influenza.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    @Override
    public void aggiornaInfluenzare(int id, int sorgente) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO "+ TABLE_NAME_INFLUENZARE + "(IdMeteo, IdSorgente)"
                    + "VALUES(?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, sorgente);
            ps.executeUpdate();
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
    }
}