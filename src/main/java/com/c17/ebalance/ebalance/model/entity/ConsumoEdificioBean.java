package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code ConsumoEdificioBean} rappresenta un oggetto di tipo consumo edificio archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ConsumoEdificioBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idEdificio;
    private String nomeEdificio;
    private float consumoAttuale;

    /**
     * Costruttore di default.
     */
    public ConsumoEdificioBean() {
    }

    /**
     * Costruttore con parametri per inizializzare un oggetto ConsumoEdificioBean.
     *
     * @param idEdificio     Identificatore dell'edificio.
     * @param nomeEdificio   Nome dell'edificio.
     * @param consumoAttuale Consumo attuale dell'edificio.
     */
    public ConsumoEdificioBean(final int idEdificio, final String nomeEdificio,
                               final float consumoAttuale) {
        this.idEdificio = idEdificio;
        this.nomeEdificio = nomeEdificio;
        this.consumoAttuale = consumoAttuale;
    }

    /**
     * Restituisce l'identificatore dell'edificio.
     *
     * @return Identificatore dell'edificio.
     */
    public int getIdEdificio() {
        return idEdificio;
    }

    /**
     * Imposta l'identificatore dell'edificio e notifica gli osservatori.
     *
     * @param idEdificio Nuovo identificatore dell'edificio.
     */
    public void setIdEdificio(final int idEdificio) {
        this.idEdificio = idEdificio;
        notifyObservers("setIdEdificio");
    }

    /**
     * Restituisce il nome dell'edificio.
     *
     * @return Nome dell'edificio.
     */
    public String getNomeEdificio() {
        return nomeEdificio;
    }

    /**
     * Imposta il nome dell'edificio e notifica gli osservatori.
     *
     * @param nomeEdificio Nuovo nome dell'edificio.
     */
    public void setNomeEdificio(final String nomeEdificio) {
        this.nomeEdificio = nomeEdificio;
        notifyObservers("setNomeEdificio");
    }

    /**
     * Restituisce il consumo attuale dell'edificio.
     *
     * @return Consumo attuale dell'edificio.
     */
    public float getConsumoAttuale() {
        return consumoAttuale;
    }

    /**
     * Imposta il consumo attuale dell'edificio e notifica gli osservatori.
     *
     * @param consumoAttuale Nuovo consumo attuale dell'edificio.
     */
    public void setConsumoAttuale(final float consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
        notifyObservers("setConsumoAttuale");
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto ConsumoEdificioBean.
     *
     * @return Stringa che rappresenta l'oggetto ConsumoEdificioBean.
     */
    @Override
    public String toString() {
        return "ConsumoEdificioBean{"
                + "idEdificio=" + idEdificio
                + ", nomeEdificio='" + nomeEdificio + '\''
                + ", consumoAttuale=" + consumoAttuale
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
