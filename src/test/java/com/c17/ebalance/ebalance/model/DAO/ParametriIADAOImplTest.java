package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ParametriIADAOImplTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ParametriIADAOImpl parametriIADAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        parametriIADAO = new ParametriIADAOImpl();
        Field dataSourceField = ParametriIADAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(parametriIADAO, dataSource);
    }

    @Test
    void visualizzaParametri() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdParametro")).thenReturn(1);
        when(resultSet.getString("Piano")).thenReturn("Piano Test");
        when(resultSet.getBoolean("FlagAttivazioneParametro")).thenReturn(true);
        when(resultSet.getInt("IdAmministratore")).thenReturn(1);

        List<ParametriIABean> result = parametriIADAO.visualizzaParametri();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo e contenga un solo elemento
        assertEquals(1, result.size());

        // Verifica che i valori nel bean siano corretti
        ParametriIABean bean = result.get(0);
        assertEquals(1, bean.getIdParametro());
        assertEquals("Piano Test", bean.getPiano());
        assertTrue(bean.getFlagAttivazioneParametro());
        assertEquals(1, bean.getIdAmministratore());
    }

    @Test
    void visualizzaInterazioneParametri() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdParametro")).thenReturn(1);
        when(resultSet.getString("TipoSorgente")).thenReturn("Tipo1");
        when(resultSet.getBoolean("FlagPreferenzaSorgente")).thenReturn(true);
        when(resultSet.getInt("PercentualeUtilizzoSorgente")).thenReturn(50);
        when(resultSet.getInt("PrioritaSorgente")).thenReturn(1);

        List<InteragisceBean> result = parametriIADAO.visualizzaInterazioneParametri();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo e contenga un elemento
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica che i valori nel bean siano corretti
        InteragisceBean bean = result.get(0);
        assertEquals(1, bean.getIdParametro());
        assertEquals("Tipo1", bean.getTipoSorgente());
        assertEquals(true, bean.getFlagPreferenzaSorgente());
        assertEquals(50, bean.getPercentualeUtilizzoSorgente());
        assertEquals(1, bean.getPrioritaSorgente());
    }

    @Test
    void ottieniParametriAttivi() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdParametro")).thenReturn(1);
        when(resultSet.getString("TipoSorgente")).thenReturn("Tipo1");
        when(resultSet.getBoolean("FlagPreferenzaSorgente")).thenReturn(true);
        when(resultSet.getInt("PercentualeUtilizzoSorgente")).thenReturn(50);
        when(resultSet.getInt("PrioritaSorgente")).thenReturn(1);

        List<InteragisceBean> result = parametriIADAO.ottieniParametriAttivi();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo e contenga un elemento
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica che i valori nel bean siano corretti
        InteragisceBean bean = result.get(0);
        assertEquals(1, bean.getIdParametro());
        assertEquals("Tipo1", bean.getTipoSorgente());
        assertTrue(bean.getFlagPreferenzaSorgente());
        assertEquals(50, bean.getPercentualeUtilizzoSorgente());
        assertEquals(1, bean.getPrioritaSorgente());
    }

    @Test
    void aggiornaPianoPersonalizzato() throws SQLException {
        // Arrange
        String preferenzaSorgente = "Pannello fotovoltaico";
        int percentualeUtilizzoPannelli = 80;
        int percentualeUtilizzoSEN = 20;
        String[] prioritaSorgenti = {"Pannello fotovoltaico"};

        // Act
        parametriIADAO.aggiornaPianoPersonalizzato(preferenzaSorgente, percentualeUtilizzoPannelli, percentualeUtilizzoSEN, prioritaSorgenti);

        // Assert
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setBoolean(anyInt(), anyBoolean());
        verify(preparedStatement, times(4)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(2)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).executeUpdate();
    }

    @Test
    void testAggiornaPianoAttivo_SalvaguardiaAmbientale() throws SQLException {
        // Configurazione del comportamento del mock per il primo update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Configurazione del comportamento del mock per il secondo update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = parametriIADAO.aggiornaPianoAttivo("SalvaguardiaAmbientale", 1);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).executeUpdate();

        // Verifica che il risultato sia true
        assertTrue(result);
    }

    @Test
    void testAggiornaPianoAttivo_EfficienzaEconomica() throws SQLException {
        // Configurazione del comportamento del mock per il primo update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Configurazione del comportamento del mock per il secondo update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = parametriIADAO.aggiornaPianoAttivo("EfficienzaEconomica", 1);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).executeUpdate();

        // Verifica che il risultato sia true
        assertTrue(result);
    }

    @Test
    void testAggiornaPianoAttivo_Personalizzato() throws SQLException {
        // Configurazione del comportamento del mock per il primo update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Configurazione del comportamento del mock per il secondo update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = parametriIADAO.aggiornaPianoAttivo("Personalizzato", 1);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).setString(anyInt(), anyString());
        verify(preparedStatement, times(2)).executeUpdate();

        // Verifica che il risultato sia true
        assertTrue(result);
    }

    @Test
    void testAggiornaPercentualeSEN() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdParametro")).thenReturn(1);
        when(resultSet.getFloat("PercentualeUtilizzoSorgente")).thenReturn(50.0f);

        // Chiamata al metodo da testare
        parametriIADAO.aggiornaPercentualeSEN(10);

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection, times(3)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).executeQuery();  // Configura il comportamento di executeQuery()
        verify(preparedStatement, times(1)).setFloat(1, 10.0f);
        verify(preparedStatement, times(1)).setInt(2, 1);
        verify(preparedStatement, times(1)).executeUpdate();
    }
}