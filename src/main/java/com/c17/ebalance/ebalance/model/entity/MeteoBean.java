package com.c17.ebalance.ebalance.model.entity;
import java.sql.Time;
import java.util.Date;

public class MeteoBean {
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
    }

    public Date getDataRilevazione() {
        return dataRilevazione;
    }

    public void setDataRilevazione(final Date dataRilevazione) {
        this.dataRilevazione = dataRilevazione;
    }

    public Time getOraRilevazione() {
        return oraRilevazione;
    }

    public void setOraRilevazione(final Time oraRilevazione) {
        this.oraRilevazione = oraRilevazione;
    }

    public float getVelocitaVento() {
        return velocitaVento;
    }

    public void setVelocitaVento(final float velocitaVento) {
        this.velocitaVento = velocitaVento;
    }

    public int getProbabilitaPioggia() {
        return probabilitaPioggia;
    }

    public void setProbabilitaPioggia(final int probabilitaPioggia) {
        this.probabilitaPioggia = probabilitaPioggia;
    }

    public String getCondizioniMetereologiche() {
        return condizioniMetereologiche;
    }

    public void setCondizioniMetereologiche(final String condizioniMetereologiche) {
        this.condizioniMetereologiche = condizioniMetereologiche;
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
}
