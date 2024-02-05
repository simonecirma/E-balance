package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code BatteriaBean} rappresenta un oggetto di tipo batteria archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class BatteriaBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idBatteria;
    private boolean flagStatoBatteria;
    private float capacitaMax;
    private float percentualeCarica;

    /**
     * Costruttore vuoto per la classe {@code BatteriaBean}.
     */
    public BatteriaBean() {
    }

    /**
     * Costruttore della classe {@code BatteriaBean} con parametri.
     *
     * @param idBatteria         Identificativo della batteria archiviata.
     * @param flagStatoBatteria Flag che rappresenta lo stato della batteria (carica o scarica).
     * @param capacitaMax        Capacità massima della batteria.
     * @param percentualeCarica  Percentuale di carica attuale della batteria.
     */
    public BatteriaBean(final int idBatteria, final boolean flagStatoBatteria,
                        final float capacitaMax, final float percentualeCarica) {
        this.idBatteria = idBatteria;
        this.flagStatoBatteria = flagStatoBatteria;
        this.capacitaMax = capacitaMax;
        this.percentualeCarica = percentualeCarica;
    }

    /**
     * Restituisce l'identificativo della batteria archiviata.
     *
     * @return Identificativo della batteria archiviata.
     */
    public int getIdBatteria() {
        return idBatteria;
    }

    /**
     * Imposta l'identificativo della batteria archiviata.
     *
     * @param idBatteria Nuovo identificativo della batteria archiviata.
     */
    public void setIdBatteria(final int idBatteria) {
        this.idBatteria = idBatteria;
        notifyObservers("setIdBatteria");
    }

    /**
     * Restituisce lo stato della batteria (carica o scarica).
     *
     * @return {@code true} se la batteria è carica, {@code false} altrimenti.
     */
    public boolean getFlagStatoBatteria() {
        return flagStatoBatteria;
    }

    /**
     * Imposta lo stato della batteria (carica o scarica).
     *
     * @param flagStatoBatteria Nuovo stato della batteria.
     */
    public void setFlagStatoBatteria(final boolean flagStatoBatteria) {
        this.flagStatoBatteria = flagStatoBatteria;
        notifyObservers("setFlagStatoBatteria");
    }

    /**
     * Restituisce la capacità massima della batteria.
     *
     * @return Capacità massima della batteria.
     */
    public float getCapacitaMax() {
        return capacitaMax;
    }

    /**
     * Imposta la capacità massima della batteria.
     *
     * @param capacitaMax Nuova capacità massima della batteria.
     */
    public void setCapacitaMax(final float capacitaMax) {
        this.capacitaMax = capacitaMax;
        notifyObservers("setCapacitaMax");
    }

    /**
     * Restituisce la percentuale di carica attuale della batteria.
     *
     * @return Percentuale di carica attuale della batteria.
     */
    public float getPercentualeCarica() {
        return percentualeCarica;
    }

    /**
     * Imposta la percentuale di carica attuale della batteria.
     *
     * @param percentualeCarica Nuova percentuale di carica attuale della batteria.
     */
    public void setPercentualeCarica(final float percentualeCarica) {
        this.percentualeCarica = percentualeCarica;
        notifyObservers("setPercentualeCarica");
    }

    /**
     * Override del metodo toString che restituisce una rappresentazione testuale dell'oggetto.
     *
     * @return Stringa che rappresenta l'oggetto {@code BatteriaBean}.
     */
    @Override
    public String toString() {
        return "BatteriaBean{"
                + "idBatteria=" + idBatteria
                + ", flagStatoBatteria=" + flagStatoBatteria
                + ", capacitaMax=" + capacitaMax
                + ", percentualeCarica=" + percentualeCarica
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
