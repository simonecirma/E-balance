package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class ConsumoEdificioBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idEdificio;
    private String nomeEdificio;
    private float consumoAttuale;

    public ConsumoEdificioBean() { }

    public ConsumoEdificioBean(final int idEdificio, final String nomeEdificio,
                               final float consumoAttuale) {
        this.idEdificio = idEdificio;
        this.nomeEdificio = nomeEdificio;
        this.consumoAttuale = consumoAttuale;
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(final int idEdificio) {
        this.idEdificio = idEdificio;
        notifyObservers("setIdEdificio");
    }

    public String getNomeEdificio() {
        return nomeEdificio;
    }

    public void setNomeEdificio(final String nomeEdificio) {
        this.nomeEdificio = nomeEdificio;
        notifyObservers("setNomeEdificio");
    }

    public float getConsumoAttuale() {
        return consumoAttuale;
    }

    public void setConsumoAttuale(final float consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
        notifyObservers("setConsumoAttuale");
    }

    @Override
    public String toString() {
        return "consumoEdificioBean{"
                + "idEdificio=" + idEdificio
                + ", nomeEdificio='" + nomeEdificio + '\''
                + ", consumoAttuale=" + consumoAttuale
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
