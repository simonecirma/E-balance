package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.BatteriaDAO;
import com.c17.ebalance.ebalance.model.DAO.BatteriaDAOImpl;

import java.sql.SQLException;

/**
 * L'implementazione di {@code BatteriaService} che gestisce le operazioni sullo stato delle batterie nel sistema eBalance.
 * Utilizza un'istanza di {@code BatteriaDAO} per accedere al database e interagire con i dati delle batterie.
 */
public class BatteriaServiceImpl implements BatteriaService {
    private BatteriaDAO batteriaDAO = new BatteriaDAOImpl();

    /**
     * Ottiene la percentuale di carica della batteria nel sistema.
     *
     * @return La percentuale di carica della batteria.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public float ottieniPercetualeBatteria() throws SQLException {
        return batteriaDAO.ottieniPercentualeBatterie();
    }

    /**
     * Ottiene il numero di batterie attualmente attive nel sistema.
     *
     * @return Il numero di batterie attive.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public int ottieniNumBatterieAttive() throws SQLException {
        return batteriaDAO.ottieniNumBatterieAttive();
    }

    /**
     * Aggiorna lo stato della batteria con l'energia fornita per un determinato numero di batterie.
     *
     * @param energia     L'energia da aggiungere o sottrarre alla batteria.
     * @param numBatteria Il numero di batterie da aggiornare.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public void aggiornaBatteria(float energia, int numBatteria) throws SQLException {
        batteriaDAO.aggiornaBatteria(energia, numBatteria);
    }
}
