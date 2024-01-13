package com.c17.ebalance.ebalance.model.entity;

import java.util.*;

public class contrattoBean {

    private int idContratto;
    private String nomeEnte;
    private float consumoMedioAnnuale;
    private float costoMedioUnitario;
    private Date dataSottoscrizione;
    private int durata;
    private float prezzoVendita;
    private int idAmministatore;

    public contrattoBean() {}

    public contrattoBean(int idContratto, String nomeEnte, float consumoMedioAnnuale,
                         float costoMedioUnitario, Date dataSottoscrizione, int durata,
                         float prezzoVendita, int idAmministatore) {
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

    public void setIdContratto(int idContratto) {
        this.idContratto = idContratto;
    }

    public String getNomeEnte() {
        return nomeEnte;
    }

    public void setNomeEnte(String nomeEnte) {
        this.nomeEnte = nomeEnte;
    }

    public float getConsumoMedioAnnuale() {
        return consumoMedioAnnuale;
    }

    public void setConsumoMedioAnnuale(float consumoMedioAnnuale) {
        this.consumoMedioAnnuale = consumoMedioAnnuale;
    }

    public float getCostoMedioUnitario() {
        return costoMedioUnitario;
    }

    public void setCostoMedioUnitario(float costoMedioUnitario) {
        this.costoMedioUnitario = costoMedioUnitario;
    }

    public Date getDataSottoscrizione() {
        return dataSottoscrizione;
    }

    public void setDataSottoscrizione(Date dataSottoscrizione) {
        this.dataSottoscrizione = dataSottoscrizione;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public float getPrezzoVendita() {
        return prezzoVendita;
    }

    public void setPrezzoVendita(float prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
    }

    public int getIdAmministatore() {
        return idAmministatore;
    }

    public void setIdAmministatore(int idAmministatore) {
        this.idAmministatore = idAmministatore;
    }

    @Override
    public String toString() {
        return "contrattoBean{" +
                "idContratto=" + idContratto +
                ", nomeEnte='" + nomeEnte + '\'' +
                ", consumoMedioAnnuale=" + consumoMedioAnnuale +
                ", costoMedioUnitario=" + costoMedioUnitario +
                ", dataSottoscrizione=" + dataSottoscrizione +
                ", durata=" + durata +
                ", prezzoVendita=" + prezzoVendita +
                ", idAmministatore=" + idAmministatore +
                '}';
    }
}
