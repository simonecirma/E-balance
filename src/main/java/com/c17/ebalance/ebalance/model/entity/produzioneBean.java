package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class produzioneBean {
    private int idProduzione;
    private Date dataProduzione;
    private float produzioneGiornaliera;
    private int idSorgente;

    public produzioneBean() {}

    public produzioneBean(int idProduzione, Date dataProduzione, float produzioneGiornaliera, int idSorgente) {
        this.idProduzione = idProduzione;
        this.dataProduzione = dataProduzione;
        this.produzioneGiornaliera = produzioneGiornaliera;
        this.idSorgente = idSorgente;
    }

    public int getIdProduzione() {
        return idProduzione;
    }

    public void setIdProduzione(int idProduzione) {
        this.idProduzione = idProduzione;
    }

    public Date getDataProduzione() {
        return dataProduzione;
    }

    public void setDataProduzione(Date dataProduzione) {
        this.dataProduzione = dataProduzione;
    }

    public float getProduzioneGiornaliera() {
        return produzioneGiornaliera;
    }

    public void setProduzioneGiornaliera(float produzioneGiornaliera) {
        this.produzioneGiornaliera = produzioneGiornaliera;
    }

    @Override
    public String toString() {
        return "produzioneBean{" +
                "idProduzione=" + idProduzione +
                ", dataProduzione=" + dataProduzione +
                ", produzioneGiornaliera=" + produzioneGiornaliera +
                ", idSorgente=" + idSorgente +
                '}';
    }
}
