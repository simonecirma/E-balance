package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ConsumoDAO;
import com.c17.ebalance.ebalance.model.DAO.ConsumoDAOImpl;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAO;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ProduzioneServiceImpl implements ProduzioneService {

    private ProduzioneDAO produzioneDAO = new ProduzioneDAOImpl();

    @Override
    public List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException {
        return produzioneDAO.visualizzaProduzione();
    }

    @Override
    public List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException {
        return produzioneDAO.visualizzaProduzioneSorgente();
    }

    @Override
    public String[][] ottieniProduzione() throws SQLException {
        return produzioneDAO.ottieniProduzione();
    }

    @Override
    public List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException {
        return produzioneDAO.ottieniTipoSorgente();
    }


}
