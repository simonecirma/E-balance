package com.c17.ebalance.ebalance.model.entity;

import java.util.Date;

public class ContrattoBean {

    private int idContratto;
    private String nomeEnte;
    private float consumoMedioAnnuale;
    private float costoMedioUnitario;
    private Date dataSottoscrizione;
    private int durata;
    private float prezzoVendita;
    private int idAmministatore;

    public ContrattoBean() { }

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
    }

    public String getNomeEnte() {
        return nomeEnte;
    }

    public void setNomeEnte(final String nomeEnte) {
        this.nomeEnte = nomeEnte;
    }

    public float getConsumoMedioAnnuale() {
        return consumoMedioAnnuale;
    }

    public void setConsumoMedioAnnuale(final float consumoMedioAnnuale) {
        this.consumoMedioAnnuale = consumoMedioAnnuale;
    }

    public float getCostoMedioUnitario() {
        return costoMedioUnitario;
    }

    public void setCostoMedioUnitario(final float costoMedioUnitario) {
        this.costoMedioUnitario = costoMedioUnitario;
    }

    public Date getDataSottoscrizione() {
        return dataSottoscrizione;
    }

    public void setDataSottoscrizione(final Date dataSottoscrizione) {
        this.dataSottoscrizione = dataSottoscrizione;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(final int durata) {
        this.durata = durata;
    }

    public float getPrezzoVendita() {
        return prezzoVendita;
    }

    public void setPrezzoVendita(final float prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
    }

    public int getIdAmministatore() {
        return idAmministatore;
    }

    public void setIdAmministatore(final int idAmministatore) {
        this.idAmministatore = idAmministatore;
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

}
