package com.c17.ebalance.ebalance.dati.service;

import java.sql.SQLException;

public interface SimulazioneService {
    void simulazione() throws SQLException;

    void insertPrevisioniIniziali() throws SQLException;
}
