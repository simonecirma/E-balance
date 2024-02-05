package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.contratto.service.ContrattoServiceImpl;
import com.c17.ebalance.ebalance.dati.service.BatteriaService;
import com.c17.ebalance.ebalance.dati.service.BatteriaServiceImpl;
import com.c17.ebalance.ebalance.model.DAO.VenditaDAO;
import com.c17.ebalance.ebalance.model.DAO.VenditaDAOImpl;
import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * La classe {@code VenditaServiceImpl} implementa l'interfaccia {@code VenditaService} e fornisce
 * l'implementazione concreta per le operazioni di gestione delle vendite nel sistema eBalance.
 * Utilizza un'istanza di {@code VenditaDAO}, {@code ContrattoService}, e {@code BatteriaService}
 * per accedere al database e eseguire le operazioni specifiche.
 */
public class VenditaServiceImpl implements VenditaService {
    private VenditaDAO venditaDAO = new VenditaDAOImpl();
    private ContrattoService contrattoService = new ContrattoServiceImpl();
    private BatteriaService batteriaService = new BatteriaServiceImpl();

    /**
     * Restituisce una lista di tutte le vendite nel periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo per le vendite.
     * @param dataFine   La data di fine del periodo per le vendite.
     * @return Una lista di oggetti {@code VenditaBean}.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public List<VenditaBean> getVendite(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getVendite(dataInizio, dataFine);
    }

    /**
     * Restituisce il ricavo totale delle vendite nel periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo per le vendite.
     * @param dataFine   La data di fine del periodo per le vendite.
     * @return Il ricavo totale delle vendite nel periodo.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getRicavoTotalePerData(dataInizio, dataFine);
    }

    /**
     * Effettua una vendita generando un oggetto {@code VenditaBean} con dati casuali
     * e aggiornando il database di vendite e le informazioni sulle batterie.
     *
     * @param idAmministratore L'ID dell'amministratore che effettua la vendita.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public void effettuaVendita(int idAmministratore) throws SQLException {
        VenditaBean bean = new VenditaBean();
        Random random = new Random();
        LocalDate data = LocalDate.now();
        float energiaVenduta = random.nextFloat() * 1000 + 1000;
        float prezzoVendita = contrattoService.ottieniPrezzoVendita();
        bean.setEnergiaVenduta(energiaVenduta);
        bean.setRicavoTotale(energiaVenduta * prezzoVendita);
        bean.setDataVendita(Date.valueOf(data));
        bean.setIdAmministratore(idAmministratore);
        venditaDAO.effetuaVendita(bean);
        int numBatterie = batteriaService.ottieniNumBatterieAttive();
        batteriaService.aggiornaBatteria(-energiaVenduta, numBatterie);
    }
}
