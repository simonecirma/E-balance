package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code MeteoBean} rappresenta un oggetto di rilevazione meteorologica.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class MeteoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idMeteo;
    private Date dataRilevazione;
    private Time oraRilevazione;
    private float velocitaVento;
    private int probabilitaPioggia;
    private String condizioniMetereologiche;

    /**
     * Costruttore di default.
     */
    public MeteoBean() {
    }

    /**
     * Costruttore con parametri per inizializzare un oggetto MeteoBean.
     *
     * @param idMeteo                   Identificatore della rilevazione meteorologica.
     * @param dataRilevazione           Data della rilevazione meteorologica.
     * @param oraRilevazione            Ora della rilevazione meteorologica.
     * @param velocitaVento             Velocità del vento rilevata.
     * @param probabilitaPioggia         Probabilità di pioggia rilevata.
     * @param condizioniMetereologiche  Condizioni meteorologiche rilevate.
     */
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

    /**
     * Restituisce l'identificatore della rilevazione meteorologica.
     *
     * @return Identificatore della rilevazione meteorologica.
     */
    public int getIdMeteo() {
        return idMeteo;
    }

    /**
     * Imposta l'identificatore della rilevazione meteorologica e notifica gli osservatori.
     *
     * @param idMeteo Nuovo identificatore della rilevazione meteorologica.
     */
    public void setIdMeteo(final int idMeteo) {
        this.idMeteo = idMeteo;
        notifyObservers("setIdMeteo");
    }

    /**
     * Restituisce la data della rilevazione meteorologica.
     *
     * @return Data della rilevazione meteorologica.
     */
    public Date getDataRilevazione() {
        return dataRilevazione;
    }

    /**
     * Imposta la data della rilevazione meteorologica e notifica gli osservatori.
     *
     * @param dataRilevazione Nuova data della rilevazione meteorologica.
     */
    public void setDataRilevazione(final Date dataRilevazione) {
        this.dataRilevazione = dataRilevazione;
        notifyObservers("setDataRilevazione");
    }

    /**
     * Restituisce l'ora della rilevazione meteorologica.
     *
     * @return Ora della rilevazione meteorologica.
     */
    public Time getOraRilevazione() {
        return oraRilevazione;
    }

    /**
     * Imposta l'ora della rilevazione meteorologica e notifica gli osservatori.
     *
     * @param oraRilevazione Nuova ora della rilevazione meteorologica.
     */
    public void setOraRilevazione(final Time oraRilevazione) {
        this.oraRilevazione = oraRilevazione;
        notifyObservers("setOraRilevazione");
    }

    /**
     * Restituisce la velocità del vento rilevata.
     *
     * @return Velocità del vento rilevata.
     */
    public float getVelocitaVento() {
        return velocitaVento;
    }

    /**
     * Imposta la velocità del vento rilevata e notifica gli osservatori.
     *
     * @param velocitaVento Nuova velocità del vento rilevata.
     */
    public void setVelocitaVento(final float velocitaVento) {
        this.velocitaVento = velocitaVento;
        notifyObservers("setVelocitaVento");
    }

    /**
     * Restituisce la probabilità di pioggia rilevata.
     *
     * @return Probabilità di pioggia rilevata.
     */
    public int getProbabilitaPioggia() {
        return probabilitaPioggia;
    }

    /**
     * Imposta la probabilità di pioggia rilevata e notifica gli osservatori.
     *
     * @param probabilitaPioggia Nuova probabilità di pioggia rilevata.
     */
    public void setProbabilitaPioggia(final int probabilitaPioggia) {
        this.probabilitaPioggia = probabilitaPioggia;
        notifyObservers("setProbabilitaPioggia");
    }

    /**
     * Restituisce le condizioni meteorologiche rilevate.
     *
     * @return Condizioni meteorologiche rilevate.
     */
    public String getCondizioniMetereologiche() {
        return condizioniMetereologiche;
    }

    /**
     * Imposta le condizioni meteorologiche rilevate e notifica gli osservatori.
     *
     * @param condizioniMetereologiche Nuove condizioni meteorologiche rilevate.
     */
    public void setCondizioniMetereologiche(final String condizioniMetereologiche) {
        this.condizioniMetereologiche = condizioniMetereologiche;
        notifyObservers("setCondizioniMetereologiche");
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto MeteoBean.
     *
     * @return Stringa che rappresenta l'oggetto MeteoBean.
     */
    @Override
    public String toString() {
        return "MeteoBean{"
                + "idMeteo=" + idMeteo
                + ", dataRilevazione=" + dataRilevazione
                + ", oraRilevazione=" + oraRilevazione
                + ", velocitaVento=" + velocitaVento
                + ", probabilitaPioggia=" + probabilitaPioggia
                + ", condizioniMetereologiche='" + condizioniMetereologiche + '\''
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
