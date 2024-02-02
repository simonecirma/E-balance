package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ReportBean;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportDAOImplTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ReportDAOImpl reportDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        reportDAO = new ReportDAOImpl();
        Field dataSourceField = ReportDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(reportDAO, dataSource);
    }

    @Test
    void visualizzaReport() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getDate("DataEmissione")).thenReturn(java.sql.Date.valueOf("2022-01-01"));
        when(resultSet.getString("NomeReport")).thenReturn("Report1");
        when(resultSet.getInt("IdAmministratore")).thenReturn(1);

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Esegui il test del metodo
        List<ReportBean> report = reportDAO.visualizzaReport();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di report sia non nulla e contenga un elemento
        assertNotNull(report);
        assertEquals(1, report.size());

        // Verifica che il report all'interno della lista sia corretto
        ReportBean result = report.get(0);
        assertNotNull(result);
        assertEquals(java.sql.Date.valueOf("2022-01-01"), result.getDataEmissione());
        assertEquals("Report1", result.getNomeReport());
        assertEquals(1, result.getIdAmministratore());

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(resultSet).close();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void ultimoReport() throws SQLException {
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un risultato
        when(resultSet.getInt("count")).thenReturn(5); // Simula il risultato del conteggio

        // Configura il comportamento del PreparedStatement simulato
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Esegui il test del metodo
        int result = reportDAO.ultimoReport();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che il metodo executeQuery sia stato chiamato
        verify(preparedStatement).executeQuery();

        // Verifica il risultato del metodo
        assertEquals(5, result);

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(resultSet).close();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void aggiungiReport() throws SQLException {
        // Configura il comportamento del PreparedStatement simulato
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Esegui il test del metodo
        ReportBean report = new ReportBean();
        report.setDataEmissione(new Date(System.currentTimeMillis()));
        report.setIdAmministratore(1);
        report.setNomeReport("Report di test");

        reportDAO.aggiungiReport(report);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));

        // Verifica che i parametri siano stati impostati correttamente sul PreparedStatement
        verify(preparedStatement).setDate(1, (Date) report.getDataEmissione());
        verify(preparedStatement).setInt(2, report.getIdAmministratore());
        verify(preparedStatement).setString(3, report.getNomeReport());

        // Verifica che il metodo executeUpdate sia stato chiamato
        verify(preparedStatement).executeUpdate();

        // Verifica che i metodi di chiusura siano stati chiamati nel blocco finally
        verify(preparedStatement).close();
        verify(connection).close();
    }
}