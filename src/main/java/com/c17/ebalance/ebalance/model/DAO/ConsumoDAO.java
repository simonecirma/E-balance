package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ConsumoEdificioBean;

import java.sql.SQLException;
import java.util.List;

public interface ConsumoDAO {
    public List<ConsumoEdificioBean> visualizzaConsumo() throws SQLException;
}