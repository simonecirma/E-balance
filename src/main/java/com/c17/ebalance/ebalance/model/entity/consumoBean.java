package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class consumoBean {
    private int idConsumo;
    private Date dataConsumo;
    private float consumoGiornaliero;
    private int idEdificio;

    public consumoBean() {}

    public consumoBean(int idConsumo, Date dataConsumo, float consumoGiornaliero, int idEdificio) {
        this.idConsumo = idConsumo;
        this.dataConsumo = dataConsumo;
        this.consumoGiornaliero = consumoGiornaliero;
        this.idEdificio = idEdificio;
    }

    public int getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(int idConsumo) {
        this.idConsumo = idConsumo;
    }

    public Date getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(Date dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    public float getConsumoGiornaliero() {
        return consumoGiornaliero;
    }

    public void setConsumoGiornaliero(float consumoGiornaliero) {
        this.consumoGiornaliero = consumoGiornaliero;
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }

    @Override
    public String toString() {
        return "consumoBean{" +
                "idConsumo=" + idConsumo +
                ", dataConsumo=" + dataConsumo +
                ", consumoGiornaliero=" + consumoGiornaliero +
                ", idEdificio=" + idEdificio +
                '}';
    }
}
