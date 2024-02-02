package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
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

class ContrattoDAOImplTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ContrattoDAOImpl contrattoDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        contrattoDAO = new ContrattoDAOImpl();
        Field dataSourceField = ContrattoDAOImpl.class.getDeclaredField("ds");
        dataSourceField.setAccessible(true);
        dataSourceField.set(contrattoDAO, dataSource);
    }
    @Test
    void visualizzaContratto() throws SQLException {
        // Configura il comportamento del ResultSet simulato
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("IdContratto")).thenReturn(3);
        when(resultSet.getString("NomeEnte")).thenReturn("Enel Energia");
        when(resultSet.getFloat("ConsumoMedioAnnuale")).thenReturn(6000000F);
        when(resultSet.getFloat("CostoMedioUnitario")).thenReturn(0.12F);
        when(resultSet.getDate("DataSottoscrizione")).thenReturn(Date.valueOf("2020-01-01"));
        when(resultSet.getInt("Durata")).thenReturn(120);
        when(resultSet.getFloat("PrezzoVendita")).thenReturn(0.09F);
        when(resultSet.getInt("IdAmministratore")).thenReturn(1);

        ContrattoBean result = contrattoDAO.visualizzaContratto();

        // Verifica che i metodi di mock siano stati chiamati correttamente
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo
        assertNotNull(result);

        // Verifica che i valori nel bean siano corretti
        assertEquals(3, result.getIdContratto());
        assertEquals("Enel Energia", result.getNomeEnte());
        assertEquals(6000000F, result.getConsumoMedioAnnuale());
        assertEquals(0.12F, result.getCostoMedioUnitario());
        assertEquals(Date.valueOf("2020-01-01"), result.getDataSottoscrizione());
        assertEquals(120, result.getDurata());
        assertEquals(0.09F, result.getPrezzoVendita());
        assertEquals(1, result.getIdAmministatore());
    }

    @Test
    void visualizzaStoricoContratti() throws SQLException{
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un solo risultato
        when(resultSet.getInt("IdContratto")).thenReturn(1);
        when(resultSet.getString("NomeEnte")).thenReturn("Enel Energia");
        when(resultSet.getFloat("ConsumoMedioAnnuale")).thenReturn(2000000F);
        when(resultSet.getFloat("CostoMedioUnitario")).thenReturn(0.05F);
        when(resultSet.getDate("DataSottoscrizione")).thenReturn(java.sql.Date.valueOf("2022-01-01"));
        when(resultSet.getInt("Durata")).thenReturn(120);
        when(resultSet.getFloat("PrezzoVendita")).thenReturn(0.03F);
        when(resultSet.getInt("IdAmministratore")).thenReturn(3);

        List<ContrattoBean> contratti = contrattoDAO.visualizzaStoricoContratti();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).executeQuery();

        // Verifica che la lista di contratti sia non nulla e contenga un elemento
        assertNotNull(contratti);
        assertEquals(1, contratti.size());

        // Verifica che il contratto all'interno della lista sia corretto
        ContrattoBean result = contratti.get(0);
        assertNotNull(result);
        assertEquals(1, result.getIdContratto());
        assertEquals("Enel Energia", result.getNomeEnte());
        assertEquals(2000000F, result.getConsumoMedioAnnuale(), 0.001); // Confronto approssimato per float
        assertEquals(0.05F, result.getCostoMedioUnitario(), 0.001);
        assertEquals(java.sql.Date.valueOf("2022-01-01"), result.getDataSottoscrizione());
        assertEquals(120, result.getDurata());
        assertEquals(0.03F, result.getPrezzoVendita(), 0.001);
        assertEquals(3, result.getIdAmministatore());
    }

    @Test
    void aggiornaContratto() throws SQLException{
        // Configura il comportamento del ResultSet simulato per il metodo executeUpdate
        when(preparedStatement.executeUpdate()).thenReturn(1);

        ContrattoBean contrattoAggiornato = new ContrattoBean(1, "Enel Energia", 2000000, 0.05F,
                Date.valueOf("2000-01-01"), 120, 0.03F, 3);

        ContrattoBean result = contrattoDAO.aggiornaContratto(contrattoAggiornato);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).setString(1, contrattoAggiornato.getNomeEnte());
        verify(preparedStatement).setFloat(2, contrattoAggiornato.getConsumoMedioAnnuale());
        verify(preparedStatement).setFloat(3, contrattoAggiornato.getCostoMedioUnitario());
        verify(preparedStatement).setDate(4, (Date) contrattoAggiornato.getDataSottoscrizione());
        verify(preparedStatement).setInt(5, contrattoAggiornato.getDurata());
        verify(preparedStatement).setFloat(6, contrattoAggiornato.getPrezzoVendita());
        verify(preparedStatement).setInt(7, contrattoAggiornato.getIdAmministatore());
        verify(preparedStatement).setInt(8, contrattoAggiornato.getIdContratto());
        verify(preparedStatement).executeUpdate();

        // Verifica che il risultato sia non nullo
        assertNotNull(result);

        // Verifica che i valori nel bean siano corretti
        assertEquals(1, result.getIdContratto());
        assertEquals("Enel Energia", result.getNomeEnte());
        assertEquals(2000000F, result.getConsumoMedioAnnuale(), 0.001); // Confronto approssimato per float
        assertEquals(0.05F, result.getCostoMedioUnitario(), 0.001);
        assertEquals(Date.valueOf("2000-01-01"), result.getDataSottoscrizione());
        assertEquals(120, result.getDurata());
        assertEquals(0.03F, result.getPrezzoVendita(), 0.001);
        assertEquals(3, result.getIdAmministatore());
    }

    @Test
    void aggiungiContratto() throws SQLException{
        // Configura il comportamento del ResultSet simulato per il metodo executeUpdate
        when(preparedStatement.executeUpdate()).thenReturn(1);

        ContrattoBean contrattoDaAggiungere = new ContrattoBean();
        contrattoDaAggiungere.setNomeEnte("Enel Energia");
        contrattoDaAggiungere.setConsumoMedioAnnuale(2000000);
        contrattoDaAggiungere.setCostoMedioUnitario(0.05F);
        contrattoDaAggiungere.setDataSottoscrizione(Date.valueOf("2000-01-01"));
        contrattoDaAggiungere.setDurata(120);
        contrattoDaAggiungere.setPrezzoVendita(0.03F);
        contrattoDaAggiungere.setIdAmministatore(3);

        ContrattoBean result = contrattoDAO.aggiungiContratto(contrattoDaAggiungere);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).setString(1, contrattoDaAggiungere.getNomeEnte());
        verify(preparedStatement).setFloat(2, contrattoDaAggiungere.getConsumoMedioAnnuale());
        verify(preparedStatement).setFloat(3, contrattoDaAggiungere.getCostoMedioUnitario());
        verify(preparedStatement).setDate(4, (Date) contrattoDaAggiungere.getDataSottoscrizione());
        verify(preparedStatement).setInt(5, contrattoDaAggiungere.getDurata());
        verify(preparedStatement).setFloat(6, contrattoDaAggiungere.getPrezzoVendita());
        verify(preparedStatement).setInt(7, contrattoDaAggiungere.getIdAmministatore());
        verify(preparedStatement).executeUpdate();

        // Verifica che il risultato sia non nullo
        assertNotNull(result);

        // Verifica che i valori nel bean siano corretti
        assertEquals("Enel Energia", result.getNomeEnte());
        assertEquals(2000000F, result.getConsumoMedioAnnuale(), 0.001); // Confronto approssimato per float
        assertEquals(0.05F, result.getCostoMedioUnitario(), 0.001);
        assertEquals(Date.valueOf("2000-01-01"), result.getDataSottoscrizione());
        assertEquals(120, result.getDurata());
        assertEquals(0.03F, result.getPrezzoVendita(), 0.001);
        assertEquals(3, result.getIdAmministatore());
    }

    @Test
    void verificaPrimoContratto() throws SQLException{
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un solo risultato

        boolean result = contrattoDAO.verificaPrimoContratto();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertTrue(result); // Se c'è almeno un risultato, dovrebbe restituire true

        // Simuliamo un secondo risultato per verificare il caso in cui ci siano più contratti
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        result = contrattoDAO.verificaPrimoContratto();

        assertFalse(result); // Ora dovrebbe restituire false perché ci sono più di un contratto
    }

    @Test
    void getContrattoAttivo() throws SQLException{
        Date dataInizio = Date.valueOf("2023-01-01");
        Date dataFine = Date.valueOf("2023-12-31");

        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un solo risultato
        when(resultSet.getInt("IdContratto")).thenReturn(1);
        when(resultSet.getString("NomeEnte")).thenReturn("Enel Energia");
        when(resultSet.getFloat("ConsumoMedioAnnuale")).thenReturn(2000000F);
        when(resultSet.getFloat("CostoMedioUnitario")).thenReturn(0.05F);
        when(resultSet.getDate("DataSottoscrizione")).thenReturn(Date.valueOf("2022-01-01"));
        when(resultSet.getInt("Durata")).thenReturn(120);
        when(resultSet.getFloat("PrezzoVendita")).thenReturn(0.03F);
        when(resultSet.getInt("IdAmministratore")).thenReturn(3);

        ContrattoBean result = contrattoDAO.getContrattoAttivo(dataInizio, dataFine);

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).setDate(1, dataFine);
        verify(preparedStatement).setDate(2, dataInizio);
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia non nullo
        assertNotNull(result);

        // Verifica che i valori nel bean siano corretti
        assertEquals(1, result.getIdContratto());
        assertEquals("Enel Energia", result.getNomeEnte());
        assertEquals(2000000F, result.getConsumoMedioAnnuale(), 0.001); // Confronto approssimato per float
        assertEquals(0.05F, result.getCostoMedioUnitario(), 0.001);
        assertEquals(Date.valueOf("2022-01-01"), result.getDataSottoscrizione());
        assertEquals(120, result.getDurata());
        assertEquals(0.03F, result.getPrezzoVendita(), 0.001);
        assertEquals(3, result.getIdAmministatore());
    }

    @Test
    void ottieniPrezzoVendita() throws SQLException{
        // Configura il comportamento del ResultSet simulato per il metodo executeQuery
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simula un solo risultato
        when(resultSet.getFloat("PrezzoVendita")).thenReturn(0.05F);

        float prezzoVendita = contrattoDAO.ottieniPrezzoVendita();

        // Verifica che il PreparedStatement sia stato creato correttamente
        verify(connection).prepareStatement(any(String.class));
        verify(preparedStatement).executeQuery();

        // Verifica che il risultato sia corretto
        assertEquals(0.05F, prezzoVendita, 0.001); // Confronto approssimato per float
    }
}