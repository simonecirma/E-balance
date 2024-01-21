package com.c17.ebalance.ebalance.dati.service;
import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.SQLException;
import java.util.List;

public interface ConsumoService {
    public List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException;

    float ottieniConsumiEdifici() throws SQLException;

    List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException;
}
