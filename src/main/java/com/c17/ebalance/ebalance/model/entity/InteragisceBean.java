package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class InteragisceBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idParametro;
    private String tipoSorgente;
    private boolean flagPreferenzaSorgente;
    private int percentualeUtilizzoSorgente;
    private int prioritaSorgente;

    public InteragisceBean() { }

    public InteragisceBean(final int idParametro, final String tipoSorgente,
                           final boolean flagPreferenzaSorgente,
                           final int percentualeUtilizzoSorgente, final int prioritaSorgente) {
        this.idParametro = idParametro;
        this.tipoSorgente = tipoSorgente;
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
        this.prioritaSorgente = prioritaSorgente;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(final int idParametro) {
        this.idParametro = idParametro;
        notifyObservers("setIdParametro");
    }

    public String getTipoSorgente() {
        return tipoSorgente;
    }

    public void setTipoSorgente(final String tipoSorgente) {
        this.tipoSorgente = tipoSorgente;
        notifyObservers("setTipoSorgente");
    }

    public boolean getFlagPreferenzaSorgente() {
        return flagPreferenzaSorgente;
    }

    public void setFlagPreferenzaSorgente(final boolean flagPreferenzaSorgente) {
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
        notifyObservers("setFlagPreferenzaSorgente");
    }

    public int getPercentualeUtilizzoSorgente() {
        return percentualeUtilizzoSorgente;
    }

    public void setPercentualeUtilizzoSorgente(final int percentualeUtilizzoSorgente) {
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
        notifyObservers("setPercentualeUtilizzoSorgente");
    }

    public int getPrioritaSorgente() {
        return prioritaSorgente;
    }

    public void setPrioritaSorgente(final int prioritaSorgente) {
        this.prioritaSorgente = prioritaSorgente;
        notifyObservers("setPrioritaSorgente");
    }

    @Override
    public String toString() {
        return "interagisceBean{"
                + "idParametro=" + idParametro
                + ", tipoSorgente='" + tipoSorgente + '\''
                + ", flagPreferenzaSorgente=" + flagPreferenzaSorgente
                + ", percentualeUtilizzoSorgente=" + percentualeUtilizzoSorgente
                + ", prioritaSorgente=" + prioritaSorgente
                + '}';
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }
}
