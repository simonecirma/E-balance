package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.BatteriaBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BatteriaDAOImplTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BatteriaDAOImpl batteriaDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        batteriaDAO = new BatteriaDAOImpl();
        Field dataSourceField = BatteriaDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(batteriaDAO, dataSource);
    }

    @Test
    void visualizzaBatteriaTest() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdBatteria")).thenReturn(1);
        when(resultSet.getBoolean("FlagStatoBatteria")).thenReturn(false);
        when(resultSet.getFloat("CapacitaMax")).thenReturn(7000F);
        when(resultSet.getFloat("PercentualeCarica")).thenReturn(10F);

        List<BatteriaBean> batterie = batteriaDAO.visualizzaBatteria();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo
        assertNotNull(batterie);
        assertEquals(1, batterie.size());
        // Verifica che i valori nel bean siano corretti
        BatteriaBean result = batterie.get(0);
        assertNotNull(result);
        assertEquals(1, result.getIdBatteria());
        assertEquals(false, result.getFlagStatoBatteria());
        assertEquals(7000F, result.getCapacitaMax());
        assertEquals(10F, result.getPercentualeCarica());
    }

    @Test
    void ottieniPercentualeBatterieTest() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getFloat("Percentuale")).thenReturn(10.0F);

        float result = batteriaDAO.ottieniPercentualeBatterie();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertEquals(10.0F, result, 0.01); // 0.01 is the delta for float comparison
    }

    @Test
    void ottieniNumBatterieAttiveTest() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("NumBatterie")).thenReturn(3);

        int result = batteriaDAO.ottieniNumBatterieAttive();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertEquals(3, result);
    }
    @Test
    void aggiornaBatteriaTest() throws SQLException {
        // Set up mock behavior for the first battery
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getFloat("PercentualeCarica")).thenReturn(50.0f);
        when(resultSet.getFloat("CapacitaMax")).thenReturn(100.0f);

        // Set up mock behavior for the second battery
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getFloat("PercentualeCarica")).thenReturn(60.0f);
        when(resultSet.getFloat("CapacitaMax")).thenReturn(120.0f);

        // Call the method
        float result = batteriaDAO.aggiornaBatteria(30.0f, 2);

        // Verify that the necessary methods were called
        verify(connection, times(4)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).executeQuery();
        verify(preparedStatement, times(2)).executeUpdate();

        // Verify the result
        assertEquals(0.0f, result); // Assuming there is no excess charge in this case
    }
}
