package com.c17.ebalance.ebalance.contratto.service;

import com.c17.ebalance.ebalance.model.DAO.ContrattoDAO;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import com.c17.ebalance.ebalance.model.DAO.ContrattoDAOImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class ContrattoServiceImpl implements ContrattoService {
    private ContrattoDAO contrattoDao = new ContrattoDAOImpl();

    @Override
    public ContrattoBean visualizzaContratto() throws SQLException {
        return contrattoDao.visualizzaContratto();
    }

    @Override
    public List<ContrattoBean> visualizzaStoricoContratti() throws SQLException {
        return contrattoDao.visualizzaStoricoContratti();
    }

    @Override
    public ContrattoBean aggiornaContratto(final ContrattoBean contratto) throws SQLException {
        return contrattoDao.aggiornaContratto(contratto);
    }

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

    public boolean verificaPrimoContratto() throws SQLException {
        return contrattoDao.verificaPrimoContratto();
    }

    public ContrattoBean getContrattoAttivo(
            final Date dataInizio, final Date dataFine) throws SQLException {
        return contrattoDao.getContrattoAttivo(dataInizio, dataFine);
    }

    @Override
    public float ottieniPrezzoVendita() throws SQLException {
        return contrattoDao.ottieniPrezzoVendita();
    }

}
