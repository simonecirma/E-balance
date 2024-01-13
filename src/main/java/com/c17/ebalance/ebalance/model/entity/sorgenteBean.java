package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class sorgenteBean {
    private int idSorgente;
    private String tipologia;
    private Date dataInstallazione;
    private float produzioneAttuale;
    private boolean flagStatoSorgente;
    private boolean flagAttivazioneSorgente;

    public sorgenteBean() {}

    public sorgenteBean(int idSorgente, String tipologia, Date dataInstallazione, float produzioneAttuale,
                        boolean flagStatoSorgente, boolean flagAttivazioneSorgente) {
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

    public void setIdSorgente(int idSorgente) {
        this.idSorgente = idSorgente;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Date getDataInstallazione() {
        return dataInstallazione;
    }

    public void setDataInstallazione(Date dataInstallazione) {
        this.dataInstallazione = dataInstallazione;
    }

    public float getProduzioneAttuale() {
        return produzioneAttuale;
    }

    public void setProduzioneAttuale(float produzioneAttuale) {
        this.produzioneAttuale = produzioneAttuale;
    }

    public boolean isFlagStatoSorgente() {
        return flagStatoSorgente;
    }

    public void setFlagStatoSorgente(boolean flagStatoSorgente) {
        this.flagStatoSorgente = flagStatoSorgente;
    }

    public boolean isFlagAttivazioneSorgente() {
        return flagAttivazioneSorgente;
    }

    public void setFlagAttivazioneSorgente(boolean flagAttivazioneSorgente) {
        this.flagAttivazioneSorgente = flagAttivazioneSorgente;
    }

    @Override
    public String toString() {
        return "sorgenteBean{" +
                "idSorgente=" + idSorgente +
                ", tipologia='" + tipologia + '\'' +
                ", dataInstallazione=" + dataInstallazione +
                ", produzioneAttuale=" + produzioneAttuale +
                ", flagStatoSorgente=" + flagStatoSorgente +
                ", flagAttivazioneSorgente=" + flagAttivazioneSorgente +
                '}';
    }
}
