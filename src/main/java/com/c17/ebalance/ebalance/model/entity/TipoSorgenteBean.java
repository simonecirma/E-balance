package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class TipoSorgenteBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private String tipo;

    public TipoSorgenteBean() {
    }

    public TipoSorgenteBean(final String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
        notifyObservers("setTipo");
    }

    @Override
    public String toString() {
        return "tipoSorgenteBean{"
                + "tipo='" + tipo + '\''
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
