package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MeteoDAOImplTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private MeteoDAOImpl meteoDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        meteoDAO = new MeteoDAOImpl();
        Field dataSourceField = MeteoDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(meteoDAO, dataSource);
    }

    @Test
    void testGetCondizioniMeteo() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un solo risultato
        when(resultSet.getInt("IdMeteo")).thenReturn(1);
        when(resultSet.getDate("DataRilevazione")).thenReturn(java.sql.Date.valueOf("2022-01-01"));
        when(resultSet.getTime("OraRilevazione")).thenReturn(new Time(new Date().getTime()));
        when(resultSet.getFloat("VelocitaVento")).thenReturn(10.0F);
        when(resultSet.getInt("ProbabilitaPioggia")).thenReturn(30);
        when(resultSet.getString("CondizioniMetereologiche")).thenReturn("Soleggiato");

        List<MeteoBean> condizioni = meteoDAO.getCondizioniMeteo();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di condizioni meteo sia non nulla e contenga un elemento
        assertNotNull(condizioni);
        assertEquals(1, condizioni.size());

        // Verifica che la condizione meteo all'interno della lista sia corretta
        MeteoBean result = condizioni.get(0);
        assertNotNull(result);
        assertEquals(1, result.getIdMeteo());
        assertEquals(java.sql.Date.valueOf("2022-01-01"), result.getDataRilevazione());
        assertNotNull(result.getOraRilevazione());
        assertEquals(10.0F, result.getVelocitaVento(), 0.001); // Confronto approssimato per float
        assertEquals(30, result.getProbabilitaPioggia());
        assertEquals("Soleggiato", result.getCondizioniMetereologiche());
    }

    @Test
    void insertPrevisioni() throws SQLException {
        java.sql.Date sqlDate = java.sql.Date.valueOf("2022-01-01");
        int orario = 12;
        float vel = 15.0F;
        int prob = 50;
        String condizioneCasuale = "Nuvoloso";

        meteoDAO.insertPrevisioni(sqlDate, orario, vel, prob, condizioneCasuale);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica dei parametri passati al PreparedStatement
        verify(preparedStatement).setDate(1, sqlDate);
        verify(preparedStatement).setTime(2, Time.valueOf("12:00:00"));
        verify(preparedStatement).setFloat(3, vel);
        verify(preparedStatement).setInt(4, prob);
        verify(preparedStatement).setString(5, condizioneCasuale);

        // Verifica che il metodo executeUpdate sia stato chiamato
        verify(preparedStatement).executeUpdate();

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void getCondizione() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Simula due risultati
        when(resultSet.getString("Condizione")).thenReturn("Soleggiato").thenReturn("Nuvoloso");

        List<String> condizioni = meteoDAO.getCondizione();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di condizioni sia non nulla e contenga due elementi
        assertNotNull(condizioni);
        assertEquals(2, condizioni.size());

        // Verifica che le condizioni all'interno della lista siano corrette
        assertEquals("Soleggiato", condizioni.get(0));
        assertEquals("Nuvoloso", condizioni.get(1));

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void verificaPresenza() throws SQLException {
        java.sql.Date sqlDate = java.sql.Date.valueOf("2022-01-01");
        int orario = 12;

        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true);

        boolean result = meteoDAO.verificaPresenza(sqlDate, orario);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica dei parametri passati al PreparedStatement
        verify(preparedStatement).setDate(1, sqlDate);
        verify(preparedStatement).setTime(2, Time.valueOf("12:00:00"));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertTrue(result);

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void mediaGiornaliera() throws SQLException {
        Calendar calendario = Calendar.getInstance();
        Date data = calendario.getTime();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());

        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getDate("DataRilevazione")).thenReturn(sqlDate);
        when(resultSet.getFloat("mediaVel")).thenReturn(15.0F);
        when(resultSet.getInt("mediaPioggia")).thenReturn(25);

        // Utilizza un matcher personalizzato per la data nel metodo set
        doNothing().when(preparedStatement).setDate(eq(1), any(java.sql.Date.class));

        List<MeteoBean> condizioni = meteoDAO.mediaGiornaliera();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica dei parametri passati al PreparedStatement
        verify(preparedStatement).setDate(eq(1), any(java.sql.Date.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di condizioni sia non nulla e contenga un elemento
        assertNotNull(condizioni);
        assertEquals(1, condizioni.size());

        // Verifica che la condizione all'interno della lista sia corretta
        MeteoBean result = condizioni.get(0);
        assertNotNull(result);
        assertEquals(sqlDate, result.getDataRilevazione());
        assertEquals(15.0F, result.getVelocitaVento(), 0.001); // Confronto approssimato per float
        assertEquals(25, result.getProbabilitaPioggia());
        assertEquals("Ventilato", result.getCondizioniMetereologiche()); // Confronto con le condizioni definite nel tuo codice

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(preparedStatement).close();
        verify(connection).close();
    }
}