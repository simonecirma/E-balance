package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.reportBean;

import java.sql.SQLException;
import java.util.List;

public interface reportService {
    List<reportBean> visualizzaReport() throws SQLException;
}
