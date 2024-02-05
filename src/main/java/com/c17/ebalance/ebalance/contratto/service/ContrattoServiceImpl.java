package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.DAO.ContrattoDAO;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import com.c17.ebalance.ebalance.model.DAO.ContrattoDAOImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * La classe {@code ContrattoServiceImpl} implementa l'interfaccia {@code ContrattoService}
 * e fornisce i metodi per la gestione dei contratti nel sistema eBalance.
 * Si interfaccia con il {@code ContrattoDAO} per l'accesso ai dati.
 */
public class ContrattoServiceImpl implements ContrattoService {
    private ContrattoDAO contrattoDao = new ContrattoDAOImpl();

    /**
     * Restituisce il contratto attivo nel sistema.
     *
     * @return Un oggetto {@code ContrattoBean} rappresentante il contratto attivo.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public ContrattoBean visualizzaContratto() throws SQLException {
        return contrattoDao.visualizzaContratto();
    }

    /**
     * Restituisce l'elenco storico di tutti i contratti nel sistema.
     *
     * @return Una lista di oggetti {@code ContrattoBean} rappresentanti gli storici contratti.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException {
        return contrattoDao.visualizzaStoricoContratti();
    }

    /**
     * Aggiorna le informazioni del contratto nel sistema.
     *
     * @param contratto L'oggetto {@code ContrattoBean} rappresentante il contratto con le informazioni aggiornate.
     * @return Un oggetto {@code ContrattoBean} con le informazioni aggiornate.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException {
        return contrattoDao.aggiornaContratto(contratto);
    }

    /**
     * Aggiunge un nuovo contratto al sistema.
     *
     * @param contrattoNuovo L'oggetto {@code ContrattoBean} rappresentante il nuovo contratto da aggiungere.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public void aggiungiContratto(final ContrattoBean contrattoNuovo) throws SQLException {
        ContrattoBean contrattoAttuale = contrattoDao.visualizzaContratto();

        if (contrattoAttuale.getIdContratto() > 0) {

            Calendar calDataDiRiferimento = Calendar.getInstance();
            calDataDiRiferimento.setTime(contrattoAttuale.getDataSottoscrizione());

            Calendar calDataAttuale = Calendar.getInstance();
            calDataAttuale.setTime(contrattoNuovo.getDataSottoscrizione());

            int anniDifferenza = calDataAttuale.get(Calendar.YEAR) - calDataDiRiferimento.get(Calendar.YEAR);
            int mesiDifferenza = calDataAttuale.get(Calendar.MONTH) - calDataDiRiferimento.get(Calendar.MONTH);

            int mesiPassati = anniDifferenza * 12 + mesiDifferenza;

            contrattoAttuale.setDurata(mesiPassati);
            contrattoDao.aggiornaContratto(contrattoAttuale);
        }

        contrattoDao.aggiungiContratto(contrattoNuovo);
    }

    /**
     * Verifica se è presente almeno un contratto nel sistema.
     *
     * @return True se è presente almeno un contratto, altrimenti False.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public boolean verificaPrimoContratto() throws SQLException {
        return contrattoDao.verificaPrimoContratto();
    }

    /**
     * Restituisce il contratto attivo nel sistema per il periodo specificato.
     *
     * @param dataInizio La data di inizio del periodo.
     * @param dataFine   La data di fine del periodo.
     * @return Un oggetto {@code ContrattoBean} rappresentante il contratto attivo nel periodo specificato.
     * @throws SQLException in caso di errori di accesso al database.
     */
    public ContrattoBean getContrattoAttivo(
            final Date dataInizio, final Date dataFine) throws SQLException {
        return contrattoDao.getContrattoAttivo(dataInizio, dataFine);
    }

    /**
     * Ottiene il prezzo di vendita dal contratto attivo nel sistema.
     *
     * @return Il prezzo di vendita del contratto attivo.
     * @throws SQLException in caso di errori di accesso al database.
     */
    @Override
    public float ottieniPrezzoVendita() throws SQLException {
        return contrattoDao.ottieniPrezzoVendita();
    }

}
