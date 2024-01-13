package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class consumoEdificioBean {
    private int idEdificio;
    private String nomeEdificio;
    private float consumoAttuale;

    public consumoEdificioBean() {}

    public consumoEdificioBean(int idEdificio, String nomeEdificio, float consumoAttuale) {
        this.idEdificio = idEdificio;
        this.nomeEdificio = nomeEdificio;
        this.consumoAttuale = consumoAttuale;
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getNomeEdificio() {
        return nomeEdificio;
    }

    public void setNomeEdificio(String nomeEdificio) {
        this.nomeEdificio = nomeEdificio;
    }

    public float getConsumoAttuale() {
        return consumoAttuale;
    }

    public void setConsumoAttuale(float consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
    }

    @Override
    public String toString() {
        return "consumoEdificioBean{" +
                "idEdificio=" + idEdificio +
                ", nomeEdificio='" + nomeEdificio + '\'' +
                ", consumoAttuale=" + consumoAttuale +
                '}';
    }
}
