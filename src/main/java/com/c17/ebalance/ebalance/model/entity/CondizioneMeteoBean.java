package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class CondizioneMeteoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private String condizione;

    public CondizioneMeteoBean() { }

    public CondizioneMeteoBean(final String condizione) {
        this.condizione = condizione;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(final String condizione) {
        this.condizione = condizione;
        notifyObservers("setCondizione");
    }

    @Override
    public String toString() {
        return "condizioneMeteoBean{"
                + "condizione='" + condizione + '\''
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
