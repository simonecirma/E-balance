package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProduzioneDAOImplTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProduzioneDAOImpl produzioneDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        produzioneDAO = new ProduzioneDAOImpl();
        Field dataSourceField = ProduzioneDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(produzioneDAO, dataSource);
    }

    @Test
    void visualizzaProduzione() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getInt("IdProduzione")).thenReturn(1);
        when(resultSet.getDate("DataProduzione")).thenReturn(Date.valueOf("2023-01-01"));
        when(resultSet.getFloat("ProduzioneGiornaliera")).thenReturn(50.0F);

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e del PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        List<ArchivioProduzioneBean> produzione = produzioneDAO.visualizzaProduzione();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di produzione sia non nulla e contenga un elemento
        assertNotNull(produzione);
        assertEquals(1, produzione.size());

        // Verifica che la produzione all'interno della lista sia corretta
        ArchivioProduzioneBean result = produzione.get(0);
        assertNotNull(result);
        assertEquals(1, result.getIdProduzione());
        assertEquals(Date.valueOf("2023-01-01"), result.getDataProduzione());
        assertEquals(50.0F, result.getProduzioneGiornaliera(), 0.001); // Confronto approssimato per float
    }

    @Test
    void visualizzaProduzioneSorgente() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getInt("IdSorgente")).thenReturn(1);
        when(resultSet.getFloat("ProduzioneAttuale")).thenReturn(100.0F);
        when(resultSet.getDate("DataInstallazione")).thenReturn(Date.valueOf("2022-01-01"));
        when(resultSet.getString("Tipologia")).thenReturn("Solar");
        when(resultSet.getBoolean("FlagAttivazioneSorgente")).thenReturn(true);
        when(resultSet.getBoolean("FlagStatoSorgente")).thenReturn(true);

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e del PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        List<SorgenteBean> produzioneSorgente = produzioneDAO.visualizzaProduzioneSorgente();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di produzioneSorgente sia non nulla e contenga un elemento
        assertNotNull(produzioneSorgente);
        assertEquals(1, produzioneSorgente.size());

        // Verifica che la produzioneSorgente all'interno della lista sia corretta
        SorgenteBean result = produzioneSorgente.get(0);
        assertNotNull(result);
        assertEquals(1, result.getIdSorgente());
        assertEquals(100.0F, result.getProduzioneAttuale(), 0.001); // Confronto approssimato per float
        assertEquals(Date.valueOf("2022-01-01"), result.getDataInstallazione());
        assertEquals("Solar", result.getTipologia());
        assertTrue(result.getFlagAttivazioneSorgente());
        assertTrue(result.getFlagStatoSorgente());
    }


    @Test
    void ottieniProduzioneProdotta() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getFloat("ProduzioneSorgente")).thenReturn(500.0F);

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e del PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        float produzioneProdotta = produzioneDAO.ottieniProduzioneProdotta();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica il risultato del metodo
        assertEquals(500.0F, produzioneProdotta, 0.001); // Confronto approssimato per float
    }


    @Test
    void ottieniProduzioneSEN() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getFloat("ProduzioneSEN")).thenReturn(200.0F);

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e del PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        float produzioneSEN = produzioneDAO.ottieniProduzioneSEN();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica il risultato del metodo
        assertEquals(200.0F, produzioneSEN, 0.001); // Confronto approssimato per float
    }


    @Test
    void ottieniTipoSorgente() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getString("Tipo")).thenReturn("Solare");

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e del PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        List<TipoSorgenteBean> tipoSorgente = produzioneDAO.ottieniTipoSorgente();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica la lista di risultati
        assertNotNull(tipoSorgente);
        assertEquals(1, tipoSorgente.size());

        // Verifica che il tipo sia corretto
        TipoSorgenteBean result = tipoSorgente.get(0);
        assertNotNull(result);
        assertEquals("Solare", result.getTipo());
    }

    @Test
    void simulaProduzione() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getBoolean("FlagAttivazioneSorgente")).thenReturn(true);
        when(resultSet.getBoolean("FlagStatoSorgente")).thenReturn(true);

        // Configura il comportamento dei PreparedStatement simulati
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e dei PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        produzioneDAO.simulaProduzione(1, 50.0F, Date.valueOf("2024-02-02"));

        // Verifica che i PreparedStatement siano stati creati correttamente
        verify(connection, times(5)).prepareStatement(any(String.class));

        // Verifica che i metodi executeUpdate siano stati chiamati
        verify(preparedStatement, times(3)).executeUpdate();

        // Verifica che i metodi close siano stati chiamati nel blocco finally
        verify(preparedStatement, times(5)).close();
        verify(connection).close();
    }

    @Test
    void ottieniSorgenti() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getInt("IdSorgente")).thenReturn(5); // Ad esempio, il risultato atteso

        // Configura il comportamento dei PreparedStatement simulati
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e dei PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        int risultato = produzioneDAO.ottieniSorgenti();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che il metodo getInt sia stato chiamato
        verify(resultSet).getInt("IdSorgente");

        // Verifica che il metodo close sia stato chiamato nel blocco finally
        verify(preparedStatement).close();
        verify(connection).close();

        // Verifica il risultato del test
        assertEquals(5, risultato);
    }

    @Test
    void simulaProduzioneSEN() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getBoolean("FlagStatoSorgente")).thenReturn(true); // Ad esempio, il risultato atteso
        when(resultSet.getBoolean("FlagAttivazioneSorgente")).thenReturn(true); // Ad esempio, il risultato atteso

        // Configura il comportamento dei PreparedStatement simulati
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e dei PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        produzioneDAO.simulaProduzioneSEN(50.0F, new Date(System.currentTimeMillis()));

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection, times(5)).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement, times(2)).executeQuery();

        // Verifica che il metodo getBoolean sia stato chiamato
        verify(resultSet).getBoolean("FlagStatoSorgente");
        verify(resultSet).getBoolean("FlagAttivazioneSorgente");

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(preparedStatement, times(5)).close();
        verify(connection).close();
    }

    @Test
    void energiaRinnovabileProdottaPerData() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getFloat("Produzione")).thenReturn(100.0F); // Ad esempio, il risultato atteso

        // Configura il comportamento dei PreparedStatement simulati
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Configura il comportamento della connessione e dei PreparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Supponendo che startDate e endDate siano oggetti java.util.Date
        Calendar calendario = Calendar.getInstance();
        java.util.Date startDate = calendario.getTime();
        Date sqlStartDate = new Date(startDate.getTime());
        calendario.add(Calendar.DAY_OF_YEAR, 10);
        java.util.Date endDate = calendario.getTime();
        Date sqlEndDate = new Date(endDate.getTime());

        // Esegui il test del metodo
        float risultato = produzioneDAO.energiaRinnovabileProdottaPerData(sqlStartDate, sqlEndDate);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che il metodo getFloat sia stato chiamato
        verify(resultSet).getFloat("Produzione");

        // Verifica il risultato del metodo
        assertEquals(100.0F, risultato, 0.001); // Confronto approssimato per float
    }
}
