package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code ParametriIABean} rappresenta un oggetto contenente i parametri relativi all'intelligenza artificiale.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ParametriIABean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idParametro;
    private String piano;
    private boolean flagAttivazioneParametro;
    private int idAmministratore;

    /**
     * Costruttore di default.
     */
    public ParametriIABean() {
    }

    /**
     * Costruttore con parametri per inizializzare un oggetto ParametriIABean.
     *
     * @param idParametro               Identificatore del parametro.
     * @param piano                     Piano relativo al parametro.
     * @param flagAttivazioneParametro  Flag di attivazione del parametro.
     * @param idAmministratore          Identificatore dell'amministratore associato al parametro.
     */
    public ParametriIABean(final int idParametro, final String piano,
                           final boolean flagAttivazioneParametro, final int idAmministratore) {
        this.idParametro = idParametro;
        this.piano = piano;
        this.flagAttivazioneParametro = flagAttivazioneParametro;
        this.idAmministratore = idAmministratore;
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
     * Restituisce il piano relativo al parametro.
     *
     * @return Piano relativo al parametro.
     */
    public String getPiano() {
        return piano;
    }

    /**
     * Imposta il piano relativo al parametro e notifica gli osservatori.
     *
     * @param piano Nuovo piano relativo al parametro.
     */
    public void setPiano(final String piano) {
        this.piano = piano;
        notifyObservers("setPiano");
    }

    /**
     * Restituisce il flag di attivazione del parametro.
     *
     * @return Flag di attivazione del parametro.
     */
    public boolean getFlagAttivazioneParametro() {
        return flagAttivazioneParametro;
    }

    /**
     * Imposta il flag di attivazione del parametro e notifica gli osservatori.
     *
     * @param flagAttivazioneParametro Nuovo flag di attivazione del parametro.
     */
    public void setFlagAttivazioneParametro(final boolean flagAttivazioneParametro) {
        this.flagAttivazioneParametro = flagAttivazioneParametro;
        notifyObservers("setFlagAttivazioneParametro");
    }

    /**
     * Restituisce l'identificatore dell'amministratore associato al parametro.
     *
     * @return Identificatore dell'amministratore associato al parametro.
     */
    public int getIdAmministratore() {
        return idAmministratore;
    }

    /**
     * Imposta l'identificatore dell'amministratore associato al parametro e notifica gli osservatori.
     *
     * @param idAmministratore Nuovo identificatore dell'amministratore associato al parametro.
     */
    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto ParametriIABean.
     *
     * @return Stringa che rappresenta l'oggetto ParametriIABean.
     */
    @Override
    public String toString() {
        return "ParametriIABean{"
                + "idParametro=" + idParametro
                + ", piano='" + piano + '\''
                + ", flagAttivazioneParametro=" + flagAttivazioneParametro
                + ", idAmministratore=" + idAmministratore
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
