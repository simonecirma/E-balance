package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code CondizioneMeteoBean} rappresenta un oggetto di tipo condizione meteo archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class CondizioneMeteoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private String condizione;

    /**
     * Costruttore vuoto per la classe {@code CondizioneMeteoBean}.
     */
    public CondizioneMeteoBean() {
    }

    /**
     * Costruttore della classe {@code CondizioneMeteoBean} con parametri.
     *
     * @param condizione Condizione meteo archiviata.
     */
    public CondizioneMeteoBean(final String condizione) {
        this.condizione = condizione;
    }

    /**
     * Restituisce la condizione meteo archiviata.
     *
     * @return Condizione meteo archiviata.
     */
    public String getCondizione() {
        return condizione;
    }

    /**
     * Imposta la condizione meteo archiviata.
     *
     * @param condizione Nuova condizione meteo archiviata.
     */
    public void setCondizione(final String condizione) {
        this.condizione = condizione;
        notifyObservers("setCondizione");
    }

    /**
     * Override del metodo toString che restituisce una rappresentazione testuale dell'oggetto.
     *
     * @return Stringa che rappresenta l'oggetto {@code CondizioneMeteoBean}.
     */
    @Override
    public String toString() {
        return "CondizioneMeteoBean{"
                + "condizione='" + condizione + '\''
                + '}';
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
