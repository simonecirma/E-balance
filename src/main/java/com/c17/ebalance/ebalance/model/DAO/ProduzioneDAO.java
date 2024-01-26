package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioProduzioneBean;
import com.c17.ebalance.ebalance.model.entity.SorgenteBean;
import com.c17.ebalance.ebalance.model.entity.TipoSorgenteBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ProduzioneDAO {
    List<ArchivioProduzioneBean> visualizzaProduzione() throws SQLException;
    List<SorgenteBean> visualizzaProduzioneSorgente() throws SQLException;
    float ottieniProduzioneSEN() throws SQLException;
    float ottieniProduzioneProdotta() throws SQLException;

    List<TipoSorgenteBean> ottieniTipoSorgente() throws SQLException;

    void simulaProduzione(int idSorgente, float produzioneSimulata, Date data) throws SQLException;

    int ottieniSorgenti() throws SQLException;

    void simulaProduzioneSEN(float produzioneNecessaria, Date data) throws SQLException;

    float energiaRinnovabileProdottaPerData(final Date dataInizio, final Date dataFine) throws SQLException;
}
