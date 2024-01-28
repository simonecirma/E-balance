package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.BatteriaDAO;
import com.c17.ebalance.ebalance.model.DAO.BatteriaDAOImpl;
import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

public class BatteriaServiceImpl implements BatteriaService {
    private BatteriaDAO batteriaDAO = new BatteriaDAOImpl();

    @Override
    public List<BatteriaBean> visualizzaBatteria() throws SQLException {
        return batteriaDAO.visualizzaBatteria();
    }

    @Override
    public float ottieniPercetualeBatteria() throws SQLException {
        return batteriaDAO.ottieniPercentualeBatterie();
    }

    @Override
    public int ottieniNumBatterieAttive() throws SQLException {
        return batteriaDAO.ottieniNumBatterieAttive();
    }

    @Override
    public void aggiornaBatteria(float energia, int numBatteria) throws SQLException {
        batteriaDAO.aggiornaBatteria(energia, numBatteria);
    }
}
