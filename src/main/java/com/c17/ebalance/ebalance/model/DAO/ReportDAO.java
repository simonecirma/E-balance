package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ReportBean;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReportDAO {
    List<ReportBean> visualizzaReport() throws SQLException;

    int ultimoReport() throws SQLException;

    void aggiungiReport(ReportBean report) throws SQLException;
}
