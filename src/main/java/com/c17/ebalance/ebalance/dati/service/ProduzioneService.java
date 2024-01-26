package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ProduzioneService {
    List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException;

    List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException;

    float[] ottieniProduzioneProdotta(float[] produzioneSorgente) throws SQLException;

    float ottieniProduzioneSEN() throws SQLException;

    List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException;

    float energiaRinnovabileProdottaPerData(final Date dataInizio, final Date dataFine) throws SQLException;

}
