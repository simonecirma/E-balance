package com.c17.ebalance.ebalance.model.DAO;


import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AmministratoreDAOImplTest {

    @Test
    void verificaSuperAdmin() throws SQLException {
        AmministratoreDAOImpl amministratoreDAO = mock(AmministratoreDAOImpl.class);
        Boolean result = amministratoreDAO.verificaSuperAdmin();
        assertNotNull(result);
    }

    @Test
    void login() {
    }

    @Test
    void visualizzaAmministratori() {
    }

    @Test
    void aggiornaAmministratore() {
    }

    @Test
    void aggiungiAmministratore() {
    }

    @Test
    void getById() {
    }

    @Test
    void rimuoviAmministratore() {
    }

    @Test
    void verificaPresenzaEmail() {
    }
}