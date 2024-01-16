package com.c17.ebalance.ebalance.model.entity;
import java.util.Date;

public class VenditaBean {
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
    }

    public float getEnergiaVenduta() {
        return energiaVenduta;
    }

    public void setEnergiaVenduta(final float energiaVenduta) {
        this.energiaVenduta = energiaVenduta;
    }

    public Date getDataVendita() {
        return dataVendita;
    }

    public void setDataVendita(final Date dataVendita) {
        this.dataVendita = dataVendita;
    }

    public float getRicavoTotale() {
        return ricavoTotale;
    }

    public void setRicavoTotale(final float ricavoTotale) {
        this.ricavoTotale = ricavoTotale;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
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
}
