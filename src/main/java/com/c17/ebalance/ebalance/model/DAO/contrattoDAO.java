package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class contrattoDAO {

    static Logger logger = Logger.getLogger(amministratoreDAO.class.getName());

    private static DataSource ds;

    static{
        try{
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/ebalance");

        }catch (NamingException e){
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public static contrattoBean visualizzaContratto(int idContratto) throws SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        contrattoBean bean=new contrattoBean();
        try{
            String query="SELECT * FROM Contratto WHERE IdContratto=?";
            ps=con.prepareStatement(query);
            ps.setInt(1, idContratto);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                bean.setIdContratto(rs.getInt("IdContratto"));
                bean.setNomeEnte(rs.getString("NomeEnte"));
                bean.setConsumoMedioAnnuale(rs.getFloat("ConsumoMedioAnnuale"));
                bean.setCostoMedioUnitario(rs.getFloat("CostoMedioUnitario"));
                bean.setDataSottoscrizione(rs.getDate("DataSottoscrizione"));
                bean.setDurata(rs.getInt("Durata"));
                bean.setPrezzoVendita(rs.getFloat("PrezzoVendita"));
                bean.setIdAmministatore(rs.getInt("IdAmministratore"));
            }
        }catch(Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }

    public List<contrattoBean> visualizzaStoricoContratti() throws SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        List<contrattoBean> contratti=new ArrayList<>();

        try{
            String query="SELECT * FROM Contratto";
            ps=con.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                contrattoBean bean=new contrattoBean();
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
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
        }
        return contratti;
    }

    public contrattoBean aggiornaContratto(contrattoBean contratto) throws SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        contrattoBean bean=new contrattoBean();
        try{
            String query="UPDATE Contratto " +
                    "SET NomeEnte= '?', ConsumoMedioAnnuale=?, CostoMedioUnitario=?, " +
                    "DataSottoscrizione=?, Durata=?, PrezzoVendita=?, IdAmministratore=? " +
                    "WHERE IdContratto=?";
            ps.setString(1, contratto.getNomeEnte());
            ps.setFloat(2, contratto.getConsumoMedioAnnuale());
            ps.setFloat(3, contratto.getCostoMedioUnitario());
            ps.setDate(4, (Date) contratto.getDataSottoscrizione());
            ps.setInt(5, contratto.getDurata());
            ps.setFloat(6, contratto.getPrezzoVendita());
            ps.setInt(7, contratto.getIdAmministatore());
            ps.setInt(8, contratto.getIdContratto());
            int rows=ps.executeUpdate();
            if(rows>0){
                bean.setIdContratto(contratto.getIdContratto());
                bean.setNomeEnte(contratto.getNomeEnte());
                bean.setConsumoMedioAnnuale(contratto.getConsumoMedioAnnuale());
                bean.setCostoMedioUnitario(contratto.getCostoMedioUnitario());
                bean.setDataSottoscrizione(contratto.getDataSottoscrizione());
                bean.setDurata(contratto.getDurata());
                bean.setPrezzoVendita(contratto.getPrezzoVendita());
                bean.setIdAmministatore(contratto.getIdAmministatore());
            }
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }

    public contrattoBean aggiungiContratto(contrattoBean contratto) throws SQLException{
        Connection con=null;
        PreparedStatement ps=null;
        contrattoBean bean=new contrattoBean();

        try{
            String query="INSERT INTO Contratto( NomeEnte, ConsumoMedioAnnuale, CostoMedioUnitario, " +
                    "DataSottoscrizione, Durata, PrezzoVendita, IdAmministratore)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps.setString(1, contratto.getNomeEnte());
            ps.setFloat(2, contratto.getConsumoMedioAnnuale());
            ps.setFloat(3, contratto.getCostoMedioUnitario());
            ps.setDate(4, (Date) contratto.getDataSottoscrizione());
            ps.setInt(5, contratto.getDurata());
            ps.setFloat(6, contratto.getPrezzoVendita());
            ps.setInt(7, contratto.getIdAmministatore());
            int rows=ps.executeUpdate();
            if(rows>0){
                bean.setNomeEnte(contratto.getNomeEnte());
                bean.setConsumoMedioAnnuale(contratto.getConsumoMedioAnnuale());
                bean.setCostoMedioUnitario(contratto.getCostoMedioUnitario());
                bean.setDataSottoscrizione(contratto.getDataSottoscrizione());
                bean.setDurata(contratto.getDurata());
                bean.setPrezzoVendita(contratto.getPrezzoVendita());
                bean.setIdAmministatore(contratto.getIdAmministatore());
            }
        }catch(Exception e){
            logger.log(Level.WARNING, e.getMessage());
        }
        return bean;
    }
}
