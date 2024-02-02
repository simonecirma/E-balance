package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.VenditaBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VenditaDAOImplTest {

    @Mock
    private DataSource dataSource;
     @Mock
     private Connection connection;

     @Mock
     private PreparedStatement preparedStatement;

     @Mock
     private ResultSet resultSet;

     @InjectMocks
     private VenditaDAOImpl venditaDAO;

     @BeforeEach
     void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
         MockitoAnnotations.openMocks(this);

         when(dataSource.getConnection()).thenReturn(connection);
         when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
         when(preparedStatement.executeQuery()).thenReturn(resultSet);

         venditaDAO = new VenditaDAOImpl();
         Field dataSourceField = VenditaDAOImpl.class.getDeclaredField("ds");
         dataSourceField.setAccessible(true);
         dataSourceField.set(venditaDAO, dataSource);
     }

    @Test
    void getVendite() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdVendita")).thenReturn(1);
        when(resultSet.getFloat("EnergiaVenduta")).thenReturn(1000F);
        when(resultSet.getDate("DataVendita")).thenReturn(Date.valueOf("2022-01-01"));
        when(resultSet.getFloat("RicavoTotale")).thenReturn(120F);
        when(resultSet.getInt("IdAmministratore")).thenReturn(1);

        // Chiamata al metodo da testare
        List<VenditaBean> result = venditaDAO.getVendite(Date.valueOf("2022-01-01"), Date.valueOf("2022-01-31"));

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setDate(1, Date.valueOf("2022-01-01"));
        verify(preparedStatement).setDate(2, Date.valueOf("2022-01-31"));
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato non sia nullo
        assertNotNull(result);

        // Verifica che la lista contenga un elemento
        assertEquals(1, result.size());

        // Verifica che i valori nel bean siano corretti
        VenditaBean venditaBean = result.get(0);
        assertEquals(1, venditaBean.getIdVendita());
        assertEquals(1000F, venditaBean.getEnergiaVenduta());
        assertEquals(Date.valueOf("2022-01-01"), venditaBean.getDataVendita());
        assertEquals(120F, venditaBean.getRicavoTotale());
        assertEquals(1, venditaBean.getIdAmministratore());
    }

    @Test
    void getRicavoTotalePerData() throws SQLException {
        // Mocking behavior for ResultSet
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getFloat("RicavoTOT")).thenReturn(100.50F);

        // Call the method with sample date range
        Date dataInizio = Date.valueOf("2023-01-01");
        Date dataFine = Date.valueOf("2023-12-31");
        float ricavo = venditaDAO.getRicavoTotalePerData(dataInizio, dataFine);

        // Verify that the necessary methods were called
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setDate(1, dataInizio);
        verify(preparedStatement).setDate(2, dataFine);
        verify(preparedStatement).executeQuery();

        // Verify that the result is as expected
        assertEquals(100.50F, ricavo);
    }

    @Test
    void effetuaVendita() throws SQLException {
        // Prepare data for the test
        VenditaBean venditaBean = new VenditaBean();
        venditaBean.setEnergiaVenduta(100F);
        venditaBean.setDataVendita(Date.valueOf("2022-01-01"));
        venditaBean.setRicavoTotale(50F);
        venditaBean.setIdAmministratore(1);

        // Configure the behavior of mocked objects
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Assuming one row is affected

        // Call the method to be tested
        venditaDAO.effetuaVendita(venditaBean);

        // Verify that the methods of mock objects are called correctly
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setFloat(1, 100F);
        verify(preparedStatement).setDate(2, Date.valueOf("2022-01-01"));
        verify(preparedStatement).setFloat(3, 50F);
        verify(preparedStatement).setFloat(4, 1);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(connection).close();
    }
}