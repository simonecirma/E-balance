package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface MeteoDAO {
    List<MeteoBean> getCondizioniMeteo() throws SQLException;

    void insertPrevisioni(java.sql.Date sqlDate, int orario, float vel, int prob, String condizioneCasuale) throws SQLException;

    List<String> getCondizione() throws SQLException;

    boolean verificaPresenza(Date sqlDate, int orario) throws SQLException;
}
