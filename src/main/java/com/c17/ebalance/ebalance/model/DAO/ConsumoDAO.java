package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ArchivioConsumoBean;
import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ConsumoDAO {
    List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException;

    float ottieniConsumiEdifici() throws SQLException;

    List<ArchivioConsumoBean> visualizzaStoricoConsumi() throws SQLException;

    void simulaConsumo(float consumoOrario, int IdEdificio, Date data) throws SQLException;

    int ottieniNumEdifici() throws SQLException;

    float getConsumoPerData(final Date dataInizio, final Date dataFine) throws SQLException;

}
