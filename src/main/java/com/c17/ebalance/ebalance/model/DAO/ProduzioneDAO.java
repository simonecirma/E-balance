package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;

import java.sql.SQLException;
import java.util.List;

public interface ProduzioneDAO {
    public List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException;
    public List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException;

    String[][] ottieniProduzione() throws SQLException;

}
