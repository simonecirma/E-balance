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
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class VenditaServiceImpl implements VenditaService {
    private VenditaDAO venditaDAO = new VenditaDAOImpl();
    private ContrattoService contrattoService = new ContrattoServiceImpl();
    private BatteriaService batteriaService = new BatteriaServiceImpl();

    @Override
    public List<VenditaBean> getVendite(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getVendite(dataInizio, dataFine);
    }

    @Override
    public float getRicavoTotalePerData(Date dataInizio, Date dataFine) throws SQLException {
        return venditaDAO.getRicavoTotalePerData(dataInizio, dataFine);
    }

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
