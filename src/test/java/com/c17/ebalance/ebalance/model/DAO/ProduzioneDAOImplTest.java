package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProduzioneDAOImplTest {

    @Mock
    private DataSource dataSource;
    private ProduzioneDAO produzioneDAO;

    @BeforeEach
    void setUp() {
        produzioneDAO = mock(ProduzioneDAOImpl.class);
    }
    @Test
    void visualizzaProduzione() throws SQLException {
        List<ArchivioProduzioneBean> listaProduzione = new ArrayList<>();
        when(produzioneDAO.visualizzaProduzione()).thenReturn(listaProduzione);
        List<ArchivioProduzioneBean> result = produzioneDAO.visualizzaProduzione();
        assertNotNull(result);
        assertEquals(listaProduzione, result);
    }

    @Test
    void visualizzaProduzioneSorgente() throws SQLException {
        List<SorgenteBean> listaProduzioneSorgente = new ArrayList<>();
        when(produzioneDAO.visualizzaProduzioneSorgente()).thenReturn(listaProduzioneSorgente);
        List<SorgenteBean> result = produzioneDAO.visualizzaProduzioneSorgente();
        assertNotNull(result);
        assertEquals(listaProduzioneSorgente, result);
    }


    @Test
    void ottieniProduzioneProdotta() throws SQLException {
        float produzioneAttuale = 123.45F;
        when(produzioneDAO.ottieniProduzioneProdotta()).thenReturn(produzioneAttuale);
        float result = produzioneDAO.ottieniProduzioneProdotta();
        assertEquals(produzioneAttuale, result, 0.001);
    }


    @Test
    void ottieniProduzioneSEN() throws SQLException {
        float produzioneSENMock = 45.67F;
        when(produzioneDAO.ottieniProduzioneSEN()).thenReturn(produzioneSENMock);
        float result = produzioneDAO.ottieniProduzioneSEN();
        assertEquals(produzioneSENMock, result, 0.001);
    }


    @Test
    void ottieniTipoSorgente() throws SQLException {
        List<TipoSorgenteBean> tipoSorgente = new ArrayList<>();
        TipoSorgenteBean tipoSorgenteBean = new TipoSorgenteBean();
        tipoSorgenteBean.setTipo("Pannello Fotovoltaico");
        tipoSorgente.add(tipoSorgenteBean);
        when(produzioneDAO.ottieniTipoSorgente()).thenReturn(tipoSorgente);
        List<TipoSorgenteBean> result = produzioneDAO.ottieniTipoSorgente();
        assertEquals(tipoSorgente, result);
    }

    @Test
    void simulaProduzione() throws SQLException {
        int idSorgente = 1;
        float produzioneSimulata = 50.0F;
        Date data = new Date(System.currentTimeMillis());
        produzioneDAO.simulaProduzione(idSorgente, produzioneSimulata, data);
        verify(produzioneDAO, times(1)).simulaProduzione(anyInt(), anyFloat(), any(Date.class));
    }

    @Test
    void ottieniSorgenti() throws SQLException {
        int numSorgente = 5;
        when(produzioneDAO.ottieniSorgenti()).thenReturn(numSorgente);
        int result = produzioneDAO.ottieniSorgenti();
        assertEquals(numSorgente, result);
    }

    @Test
    void simulaProduzioneSEN() throws SQLException {
        float produzioneNecessaria = 50.0F;
        Date data = new Date(System.currentTimeMillis());
        produzioneDAO.simulaProduzioneSEN(produzioneNecessaria, data);
        verify(produzioneDAO, times(1)).simulaProduzioneSEN(anyFloat(), any(Date.class));
    }

    @Test
    void energiaRinnovabileProdottaPerData() throws SQLException {
        float energiaTotale = 100.0F;
        when(produzioneDAO.energiaRinnovabileProdottaPerData(any(), any())).thenReturn(energiaTotale);
        Date dataInizio = new Date(System.currentTimeMillis() - 100000);
        Date dataFine = new Date(System.currentTimeMillis());
        float result = produzioneDAO.energiaRinnovabileProdottaPerData(dataInizio, dataFine);
        assertEquals(energiaTotale, result, 0.001);
    }
}
