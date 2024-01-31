package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

public class BatteriaBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idBatteria;
    private boolean flagStatoBatteria;
    private float capacitaMax;
    private float percentualeCarica;

    public BatteriaBean() {
    }

    public BatteriaBean(final int idBatteria, final boolean flagStatoBatteria,
                        final float capacitaMax, final float percentualeCarica) {
        this.idBatteria = idBatteria;
        this.flagStatoBatteria = flagStatoBatteria;
        this.capacitaMax = capacitaMax;
        this.percentualeCarica = percentualeCarica;
    }

    public int getIdBatteria() {
        return idBatteria;
    }

    public void setIdBatteria(final int idBatteria) {
        this.idBatteria = idBatteria;
        notifyObservers("setIdBatteria");
    }

    public boolean getFlagStatoBatteria() {
        return flagStatoBatteria;
    }

    public void setFlagStatoBatteria(final boolean flagStatoBatteria) {
        this.flagStatoBatteria = flagStatoBatteria;
        notifyObservers("setFlagStatoBatteria");
    }

    public float getCapacitaMax() {
        return capacitaMax;
    }

    public void setCapacitaMax(final float capacitaMax) {
        this.capacitaMax = capacitaMax;
        notifyObservers("setCapacitaMax");
    }

    public float getPercentualeCarica() {
        return percentualeCarica;
    }

    public void setPercentualeCarica(final float percentualeCarica) {
        this.percentualeCarica = percentualeCarica;
        notifyObservers("setPercentualeCarica");
    }

    @Override
    public String toString() {
        return "batteriaBean{"
                + "idBatteria=" + idBatteria
                + ", flagStatoBatteria=" + flagStatoBatteria
                + ", capacitaMax=" + capacitaMax
                + ", percentualeCarica=" + percentualeCarica
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
