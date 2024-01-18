package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;

import java.sql.SQLException;
import java.util.List;

public interface ProduzioneService {
    public List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException;

    public List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException;

    float[] ottieniProduzione() throws SQLException;

}
