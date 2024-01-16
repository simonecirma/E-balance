package com.c17.ebalance.ebalance.model.DAO;

import com.c17.ebalance.ebalance.model.entity.ReportBean;

import java.sql.SQLException;
import java.util.List;

public interface ReportDAO {
    public List<ReportBean> visualizzaReport() throws SQLException;

}
