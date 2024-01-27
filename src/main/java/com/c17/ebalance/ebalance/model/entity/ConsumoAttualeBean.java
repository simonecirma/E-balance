package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class ConsumoAttualeBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private float consumoAttuale;

    public float getConsumoAttuale() {
        return consumoAttuale;
    }

    public void setConsumoAttuale(float consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
        notifyObservers("setConsumoAttuale");
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

