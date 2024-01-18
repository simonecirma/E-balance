package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

public interface BatteriaService {
    public List<BatteriaBean> visualizzaBatteria() throws SQLException;

    float ottieniPercetualeBatteria() throws SQLException;
}
