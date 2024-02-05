package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code ProduzioneAttualeBean} rappresenta un oggetto contenente la produzione energetica attuale.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ProduzioneAttualeBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private float produzioneAttuale;

    /**
     * Restituisce la produzione energetica attuale.
     *
     * @return Produzione energetica attuale.
     */
    public float getProduzioneAttuale() {
        return produzioneAttuale;
    }

    /**
     * Imposta la produzione energetica attuale e notifica gli osservatori.
     *
     * @param produzioneAttuale Nuova produzione energetica attuale.
     */
    public void setProduzioneAttuale(float produzioneAttuale) {
        this.produzioneAttuale = produzioneAttuale;
        notifyObservers("setProduzioneAttuale");
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
