package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class interagisceBean {
    private int idParametro;
    private String tipoSorgente;
    private boolean flagPreferenzaSorgente;
    private int percentualeUtilizzoSorgente;
    private int prioritaSorgente;

    public interagisceBean() {}

    public interagisceBean(int idParametro, String tipoSorgente, boolean flagPreferenzaSorgente,
                           int percentualeUtilizzoSorgente, int prioritaSorgente) {
        this.idParametro = idParametro;
        this.tipoSorgente = tipoSorgente;
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
        this.prioritaSorgente = prioritaSorgente;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public String getTipoSorgente() {
        return tipoSorgente;
    }

    public void setTipoSorgente(String tipoSorgente) {
        this.tipoSorgente = tipoSorgente;
    }

    public boolean isFlagPreferenzaSorgente() {
        return flagPreferenzaSorgente;
    }

    public void setFlagPreferenzaSorgente(boolean flagPreferenzaSorgente) {
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
    }

    public int getPercentualeUtilizzoSorgente() {
        return percentualeUtilizzoSorgente;
    }

    public void setPercentualeUtilizzoSorgente(int percentualeUtilizzoSorgente) {
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
    }

    public int getPrioritaSorgente() {
        return prioritaSorgente;
    }

    public void setPrioritaSorgente(int prioritaSorgente) {
        this.prioritaSorgente = prioritaSorgente;
    }

    @Override
    public String toString() {
        return "interagisceBean{" +
                "idParametro=" + idParametro +
                ", tipoSorgente='" + tipoSorgente + '\'' +
                ", flagPreferenzaSorgente=" + flagPreferenzaSorgente +
                ", percentualeUtilizzoSorgente=" + percentualeUtilizzoSorgente +
                ", prioritaSorgente=" + prioritaSorgente +
                '}';
    }
}
