package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.BatteriaBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce i metodi per l'accesso ai dati delle batterie nel sistema eBalance.
 * Fornisce operazioni per visualizzare le batterie, ottenere informazioni sulle batterie attive,
 * ottenere la percentuale di batterie e aggiornare lo stato delle batterie.
 */
public interface BatteriaDAO {

    /**
     * Restituisce una lista di tutte le batterie presenti nel sistema.
     *
     * @return Una lista di oggetti BatteriaBean.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    List<BatteriaBean> visualizzaBatteria() throws SQLException;

    /**
     * Restituisce il numero di batterie attive nel sistema.
     *
     * @return Il numero di batterie attive.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    int ottieniNumBatterieAttive() throws SQLException;


    /**
     * Restituisce la percentuale complessiva di batterie nel sistema.
     *
     * @return La percentuale complessiva di batterie.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    float ottieniPercentualeBatterie() throws SQLException;

    /**
     * Aggiorna lo stato delle batterie nel sistema sulla base dell'energia fornita e del numero di batterie.
     *
     * @param energia      L'energia da aggiungere o sottrarre alle batterie (positiva per aggiungere, negativa per sottrarre).
     * @param numBatterie  Il numero di batterie su cui applicare l'aggiornamento.
     * @return La percentuale di energia in eccesso o in deficit rispetto alle batterie nel sistema.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    float aggiornaBatteria(float energia, int numBatterie) throws SQLException;
}
