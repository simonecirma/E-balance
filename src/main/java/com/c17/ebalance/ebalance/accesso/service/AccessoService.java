package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;

import java.sql.SQLException;

public interface AccessoService {

    AmministratoreBean login(String email, String password) throws SQLException;

}
