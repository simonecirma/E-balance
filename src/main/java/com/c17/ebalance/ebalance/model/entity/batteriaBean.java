package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class batteriaBean {
    private int idBatteria;
    private boolean flagStatoBatteria;
    private float capacitaMax;
    private int percentualeCarica;

    public batteriaBean() {}

    public batteriaBean(int idBatteria, boolean flagStatoBatteria, float capacitaMax, int percentualeCarica) {
        this.idBatteria = idBatteria;
        this.flagStatoBatteria = flagStatoBatteria;
        this.capacitaMax = capacitaMax;
        this.percentualeCarica = percentualeCarica;
    }

    public int getIdBatteria() {
        return idBatteria;
    }

    public void setIdBatteria(int idBatteria) {
        this.idBatteria = idBatteria;
    }

    public boolean isFlagStatoBatteria() {
        return flagStatoBatteria;
    }

    public void setFlagStatoBatteria(boolean flagStatoBatteria) {
        this.flagStatoBatteria = flagStatoBatteria;
    }

    public float getCapacitaMax() {
        return capacitaMax;
    }

    public void setCapacitaMax(float capacitaMax) {
        this.capacitaMax = capacitaMax;
    }

    public int getPercentualeCarica() {
        return percentualeCarica;
    }

    public void setPercentualeCarica(int percentualeCarica) {
        this.percentualeCarica = percentualeCarica;
    }

    @Override
    public String toString() {
        return "batteriaBean{" +
                "idBatteria=" + idBatteria +
                ", flagStatoBatteria=" + flagStatoBatteria +
                ", capacitaMax=" + capacitaMax +
                ", percentualeCarica=" + percentualeCarica +
                '}';
    }
}
