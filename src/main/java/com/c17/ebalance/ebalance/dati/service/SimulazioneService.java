package com.c17.ebalance.ebalance.dati.service;

import java.sql.SQLException;

public interface SimulazioneService {
    void simulazioneEnergia() throws SQLException;

    void insertPrevisioni() throws SQLException;

    void modificaPrevisioni() throws SQLException;
}
