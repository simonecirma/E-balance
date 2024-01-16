package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import com.c17.ebalance.ebalance.model.DAO.ContrattoDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class ContrattoServiceImpl implements ContrattoService {
    private ContrattoDAO contrattoDao = new ContrattoDAO();
    @Override
    public ContrattoBean visualizzaContratto() throws SQLException {
        return ContrattoDAO.visualizzaContratto();
    }

    @Override
    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException {
        return contrattoDao.visualizzaStoricoContratti();
    }

    @Override
    public void aggiornaContratto(final ContrattoBean contratto) throws SQLException {
        contrattoDao.aggiornaContratto(contratto);
    }

    @Override
    public void aggiungiContratto(final ContrattoBean contrattoNuovo) throws SQLException {
        ContrattoBean contrattoAttuale = ContrattoDAO.visualizzaContratto();

        if (contrattoAttuale.getIdContratto() > 0) {

            // Converte le date in oggetti Calendar
            Calendar calDataDiRiferimento = Calendar.getInstance();
            calDataDiRiferimento.setTime(contrattoAttuale.getDataSottoscrizione());

            Calendar calDataAttuale = Calendar.getInstance();
            calDataAttuale.setTime(contrattoNuovo.getDataSottoscrizione());

            // Calcola il periodo tra le due date
            int anniDifferenza = calDataAttuale.get(Calendar.YEAR) - calDataDiRiferimento.get(Calendar.YEAR);
            int mesiDifferenza = calDataAttuale.get(Calendar.MONTH) - calDataDiRiferimento.get(Calendar.MONTH);

            // Calcola il numero totale di mesi passati
            int mesiPassati = anniDifferenza * 12 + mesiDifferenza;

            contrattoAttuale.setDurata(mesiPassati);
            contrattoDao.aggiornaContratto(contrattoAttuale);
        }

        contrattoDao.aggiungiContratto(contrattoNuovo);
    }

    public boolean verificaPrimoContratto() throws SQLException {
        return contrattoDao.verificaPrimoContratto();
    }
}
