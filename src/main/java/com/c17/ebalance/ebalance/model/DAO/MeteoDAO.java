package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.SQLException;
import java.util.List;

public interface MeteoDAO {
    List<MeteoBean> getCondizioniMeteo() throws SQLException;
    void insertPrevisioni() throws SQLException;
}
