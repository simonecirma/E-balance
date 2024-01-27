package com.c17.ebalance.ebalance.model.entity;
import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArchivioProduzioneBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idProduzione;
    private Date dataProduzione;
    private float produzioneGiornaliera;
    private int idSorgente;

    public ArchivioProduzioneBean() { }

    public ArchivioProduzioneBean(final int idProduzione, final Date dataProduzione,
                                  final float produzioneGiornaliera, final int idSorgente) {
        this.idProduzione = idProduzione;
        this.dataProduzione = dataProduzione;
        this.produzioneGiornaliera = produzioneGiornaliera;
        this.idSorgente = idSorgente;
    }

    public int getIdProduzione() {
        return idProduzione;
    }

    public void setIdProduzione(final int idProduzione) {
        this.idProduzione = idProduzione;
        notifyObservers("setIdProduzione");
    }

    public Date getDataProduzione() {
        return dataProduzione;
    }

    public void setDataProduzione(final Date dataProduzione) {
        this.dataProduzione = dataProduzione;
        notifyObservers("setDataProduzione");
    }

    public float getProduzioneGiornaliera() {
        return produzioneGiornaliera;
    }

    public void setProduzioneGiornaliera(final float produzioneGiornaliera) {
        this.produzioneGiornaliera = produzioneGiornaliera;
        notifyObservers("setProduzioneGiornaliera");
    }

    @Override
    public String toString() {
        return "produzioneBean{"
                + "idProduzione=" + idProduzione
                + ", dataProduzione=" + dataProduzione
                + ", produzioneGiornaliera=" + produzioneGiornaliera
                + ", idSorgente=" + idSorgente
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
