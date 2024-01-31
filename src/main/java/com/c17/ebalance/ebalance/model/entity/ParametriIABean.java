package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class ParametriIABean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idParametro;
    private String piano;
    private boolean flagAttivazioneParametro;
    private int idAmministratore;

    public ParametriIABean() {
    }

    public ParametriIABean(final int idParametro, final String piano,
                           final boolean flagAttivazioneParametro, final int idAmministratore) {
        this.idParametro = idParametro;
        this.piano = piano;
        this.flagAttivazioneParametro = flagAttivazioneParametro;
        this.idAmministratore = idAmministratore;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(final int idParametro) {
        this.idParametro = idParametro;
        notifyObservers("setIdParametro");
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(final String piano) {
        this.piano = piano;
        notifyObservers("setPiano");
    }

    public boolean getFlagAttivazioneParametro() {
        return flagAttivazioneParametro;
    }

    public void setFlagAttivazioneParametro(final boolean flagAttivazioneParametro) {
        this.flagAttivazioneParametro = flagAttivazioneParametro;
        notifyObservers("setFlagAttivazioneParametro");
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    @Override
    public String toString() {
        return "parametriIABean{"
                + "idParametro=" + idParametro
                + ", piano='" + piano + '\''
                + ", flagAttivazioneParametro=" + flagAttivazioneParametro
                + ", idAmministratore=" + idAmministratore
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
