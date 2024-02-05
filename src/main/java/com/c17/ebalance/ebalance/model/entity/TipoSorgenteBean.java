package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code TipoSorgenteBean} rappresenta un oggetto contenente le informazioni sul tipo di sorgente di energia.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class TipoSorgenteBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private String tipo;

    /**
     * Costruttore vuoto di default.
     */
    public TipoSorgenteBean() {
    }

    /**
     * Costruttore che inizializza un oggetto {@code TipoSorgenteBean} con le informazioni fornite.
     *
     * @param tipo Tipo di sorgente di energia.
     */
    public TipoSorgenteBean(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * Restituisce il tipo di sorgente di energia.
     *
     * @return Tipo di sorgente di energia.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Imposta il tipo di sorgente di energia e notifica gli osservatori.
     *
     * @param tipo Nuovo tipo di sorgente di energia.
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
        notifyObservers("setTipo");
    }

    /**
     * Restituisce una stringa rappresentante l'oggetto {@code TipoSorgenteBean}.
     *
     * @return Stringa rappresentante l'oggetto.
     */
    @Override
    public String toString() {
        return "TipoSorgenteBean{"
                + "tipo='" + tipo + '\''
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
