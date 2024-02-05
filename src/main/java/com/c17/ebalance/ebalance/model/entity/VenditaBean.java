package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * La classe {@code VenditaBean} rappresenta un oggetto contenente le informazioni su una vendita di energia.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class VenditaBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idVendita;
    private float energiaVenduta;
    private Date dataVendita;
    private float ricavoTotale;
    private int idAmministratore;

    /**
     * Costruttore vuoto di default.
     */
    public VenditaBean() {
    }

    /**
     * Costruttore che inizializza un oggetto {@code VenditaBean} con le informazioni fornite.
     *
     * @param idVendita           Identificativo della vendita.
     * @param energiaVenduta      Quantità di energia venduta.
     * @param dataVendita         Data della vendita.
     * @param ricavoTotale        Ricavo totale della vendita.
     * @param idAmministratore    Identificativo dell'amministratore responsabile della vendita.
     */
    public VenditaBean(final int idVendita, final float energiaVenduta,
                       final Date dataVendita, final float ricavoTotale, final int idAmministratore) {
        this.idVendita = idVendita;
        this.energiaVenduta = energiaVenduta;
        this.dataVendita = dataVendita;
        this.ricavoTotale = ricavoTotale;
        this.idAmministratore = idAmministratore;
    }

    /**
     * Restituisce l'identificativo della vendita.
     *
     * @return Identificativo della vendita.
     */
    public int getIdVendita() {
        return idVendita;
    }

    /**
     * Imposta l'identificativo della vendita e notifica gli osservatori.
     *
     * @param idVendita Nuovo identificativo della vendita.
     */
    public void setIdVendita(final int idVendita) {
        this.idVendita = idVendita;
        notifyObservers("setIdVendita");
    }

    /**
     * Restituisce la quantità di energia venduta.
     *
     * @return Quantità di energia venduta.
     */
    public float getEnergiaVenduta() {
        return energiaVenduta;
    }

    /**
     * Imposta la quantità di energia venduta e notifica gli osservatori.
     *
     * @param energiaVenduta Nuova quantità di energia venduta.
     */
    public void setEnergiaVenduta(final float energiaVenduta) {
        this.energiaVenduta = energiaVenduta;
        notifyObservers("setEnergiaVenduta");
    }

    /**
     * Restituisce la data della vendita.
     *
     * @return Data della vendita.
     */
    public Date getDataVendita() {
        return dataVendita;
    }

    /**
     * Imposta la data della vendita e notifica gli osservatori.
     *
     * @param dataVendita Nuova data della vendita.
     */
    public void setDataVendita(final Date dataVendita) {
        this.dataVendita = dataVendita;
        notifyObservers("setDataVendita");
    }

    /**
     * Restituisce il ricavo totale della vendita.
     *
     * @return Ricavo totale della vendita.
     */
    public float getRicavoTotale() {
        return ricavoTotale;
    }

    /**
     * Imposta il ricavo totale della vendita e notifica gli osservatori.
     *
     * @param ricavoTotale Nuovo ricavo totale della vendita.
     */
    public void setRicavoTotale(final float ricavoTotale) {
        this.ricavoTotale = ricavoTotale;
        notifyObservers("setRicavoTotale");
    }

    /**
     * Restituisce l'identificativo dell'amministratore responsabile della vendita.
     *
     * @return Identificativo dell'amministratore responsabile della vendita.
     */
    public int getIdAmministratore() {
        return idAmministratore;
    }

    /**
     * Imposta l'identificativo dell'amministratore responsabile della vendita e notifica gli osservatori.
     *
     * @param idAmministratore Nuovo identificativo dell'amministratore responsabile della vendita.
     */
    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    /**
     * Restituisce una stringa rappresentante l'oggetto {@code VenditaBean}.
     *
     * @return Stringa rappresentante l'oggetto.
     */
    @Override
    public String toString() {
        return "VenditaBean{"
                + "idVendita=" + idVendita
                + ", energiaVenduta=" + energiaVenduta
                + ", dataVendita=" + dataVendita
                + ", ricavoTotale=" + ricavoTotale
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
