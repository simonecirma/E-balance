package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AmministratoreDAOImplTest {/*
    private AmministratoreDAO amministratoreDAO;
    @BeforeEach
    void setUp() {
        amministratoreDAO = mock(AmministratoreDAOImpl.class);
    }

    @Test
    void verificaSuperAdmin() throws SQLException {
        Boolean result = amministratoreDAO.verificaSuperAdmin();
        assertNotNull(result);
    }

    @Test
    void login() throws SQLException {
        AmministratoreBean bean = amministratoreDAO.login("m.ercolino1@studenti.unisa.it", "Matteo2024!");
        AmministratoreBean expeted = new AmministratoreBean(1, "Matteo", "Ercolino",
                Date.valueOf("1999-01-01"), "m.ercolino1@studenti.unisa.it", "Matteo2024!", true);
        assertNotNull(bean);
        assertTrue(expeted.getIdAmministratore() == bean.getIdAmministratore()
                && expeted.getNome().equals(bean.getNome())
                && expeted.getCognome().equals(bean.getCognome())
                && expeted.getDataNascita().equals(bean.getDataNascita())
                && expeted.getEmail().equals(bean.getEmail())
                && expeted.getPassword().equals(bean.getPassword())
                && expeted.getFlagTipo() == bean.getFlagTipo());
    }

    @Test
    void visualizzaAmministratori() throws SQLException {
        List<AmministratoreBean> amministratori = amministratoreDAO.visualizzaAmministratori();
        assertNotNull(amministratori);
    }

    @Test
    void aggiornaAmministratore()  throws SQLException {
        AmministratoreBean amministratoreAggiornato = new AmministratoreBean(01, "Carlo", "Ercolino",
                Date.valueOf("1999-01-01"), "c.ercolino1@studenti.unisa.it", "Carlo2024!", true);
        AmministratoreBean bean = amministratoreDAO.aggiornaAmministratore(amministratoreAggiornato);
        assertNotNull(bean);
    }

    @Test
    void aggiungiAmministratore() throws SQLException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataNascita = new Date(dateFormat.parse("1999-01-01").getTime());
        AmministratoreBean amministratoreAggiunto = new AmministratoreBean();
        amministratoreAggiunto.setNome("Luca");
        amministratoreAggiunto.setCognome("Argentero");
        amministratoreAggiunto.setDataNascita(dataNascita);
        amministratoreAggiunto.setEmail("l.argentero1@studenti.unisa.it");
        amministratoreAggiunto.setPassword("Luca2024!");
        amministratoreAggiunto.setFlagTipo(false);
        AmministratoreBean bean = amministratoreDAO.aggiornaAmministratore(amministratoreAggiunto);
        assertNotNull(bean);
    }

    @Test
    void getById()  throws SQLException {
        AmministratoreBean bean = amministratoreDAO.getById(1);
        AmministratoreBean expeted = new AmministratoreBean(01, "Matteo", "Ercolino",
                Date.valueOf("1999-01-01"), "m.ercolino1@studenti.unisa.it", "Matteo2024!", true);
        assertNotNull(bean);
        assertTrue(expeted.getIdAmministratore() == bean.getIdAmministratore()
                && expeted.getNome().equals(bean.getNome())
                && expeted.getCognome().equals(bean.getCognome())
                && expeted.getDataNascita().equals(bean.getDataNascita())
                && expeted.getEmail().equals(bean.getEmail())
                && expeted.getPassword().equals(bean.getPassword())
                && expeted.getFlagTipo() == bean.getFlagTipo());
    }

    @Test
    void rimuoviAmministratore()  throws SQLException {
        amministratoreDAO.rimuoviAmministratore(1);
    }

    @Test
    void verificaPresenzaEmail()  throws SQLException {
        Boolean presenza = amministratoreDAO.verificaPresenzaEmail("m.ecrolino1@studenti.unisa.it");
        assertNotNull(presenza);
    }*/
}