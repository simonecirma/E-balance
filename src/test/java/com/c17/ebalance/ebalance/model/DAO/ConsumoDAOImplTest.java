package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Calendar;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConsumoDAOImplTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ConsumoDAOImpl consumoDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);  // Properly set up the mock


        consumoDAO = new ConsumoDAOImpl();
        Field dataSourceField = ConsumoDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(consumoDAO, dataSource);
    }


    @Test
    void visualizzaConsumo() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdEdificio")).thenReturn(1);
        when(resultSet.getString("NomeEdificio")).thenReturn("Edificio A");
        when(resultSet.getFloat("ConsumoAttuale")).thenReturn(100.5F);

        List<ConsumoEdificioBean> result = consumoDAO.visualizzaConsumo();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo
        assertNotNull(result);
        // Verifica che la lista contenga un elemento
        assertEquals(1, result.size());

        // Verifica che i valori nel bean siano corretti
        ConsumoEdificioBean bean = result.get(0);
        assertEquals(1, bean.getIdEdificio());
        assertEquals("Edificio A", bean.getNomeEdificio());
        assertEquals(100.5F, bean.getConsumoAttuale());
    }

    @Test
    void ottieniConsumiEdifici() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getFloat("Consumo")).thenReturn(150.75F);

        float result = consumoDAO.ottieniConsumiEdifici();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertEquals(150.75F, result);
    }

    @Test
    void visualizzaStoricoConsumi() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("Consumo")).thenReturn(100);
        when(resultSet.getDate("DataConsumo")).thenReturn(Date.valueOf("2022-01-01"));

        List<ArchivioConsumoBean> result = consumoDAO.visualizzaStoricoConsumi();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo
        assertNotNull(result);
        // Verifica che la lista contenga un elemento
        assertEquals(1, result.size());

        // Verifica che i valori nel bean siano corretti
        ArchivioConsumoBean bean = result.get(0);
        assertEquals(100, bean.getConsumoGiornaliero());
        assertEquals(Date.valueOf("2022-01-01"), bean.getDataConsumo());
    }

    @Test
    void simulaConsumo() throws SQLException {
        // Mock behavior for the resultSet
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        // Call the method to test
        consumoDAO.simulaConsumo(10.0F, 1, Date.valueOf("2024-02-02"));

        // Verify that the expected methods were called
        verify(connection, times(3)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).executeUpdate();
        verify(preparedStatement, times(3)).close();
        verify(connection, times(1)).close();
    }

    @Test
    void ottieniNumEdifici() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdEdificio")).thenReturn(5);

        int result = consumoDAO.ottieniNumEdifici();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertEquals(5, result);
    }

    @Test
    void getConsumoPerData() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);  // Simula due chiamate, una per il risultato e una per la fine
        when(resultSet.getFloat("ConsumoTotale")).thenReturn(10.5F);

        // Crea un oggetto Date utilizzando il timestamp attuale
        Date dataInizio = new Date(Calendar.getInstance().getTimeInMillis());
        Date dataFine = new Date(Calendar.getInstance().getTimeInMillis());

        float result = consumoDAO.getConsumoPerData(dataInizio, dataFine);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).setDate(1, dataInizio);
        verify(preparedStatement).setDate(2, dataFine);
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertEquals(10.5F, result);
    }
}
