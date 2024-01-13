package com.c17.ebalance.ebalance.accesso.service;

import com.c17.ebalance.ebalance.model.entity.*;

import java.sql.SQLException;

public interface accessoService {

    amministratoreBean login(String email, String password) throws SQLException;

}
