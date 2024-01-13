package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class venditaBean {
    private int idVendita;
    private float energiaVenduta;
    private Date dataVendita;
    private float ricavoTotale;
    private int idAmministratore;

    public venditaBean() {}

    public venditaBean(int idVendita, float energiaVenduta, Date dataVendita, float ricavoTotale, int idAmministratore) {
        this.idVendita = idVendita;
        this.energiaVenduta = energiaVenduta;
        this.dataVendita = dataVendita;
        this.ricavoTotale = ricavoTotale;
        this.idAmministratore = idAmministratore;
    }

    public int getIdVendita() {
        return idVendita;
    }

    public void setIdVendita(int idVendita) {
        this.idVendita = idVendita;
    }

    public float getEnergiaVenduta() {
        return energiaVenduta;
    }

    public void setEnergiaVenduta(float energiaVenduta) {
        this.energiaVenduta = energiaVenduta;
    }

    public Date getDataVendita() {
        return dataVendita;
    }

    public void setDataVendita(Date dataVendita) {
        this.dataVendita = dataVendita;
    }

    public float getRicavoTotale() {
        return ricavoTotale;
    }

    public void setRicavoTotale(float ricavoTotale) {
        this.ricavoTotale = ricavoTotale;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    @Override
    public String toString() {
        return "venditaBean{" +
                "idVendita=" + idVendita +
                ", energiaVenduta=" + energiaVenduta +
                ", dataVendita=" + dataVendita +
                ", ricavoTotale=" + ricavoTotale +
                ", idAmministratore=" + idAmministratore +
                '}';
    }
}
