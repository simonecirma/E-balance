package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class PercentualeBatteriaBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private float percentualeBatteria;

    public float getPercentualeBatteria() {
        return percentualeBatteria;
    }

    public void setPercentualeBatteria(float percentualeBatteria) {
        this.percentualeBatteria = percentualeBatteria;
        notifyObservers("setPercentualeBatteria");
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


