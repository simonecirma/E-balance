package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.DAO.AmministratoreDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AmministratoreDAOImplTest {



    @Test
    void verificaSuperAdmin() throws SQLException {
        Boolean result = AmministratoreDAO.verificaSuperAdmin();
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