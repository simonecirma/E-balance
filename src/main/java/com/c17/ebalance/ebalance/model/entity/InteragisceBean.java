package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code InteragisceBean} rappresenta un oggetto di interazione con una sorgente archiviata.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class InteragisceBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idParametro;
    private String tipoSorgente;
    private boolean flagPreferenzaSorgente;
    private int percentualeUtilizzoSorgente;
    private int prioritaSorgente;

    /**
     * Costruttore di default.
     */
    public InteragisceBean() { }

    /**
     * Costruttore con parametri per inizializzare un oggetto InteragisceBean.
     *
     * @param idParametro               Identificatore del parametro.
     * @param tipoSorgente              Tipo di sorgente.
     * @param flagPreferenzaSorgente    Flag di preferenza della sorgente.
     * @param percentualeUtilizzoSorgente Percentuale di utilizzo della sorgente.
     * @param prioritaSorgente          Priorità della sorgente.
     */
    public InteragisceBean(final int idParametro, final String tipoSorgente,
                           final boolean flagPreferenzaSorgente,
                           final int percentualeUtilizzoSorgente, final int prioritaSorgente) {
        this.idParametro = idParametro;
        this.tipoSorgente = tipoSorgente;
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
        this.prioritaSorgente = prioritaSorgente;
    }

    /**
     * Restituisce l'identificatore del parametro.
     *
     * @return Identificatore del parametro.
     */
    public int getIdParametro() {
        return idParametro;
    }

    /**
     * Imposta l'identificatore del parametro e notifica gli osservatori.
     *
     * @param idParametro Nuovo identificatore del parametro.
     */
    public void setIdParametro(final int idParametro) {
        this.idParametro = idParametro;
        notifyObservers("setIdParametro");
    }

    /**
     * Restituisce il tipo di sorgente associato al parametro.
     *
     * @return Tipo di sorgente.
     */
    public String getTipoSorgente() {
        return tipoSorgente;
    }

    /**
     * Imposta il tipo di sorgente associato al parametro e notifica gli osservatori.
     *
     * @param tipoSorgente Nuovo tipo di sorgente.
     */
    public void setTipoSorgente(final String tipoSorgente) {
        this.tipoSorgente = tipoSorgente;
        notifyObservers("setTipoSorgente");
    }

    /**
     * Restituisce il flag di preferenza della sorgente associato al parametro.
     *
     * @return Flag di preferenza della sorgente.
     */
    public boolean getFlagPreferenzaSorgente() {
        return flagPreferenzaSorgente;
    }

    /**
     * Imposta il flag di preferenza della sorgente associato al parametro e notifica gli osservatori.
     *
     * @param flagPreferenzaSorgente Nuovo flag di preferenza della sorgente.
     */
    public void setFlagPreferenzaSorgente(final boolean flagPreferenzaSorgente) {
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
        notifyObservers("setFlagPreferenzaSorgente");
    }

    /**
     * Restituisce la percentuale di utilizzo della sorgente associata al parametro.
     *
     * @return Percentuale di utilizzo della sorgente.
     */
    public int getPercentualeUtilizzoSorgente() {
        return percentualeUtilizzoSorgente;
    }

    /**
     * Imposta la percentuale di utilizzo della sorgente associata al parametro e notifica gli osservatori.
     *
     * @param percentualeUtilizzoSorgente Nuova percentuale di utilizzo della sorgente.
     */
    public void setPercentualeUtilizzoSorgente(final int percentualeUtilizzoSorgente) {
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
        notifyObservers("setPercentualeUtilizzoSorgente");
    }

    /**
     * Restituisce la priorità della sorgente associata al parametro.
     *
     * @return Priorità della sorgente.
     */
    public int getPrioritaSorgente() {
        return prioritaSorgente;
    }

    /**
     * Imposta la priorità della sorgente associata al parametro e notifica gli osservatori.
     *
     * @param prioritaSorgente Nuova priorità della sorgente.
     */
    public void setPrioritaSorgente(final int prioritaSorgente) {
        this.prioritaSorgente = prioritaSorgente;
        notifyObservers("setPrioritaSorgente");
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto InteragisceBean.
     *
     * @return Stringa che rappresenta l'oggetto InteragisceBean.
     */
    @Override
    public String toString() {
        return "InteragisceBean{"
                + "idParametro=" + idParametro
                + ", tipoSorgente='" + tipoSorgente + '\''
                + ", flagPreferenzaSorgente=" + flagPreferenzaSorgente
                + ", percentualeUtilizzoSorgente=" + percentualeUtilizzoSorgente
                + ", prioritaSorgente=" + prioritaSorgente
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
