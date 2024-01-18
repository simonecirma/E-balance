package com.c17.ebalance.ebalance.IA.service;

import com.c17.ebalance.ebalance.model.DAO.ParametriIADAO;
import com.c17.ebalance.ebalance.model.DAO.ParametriIADAOImpl;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;

import java.sql.SQLException;
import java.util.List;

public class IAServiceImpl implements IAService{
    private ParametriIADAO parametriIA = new ParametriIADAOImpl();
    @Override
    public List<ParametriIABean> visualizzaParametri() throws SQLException {
        return parametriIA.visualizzaParametri();
    }

    @Override
    public List<InteragisceBean> visualizzaInterazioneParametri() throws SQLException {
        return parametriIA.visualizzaInterazioneParametri();
    }
}
