package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.model.DAO.MeteoDAO;
import com.c17.ebalance.ebalance.model.entity.MeteoBean;

import java.sql.SQLException;
import java.util.List;

public interface MeteoService {
    List<MeteoBean> getCondizioniMeteo()throws SQLException;
    List<MeteoBean> getCondizioniSettimanali()throws SQLException;
}
