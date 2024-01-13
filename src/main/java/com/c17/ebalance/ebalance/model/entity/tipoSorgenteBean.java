package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class tipoSorgenteBean {
    private String tipo;

    public tipoSorgenteBean() {}

    public tipoSorgenteBean(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "tipoSorgenteBean{" +
                "tipo='" + tipo + '\'' +
                '}';
    }
}
