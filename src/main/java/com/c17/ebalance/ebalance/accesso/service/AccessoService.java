package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.entity.amministratoreBean;

import java.sql.SQLException;

public interface AccessoService {

    amministratoreBean login(String email, String password) throws SQLException;

}
