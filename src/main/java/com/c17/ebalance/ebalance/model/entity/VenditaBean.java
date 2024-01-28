package com.c17.ebalance.ebalance.model.entity;
import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class VenditaBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idVendita;
    private float energiaVenduta;
    private Date dataVendita;
    private float ricavoTotale;
    private int idAmministratore;

    public VenditaBean() { }

    public VenditaBean(final int idVendita, final float energiaVenduta,
                       final Date dataVendita, final float ricavoTotale, final int idAmministratore) {
        this.idVendita = idVendita;
        this.energiaVenduta = energiaVenduta;
        this.dataVendita = dataVendita;
        this.ricavoTotale = ricavoTotale;
        this.idAmministratore = idAmministratore;
    }

    public int getIdVendita() {
        return idVendita;
    }

    public void setIdVendita(final int idVendita) {
        this.idVendita = idVendita;
        notifyObservers("setIdVendita");
    }

    public float getEnergiaVenduta() {
        return energiaVenduta;
    }

    public void setEnergiaVenduta(final float energiaVenduta) {
        this.energiaVenduta = energiaVenduta;
        notifyObservers("setEnergiaVenduta");
    }

    public Date getDataVendita() {
        return dataVendita;
    }

    public void setDataVendita(final Date dataVendita) {
        this.dataVendita = dataVendita;
        notifyObservers("setDataVendita");
    }

    public float getRicavoTotale() {
        return ricavoTotale;
    }

    public void setRicavoTotale(final float ricavoTotale) {
        this.ricavoTotale = ricavoTotale;
        notifyObservers("setRicavoTotale");
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    @Override
    public String toString() {
        return "venditaBean{"
                + "idVendita=" + idVendita
                + ", energiaVenduta=" + energiaVenduta
                + ", dataVendita=" + dataVendita
                + ", ricavoTotale=" + ricavoTotale
                + ", idAmministratore=" + idAmministratore
                + '}';
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }
}
