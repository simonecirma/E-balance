package com.c17.ebalance.ebalance.model.DAO;


import com.c17.ebalance.ebalance.model.entity.BatteriaBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class BatteriaDAOImpl implements BatteriaDAO {

    private static Logger logger = Logger.getLogger(BatteriaDAOImpl.class.getName());
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/ebalance");

        } catch (NamingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    private static final String TABLE_NAME_BATTERIA = "Batteria";

    public List<BatteriaBean> visualizzaBatteria() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BatteriaBean> batteria = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME_BATTERIA;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BatteriaBean bean = new BatteriaBean();
                bean.setFlagStatoBatteria(resultSet.getBoolean("FlagStatoBatteria"));
                bean.setCapacitaMax(resultSet.getFloat("CapacitaMax"));
                bean.setPercentualeCarica(resultSet.getInt("PercentualeCarica"));
                batteria.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return batteria;
    }

}
