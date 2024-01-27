package com.c17.ebalance.ebalance.model.entity;
import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeteoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idMeteo;
    private Date dataRilevazione;
    private Time oraRilevazione;
    private float velocitaVento;
    private int probabilitaPioggia;
    private String condizioniMetereologiche;

    public MeteoBean() { }

    public MeteoBean(final int idMeteo, final Date dataRilevazione,
                     final Time oraRilevazione, final float velocitaVento,
                     final int probabilitaPioggia,
                     final String condizioniMetereologiche) {
        this.idMeteo = idMeteo;
        this.dataRilevazione = dataRilevazione;
        this.oraRilevazione = oraRilevazione;
        this.velocitaVento = velocitaVento;
        this.probabilitaPioggia = probabilitaPioggia;
        this.condizioniMetereologiche = condizioniMetereologiche;
    }

    public int getIdMeteo() {
        return idMeteo;
    }

    public void setIdMeteo(final int idMeteo) {
        this.idMeteo = idMeteo;
        notifyObservers("setIdMeteo");
    }

    public Date getDataRilevazione() {
        return dataRilevazione;
    }

    public void setDataRilevazione(final Date dataRilevazione) {
        this.dataRilevazione = dataRilevazione;
        notifyObservers("setDataRilevazione");
    }

    public Time getOraRilevazione() {
        return oraRilevazione;
    }

    public void setOraRilevazione(final Time oraRilevazione) {
        this.oraRilevazione = oraRilevazione;
        notifyObservers("setOraRilevazione");
    }

    public float getVelocitaVento() {
        return velocitaVento;
    }

    public void setVelocitaVento(final float velocitaVento) {
        this.velocitaVento = velocitaVento;
        notifyObservers("setVelocitaVento");
    }

    public int getProbabilitaPioggia() {
        return probabilitaPioggia;
    }

    public void setProbabilitaPioggia(final int probabilitaPioggia) {
        this.probabilitaPioggia = probabilitaPioggia;
        notifyObservers("setProbabilitaPioggia");
    }

    public String getCondizioniMetereologiche() {
        return condizioniMetereologiche;
    }

    public void setCondizioniMetereologiche(final String condizioniMetereologiche) {
        this.condizioniMetereologiche = condizioniMetereologiche;
        notifyObservers("setCondizioniMetereologiche");
    }

    @Override
    public String toString() {
        return "meteoBean{"
                + "idMeteo=" + idMeteo
                + ", dataRilevazione=" + dataRilevazione
                + ", oraRilevazione=" + oraRilevazione
                + ", velocitaVento=" + velocitaVento
                + ", probabilitaPioggia=" + probabilitaPioggia
                + ", condizioniMetereologiche='" + condizioniMetereologiche + '\''
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
