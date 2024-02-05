package com.c17.ebalance.ebalance.dati.service;

import java.sql.SQLException;

/**
 * L'interfaccia {@code BatteriaService} definisce il contratto per la gestione delle batterie nel sistema eBalance.
 * Fornisce metodi per ottenere informazioni sulla percentuale di carica della batteria, il numero di batterie attive,
 * e per aggiornare lo stato della batteria.
 */
public interface BatteriaService {

    /**
     * Ottiene la percentuale di carica della batteria nel sistema.
     *
     * @return La percentuale di carica della batteria.
     * @throws SQLException in caso di errori di accesso al database.
     */
    float ottieniPercetualeBatteria() throws SQLException;

    /**
     * Ottiene il numero di batterie attualmente attive nel sistema.
     *
     * @return Il numero di batterie attive.
     * @throws SQLException in caso di errori di accesso al database.
     */
    int ottieniNumBatterieAttive() throws SQLException;

    /**
     * Aggiorna lo stato della batteria con l'energia fornita per un determinato numero di batterie.
     *
     * @param energia     L'energia da aggiungere o sottrarre alla batteria.
     * @param numBatteria Il numero di batterie da aggiornare.
     * @throws SQLException in caso di errori di accesso al database.
     */
    void aggiornaBatteria(float energia, int numBatteria) throws SQLException;
}
