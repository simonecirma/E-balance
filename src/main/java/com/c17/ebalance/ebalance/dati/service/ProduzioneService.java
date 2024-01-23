package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.SQLException;
import java.util.List;

public interface ProduzioneService {
    public List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException;

    public List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException;

    String[][] ottieniProduzione() throws SQLException;

    List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException;

    void simulaProduzione() throws SQLException;
    void simulaProduzioneSEN() throws SQLException;
}
