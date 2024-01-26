package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAO;
import com.c17.ebalance.ebalance.model.DAO.ProduzioneDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

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
    public float[] ottieniProduzioneProdotta(float[] produzioneSorgente) throws SQLException {
        int numElementi = produzioneSorgente.length;
        for (int i = 1; i < numElementi; i++) {
            produzioneSorgente[i - 1] = produzioneSorgente[i];
        }
        produzioneSorgente[numElementi-1] = produzioneDAO.ottieniProduzioneProdotta();
        return produzioneSorgente;
    }
    @Override
    public float ottieniProduzioneSEN() throws SQLException {
        return produzioneDAO.ottieniProduzioneSEN();
    }

    @Override
    public List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException {
        return produzioneDAO.ottieniTipoSorgente();
    }

    @Override
    public float energiaRinnovabileProdottaPerData(
            final Date dataInizio, final Date dataFine)
            throws SQLException {
        return produzioneDAO.energiaRinnovabileProdottaPerData(
                dataInizio, dataFine);
    }

}
