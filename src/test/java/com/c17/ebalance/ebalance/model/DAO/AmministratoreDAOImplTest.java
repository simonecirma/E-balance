package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
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

class AmministratoreDAOImplTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AmministratoreDAOImpl amministratoreDAO;
    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        amministratoreDAO = new AmministratoreDAOImpl();
        Field dataSourceField = AmministratoreDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(amministratoreDAO, dataSource);
    }

    @Test
    void verificaSuperAdmin() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo verificaSuperAdmin
        when(resultSet.next()).thenReturn(true); // Simula che ci sia almeno un risultato
        when(resultSet.getInt(anyString())).thenReturn(1); // Simula il risultato della colonna "FlagTipo"

        boolean result = amministratoreDAO.verificaSuperAdmin();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia true
        assertTrue(result);
    }

    @Test
    void login() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo login
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Restituisci true una volta e poi false
        when(resultSet.getInt("IdAmministratore")).thenReturn(1);
        when(resultSet.getString("Nome")).thenReturn("NomeUtente");
        when(resultSet.getString("Cognome")).thenReturn("CognomeUtente");
        when(resultSet.getDate("DataNascita")).thenReturn(Date.valueOf("1990-01-01"));
        when(resultSet.getString("Email")).thenReturn("utente@example.com");
        when(resultSet.getString("Password")).thenReturn("password");
        when(resultSet.getBoolean("FlagTipo")).thenReturn(true);

        AmministratoreBean result = amministratoreDAO.login("utente@example.com", "password");

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "utente@example.com");
        verify(preparedStatement).setString(2, "password");
        verify(preparedStatement, times(1)).executeQuery(); // Verifica che executeQuery sia chiamato due volte

        // Verifica che il risultato non sia nullo
        assertNotNull(result);

        // Verifica che i valori nel bean siano corretti
        assertEquals(1, result.getIdAmministratore());
        assertEquals("NomeUtente", result.getNome());
        assertEquals("CognomeUtente", result.getCognome());
        assertEquals(Date.valueOf("1990-01-01"), result.getDataNascita());
        assertEquals("utente@example.com", result.getEmail());
        assertEquals("password", result.getPassword());
        assertTrue(result.getFlagTipo());
    }

    @Test
    void visualizzaAmministratori() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo visualizzaAmministratori
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Restituisci true una volta e poi false
        when(resultSet.getInt("IdAmministratore")).thenReturn(1);
        when(resultSet.getString("Email")).thenReturn("admin1@example.com");
        when(resultSet.getString("Nome")).thenReturn("Admin1");
        when(resultSet.getString("Cognome")).thenReturn("AdminCognome1");
        when(resultSet.getString("Password")).thenReturn("password1");
        when(resultSet.getBoolean("FlagTipo")).thenReturn(true);

        List<AmministratoreBean> result = amministratoreDAO.visualizzaAmministratori();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato non sia nullo
        assertNotNull(result);
        assertEquals(1, result.size()); // Assicurati che ci sia un solo amministratore nel risultato

        // Verifica che i valori nel bean siano corretti
        AmministratoreBean admin = result.get(0);
        assertEquals(1, admin.getIdAmministratore());
        assertEquals("admin1@example.com", admin.getEmail());
        assertEquals("Admin1", admin.getNome());
        assertEquals("AdminCognome1", admin.getCognome());
        assertEquals("password1", admin.getPassword());
        assertTrue(admin.getFlagTipo());
    }

    @Test
    void aggiornaAmministratore()  throws SQLException {
        // Configura il comportamento del PreparedStatement simulato per il metodo aggiornaAmministratore
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simula l'aggiornamento di una riga

        AmministratoreBean amministratore = new AmministratoreBean();
        amministratore.setIdAmministratore(1);
        amministratore.setNome("NuovoNome");
        amministratore.setCognome("NuovoCognome");
        amministratore.setDataNascita(Date.valueOf("1990-01-01"));
        amministratore.setEmail("nuovo@email.com");
        amministratore.setPassword("nuovaPassword");

        AmministratoreBean result = amministratoreDAO.aggiornaAmministratore(amministratore);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "NuovoNome");
        verify(preparedStatement).setString(2, "NuovoCognome");
        verify(preparedStatement).setDate(3, Date.valueOf("1990-01-01"));
        verify(preparedStatement).setString(4, "nuovo@email.com");
        verify(preparedStatement).setString(5, "nuovaPassword");
        verify(preparedStatement).setInt(6, 1); // ID dell'amministratore
        verify(preparedStatement).executeUpdate();

        // Verifica che il risultato non sia nullo
        assertNotNull(result);

        // Verifica che il bean aggiornato abbia gli stessi valori del bean in input
        assertEquals(amministratore.getIdAmministratore(), result.getIdAmministratore());
        assertEquals(amministratore.getNome(), result.getNome());
        assertEquals(amministratore.getCognome(), result.getCognome());
        assertEquals(amministratore.getDataNascita(), result.getDataNascita());
        assertEquals(amministratore.getEmail(), result.getEmail());
        assertEquals(amministratore.getPassword(), result.getPassword());
    }

    @Test
    void aggiungiAmministratore() throws SQLException {
        // Configura il comportamento del PreparedStatement simulato per il metodo aggiungiAmministratore
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simula l'aggiunta di una riga

        AmministratoreBean amministratore = new AmministratoreBean();
        amministratore.setNome("NuovoNome");
        amministratore.setCognome("NuovoCognome");
        amministratore.setDataNascita(Date.valueOf("1990-01-01"));
        amministratore.setEmail("nuovo@email.com");
        amministratore.setPassword("nuovaPassword");
        amministratore.setFlagTipo(true);

        amministratoreDAO.aggiungiAmministratore(amministratore);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "NuovoNome");
        verify(preparedStatement).setString(2, "NuovoCognome");
        verify(preparedStatement).setDate(3, Date.valueOf("1990-01-01"));
        verify(preparedStatement).setString(4, "nuovo@email.com");
        verify(preparedStatement).setString(5, "nuovaPassword");
        verify(preparedStatement).setBoolean(6, true);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void getById()  throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo getById
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Restituisci true una volta e poi false
        when(resultSet.getString("Nome")).thenReturn("AdminNome");
        when(resultSet.getString("Cognome")).thenReturn("AdminCognome");
        when(resultSet.getDate("DataNascita")).thenReturn(Date.valueOf("1990-01-01"));
        when(resultSet.getString("Email")).thenReturn("admin@example.com");
        when(resultSet.getString("Password")).thenReturn("adminPassword");
        when(resultSet.getBoolean("FlagTipo")).thenReturn(true);

        AmministratoreBean result = amministratoreDAO.getById(1);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato non sia nullo
        assertNotNull(result);

        // Verifica che i valori nel bean siano corretti
        assertEquals("AdminNome", result.getNome());
        assertEquals("AdminCognome", result.getCognome());
        assertEquals(Date.valueOf("1990-01-01"), result.getDataNascita());
        assertEquals("admin@example.com", result.getEmail());
        assertEquals("adminPassword", result.getPassword());
        assertTrue(result.getFlagTipo());
    }

    @Test
    void rimuoviAmministratore()  throws SQLException {
        // Configura il comportamento del PreparedStatement simulato per il metodo rimuoviAmministratore
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simula la rimozione di una riga

        amministratoreDAO.rimuoviAmministratore(1);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void verificaPresenzaEmail()  throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo verificaPresenzaEmail
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Restituisci true una volta e poi false

        boolean result = amministratoreDAO.verificaPresenzaEmail("test@example.com");

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "test@example.com");
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertTrue(result);
    }
}