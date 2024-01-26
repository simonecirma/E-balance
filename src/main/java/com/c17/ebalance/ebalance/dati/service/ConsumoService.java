package com.c17.ebalance.ebalance.dati.service;
import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ConsumoService {
    List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException;

    public float[] ottieniConsumiEdifici(float consumoEdifici[]) throws SQLException;

    List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException;


    float getConsumoPerData(Date dataInizio, Date dataFine) throws SQLException;
}
