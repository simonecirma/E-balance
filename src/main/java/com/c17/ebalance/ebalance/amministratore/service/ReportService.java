package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.model.entity.ReportBean;

import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    List<ReportBean> visualizzaReport() throws SQLException;
}
