package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContrattoDAOImplTest {/*
    @Mock
    private DataSource dataSource;
    private ContrattoDAO contrattoDao;

    @BeforeEach
    void setUp() {
        contrattoDao = mock(ContrattoDAOImpl.class); }
    @Test
    void visualizzaContratto() throws SQLException {
        ContrattoBean contrattoBean = new ContrattoBean();
        when(contrattoDao.visualizzaContratto()).thenReturn(contrattoBean);
        ContrattoBean result = contrattoDao.visualizzaContratto();
        assertNotNull(result);
        assertEquals(contrattoBean, result);
    }

    @Test
    void visualizzaStoricoContratti() throws SQLException{
        List<ContrattoBean> listaContratti = new ArrayList<>();
        when(contrattoDao.visualizzaStoricoContratti()).thenReturn(listaContratti);
        List<ContrattoBean> result = contrattoDao.visualizzaStoricoContratti();
        assertNotNull(result);
        assertEquals(listaContratti, result);
    }

    @Test
    void aggiornaContratto() throws SQLException{
        ContrattoBean contrattoAggiornato = new ContrattoBean(1, "Enel Energia",
                2000000, 0.05F, Date.valueOf("2000-01-01"), 120, 0.03F, 3);
        when(contrattoDao.aggiornaContratto(any())).thenReturn(contrattoAggiornato);
        ContrattoBean bean = contrattoDao.aggiornaContratto(contrattoAggiornato);
        assertNotNull(contrattoAggiornato);
        System.out.println(contrattoAggiornato);
        System.out.println(bean);
        assertEquals(contrattoAggiornato, bean);
    }

    @Test
    void aggiungiContratto() throws SQLException{
        ContrattoBean contrattoAggiunto = new ContrattoBean(4, "Enel Energia",
                2000000, 0.05F, Date.valueOf("2000-01-01"), 120, 0.03F, 3);
        when(contrattoDao.aggiungiContratto(any())).thenReturn(contrattoAggiunto);
        ContrattoBean bean = contrattoDao.aggiungiContratto(contrattoAggiunto);
        assertNotNull(contrattoAggiunto);
        System.out.println(contrattoAggiunto);
        System.out.println(bean);
        assertEquals(contrattoAggiunto, bean);
    }

    @Test
    void verificaPrimoContratto() throws SQLException{
        boolean res = false;
        when(contrattoDao.verificaPrimoContratto()).thenReturn(res);
        boolean result = contrattoDao.verificaPrimoContratto();
        assertEquals(res, result);
    }

    @Test
    void getContrattoAttivo() throws SQLException{
        ContrattoBean contrattoBean = new ContrattoBean();
        when(contrattoDao.getContrattoAttivo(any(), any())).thenReturn(contrattoBean);
        ContrattoBean result = contrattoDao.getContrattoAttivo(any(), any());
        assertNotNull(result);
        System.out.println(result);
        assertEquals(contrattoBean, result);
    }

    @Test
    void ottieniPrezzoVendita() throws SQLException{
        float prezzoVendita = 0;
        when(contrattoDao.ottieniPrezzoVendita()).thenReturn(prezzoVendita);
        float result = contrattoDao.ottieniPrezzoVendita();
        assertEquals(prezzoVendita, result);
    }*/
}