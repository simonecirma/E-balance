package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.contratto.service.ContrattoServiceImpl;
import com.c17.ebalance.ebalance.model.DAO.VenditaDAO;
import com.c17.ebalance.ebalance.model.DAO.VenditaDAOImpl;
import com.c17.ebalance.ebalance.model.entity.VenditaBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class VenditaServiceImpl implements VenditaService {
    private VenditaDAO venditaDAO = new VenditaDAOImpl();
    private ContrattoService contrattoService = new ContrattoServiceImpl();

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
        Date data = (Date) Calendar.getInstance().getTime();
        float energiaVenduta = random.nextFloat() * 1000 + 1000;
        float prezzoVendita = contrattoService.ottieniPrezzoVendita();
        bean.setEnergiaVenduta(energiaVenduta);
        bean.setRicavoTotale(energiaVenduta*prezzoVendita);
        bean.setDataVendita(data);
        bean.setIdAmministratore(idAmministratore);
        venditaDAO.effetuaVendita(bean);
    }
}
