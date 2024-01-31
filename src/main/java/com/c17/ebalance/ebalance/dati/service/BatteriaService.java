package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

public interface BatteriaService {
    List<BatteriaBean> visualizzaBatteria() throws SQLException;

    float ottieniPercetualeBatteria() throws SQLException;

    int ottieniNumBatterieAttive() throws SQLException;

    void aggiornaBatteria(float energia, int numBatteria) throws SQLException;
}
