package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

public interface BatteriaDAO {
    List<BatteriaBean> visualizzaBatteria() throws SQLException;
    int ottieniNumBatterieAttive() throws SQLException;

    float ottieniPercentualeBatterie() throws SQLException;

    void aggiornaConsumiBatteria(float consumoOrario, int idEdificio) throws SQLException;

    void aggiornaProduzioneBatteria(float produzioneOraria, int idSorgente) throws SQLException;
}
