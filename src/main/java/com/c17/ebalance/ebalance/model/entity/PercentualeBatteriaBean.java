package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code PercentualeBatteriaBean} rappresenta un oggetto contenente la percentuale di carica della batteria.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class PercentualeBatteriaBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private float percentualeBatteria;

    /**
     * Restituisce la percentuale di carica della batteria.
     *
     * @return Percentuale di carica della batteria.
     */
    public float getPercentualeBatteria() {
        return percentualeBatteria;
    }

    /**
     * Imposta la percentuale di carica della batteria e notifica gli osservatori.
     *
     * @param percentualeBatteria Nuova percentuale di carica della batteria.
     */
    public void setPercentualeBatteria(float percentualeBatteria) {
        this.percentualeBatteria = percentualeBatteria;
        notifyObservers("setPercentualeBatteria");
    }

    /**
     * Aggiunge un osservatore alla lista degli osservatori.
     *
     * @param observer Osservatore da aggiungere.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un osservatore dalla lista degli osservatori.
     *
     * @param observer Osservatore da rimuovere.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori chiamando il loro metodo di aggiornamento.
     *
     * @param nomeMetodo Nome del metodo che ha generato la notifica.
     */
    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }
}
