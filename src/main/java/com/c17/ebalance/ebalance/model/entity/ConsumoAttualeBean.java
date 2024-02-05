package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code ConsumoAttualeBean} rappresenta un oggetto di tipo consumo attuale archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ConsumoAttualeBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private float consumoAttuale;

    /**
     * Restituisce il consumo attuale archiviato.
     *
     * @return Consumo attuale archiviato.
     */
    public float getConsumoAttuale() {
        return consumoAttuale;
    }

    /**
     * Imposta il consumo attuale archiviato.
     *
     * @param consumoAttuale Nuovo consumo attuale archiviato.
     */
    public void setConsumoAttuale(float consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
        notifyObservers("setConsumoAttuale");
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
