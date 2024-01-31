package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContrattoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();

    private int idContratto;
    private String nomeEnte;
    private float consumoMedioAnnuale;
    private float costoMedioUnitario;
    private Date dataSottoscrizione;
    private int durata;
    private float prezzoVendita;
    private int idAmministatore;

    public ContrattoBean() {
    }

    public ContrattoBean(final int idContratto, final String nomeEnte, final float consumoMedioAnnuale,
                         final float costoMedioUnitario, final Date dataSottoscrizione, final int durata,
                         final float prezzoVendita, final int idAmministatore) {
        this.idContratto = idContratto;
        this.nomeEnte = nomeEnte;
        this.consumoMedioAnnuale = consumoMedioAnnuale;
        this.costoMedioUnitario = costoMedioUnitario;
        this.dataSottoscrizione = dataSottoscrizione;
        this.durata = durata;
        this.prezzoVendita = prezzoVendita;
        this.idAmministatore = idAmministatore;
    }

    public int getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(final int idContratto) {
        this.idContratto = idContratto;
        notifyObservers("setIdContratto");
    }

    public String getNomeEnte() {
        return nomeEnte;
    }

    public void setNomeEnte(final String nomeEnte) {
        this.nomeEnte = nomeEnte;
        notifyObservers("setNomeEnte");
    }

    public float getConsumoMedioAnnuale() {
        return consumoMedioAnnuale;
    }

    public void setConsumoMedioAnnuale(final float consumoMedioAnnuale) {
        this.consumoMedioAnnuale = consumoMedioAnnuale;
        notifyObservers("setConsumoMedioAnnuale");
    }

    public float getCostoMedioUnitario() {
        return costoMedioUnitario;
    }

    public void setCostoMedioUnitario(final float costoMedioUnitario) {
        this.costoMedioUnitario = costoMedioUnitario;
        notifyObservers("setCostoMedioUnitario");
    }

    public Date getDataSottoscrizione() {
        return dataSottoscrizione;
    }

    public void setDataSottoscrizione(final Date dataSottoscrizione) {
        this.dataSottoscrizione = dataSottoscrizione;
        notifyObservers("setDataSottoscrizione");
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(final int durata) {
        this.durata = durata;
        notifyObservers("setDurata");
    }

    public float getPrezzoVendita() {
        return prezzoVendita;
    }

    public void setPrezzoVendita(final float prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
        notifyObservers("setPrezzoVendita");
    }

    public int getIdAmministatore() {
        return idAmministatore;
    }

    public void setIdAmministatore(final int idAmministatore) {
        this.idAmministatore = idAmministatore;
        notifyObservers("setIdAmministatore");
    }

    @Override
    public String toString() {
        return "contrattoBean{"
                + "idContratto=" + idContratto
                + ", nomeEnte='" + nomeEnte + '\''
                + ", consumoMedioAnnuale=" + consumoMedioAnnuale
                + ", costoMedioUnitario=" + costoMedioUnitario
                + ", dataSottoscrizione=" + dataSottoscrizione
                + ", durata=" + durata
                + ", prezzoVendita=" + prezzoVendita
                + ", idAmministatore=" + idAmministatore
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
