package com.c17.ebalance.ebalance.model.entity;
import java.util.Date;

public class ArchivioProduzioneBean {
    private int idProduzione;
    private Date dataProduzione;
    private float produzioneGiornaliera;
    private int idSorgente;

    public ArchivioProduzioneBean() { }

    public ArchivioProduzioneBean(final int idProduzione, final Date dataProduzione,
                                  final float produzioneGiornaliera, final int idSorgente) {
        this.idProduzione = idProduzione;
        this.dataProduzione = dataProduzione;
        this.produzioneGiornaliera = produzioneGiornaliera;
        this.idSorgente = idSorgente;
    }

    public int getIdProduzione() {
        return idProduzione;
    }

    public void setIdProduzione(final int idProduzione) {
        this.idProduzione = idProduzione;
    }

    public Date getDataProduzione() {
        return dataProduzione;
    }

    public void setDataProduzione(final Date dataProduzione) {
        this.dataProduzione = dataProduzione;
    }

    public float getProduzioneGiornaliera() {
        return produzioneGiornaliera;
    }

    public void setProduzioneGiornaliera(final float produzioneGiornaliera) {
        this.produzioneGiornaliera = produzioneGiornaliera;
    }

    @Override
    public String toString() {
        return "produzioneBean{"
                + "idProduzione=" + idProduzione
                + ", dataProduzione=" + dataProduzione
                + ", produzioneGiornaliera=" + produzioneGiornaliera
                + ", idSorgente=" + idSorgente
                + '}';
    }
}
