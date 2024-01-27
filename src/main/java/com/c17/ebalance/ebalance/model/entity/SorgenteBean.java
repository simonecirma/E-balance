package com.c17.ebalance.ebalance.model.entity;
import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SorgenteBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idSorgente;
    private String tipologia;
    private Date dataInstallazione;
    private float produzioneAttuale;
    private boolean flagStatoSorgente;
    private boolean flagAttivazioneSorgente;

    public SorgenteBean() { }

    public SorgenteBean(final int idSorgente, final String tipologia, final Date dataInstallazione,
                        final float produzioneAttuale, final boolean flagStatoSorgente,
                        final boolean flagAttivazioneSorgente) {
        this.idSorgente = idSorgente;
        this.tipologia = tipologia;
        this.dataInstallazione = dataInstallazione;
        this.produzioneAttuale = produzioneAttuale;
        this.flagStatoSorgente = flagStatoSorgente;
        this.flagAttivazioneSorgente = flagAttivazioneSorgente;
    }

    public int getIdSorgente() {
        return idSorgente;
    }

    public void setIdSorgente(final int idSorgente) {
        this.idSorgente = idSorgente;
        notifyObservers("setIdSorgente");
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(final String tipologia) {
        this.tipologia = tipologia;
        notifyObservers("setTipologia");
    }

    public Date getDataInstallazione() {
        return dataInstallazione;
    }

    public void setDataInstallazione(final Date dataInstallazione) {
        this.dataInstallazione = dataInstallazione;
        notifyObservers("setDataInstallazione");
    }

    public float getProduzioneAttuale() {
        return produzioneAttuale;
    }

    public void setProduzioneAttuale(final float produzioneAttuale) {
        this.produzioneAttuale = produzioneAttuale;
        notifyObservers("setProduzioneAttuale");
    }

    public boolean getFlagStatoSorgente() {
        return flagStatoSorgente;
    }

    public void setFlagStatoSorgente(final boolean flagStatoSorgente) {
        this.flagStatoSorgente = flagStatoSorgente;
        notifyObservers("setFlagStatoSorgente");
    }

    public boolean getFlagAttivazioneSorgente() {
        return flagAttivazioneSorgente;
    }

    public void setFlagAttivazioneSorgente(final boolean flagAttivazioneSorgente) {
        this.flagAttivazioneSorgente = flagAttivazioneSorgente;
        notifyObservers("setFlagAttivazioneSorgente");
    }

    @Override
    public String toString() {
        return "sorgenteBean{"
                + "idSorgente=" + idSorgente
                + ", tipologia='" + tipologia + '\''
                + ", dataInstallazione=" + dataInstallazione
                + ", produzioneAttuale=" + produzioneAttuale
                + ", flagStatoSorgente=" + flagStatoSorgente
                + ", flagAttivazioneSorgente=" + flagAttivazioneSorgente
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
