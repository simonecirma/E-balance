package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

public interface BatteriaDAO {
    List<BatteriaBean> visualizzaBatteria() throws SQLException;

    int ottieniNumBatterieAttive() throws SQLException;

    float ottieniPercentualeBatterie() throws SQLException;

    float aggiornaBatteria(float energia, int numBatterie) throws SQLException;
}
