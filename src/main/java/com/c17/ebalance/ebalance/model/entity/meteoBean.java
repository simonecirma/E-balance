package com.c17.ebalance.ebalance.model.entity;
import java.sql.Time;
import java.util.*;

public class meteoBean {
    private int idMeteo;
    private Date dataRilevazione;
    private Time oraRilevazione;
    private float velocitaVento;
    private int probabilitaPioggia;
    private String condizioniMetereologiche;

    public meteoBean() {}

    public meteoBean(int idMeteo, Date dataRilevazione, Time oraRilevazione, float velocitaVento, int probabilitaPioggia,
                     String condizioniMetereologiche) {
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

    public void setIdMeteo(int idMeteo) {
        this.idMeteo = idMeteo;
    }

    public Date getDataRilevazione() {
        return dataRilevazione;
    }

    public void setDataRilevazione(Date dataRilevazione) {
        this.dataRilevazione = dataRilevazione;
    }

    public Time getOraRilevazione() {
        return oraRilevazione;
    }

    public void setOraRilevazione(Time oraRilevazione) {
        this.oraRilevazione = oraRilevazione;
    }

    public float getVelocitaVento() {
        return velocitaVento;
    }

    public void setVelocitaVento(float velocitaVento) {
        this.velocitaVento = velocitaVento;
    }

    public int getProbabilitaPioggia() {
        return probabilitaPioggia;
    }

    public void setProbabilitaPioggia(int probabilitaPioggia) {
        this.probabilitaPioggia = probabilitaPioggia;
    }

    public String getCondizioniMetereologiche() {
        return condizioniMetereologiche;
    }

    public void setCondizioniMetereologiche(String condizioniMetereologiche) {
        this.condizioniMetereologiche = condizioniMetereologiche;
    }

    @Override
    public String toString() {
        return "meteoBean{" +
                "idMeteo=" + idMeteo +
                ", dataRilevazione=" + dataRilevazione +
                ", oraRilevazione=" + oraRilevazione +
                ", velocitaVento=" + velocitaVento +
                ", probabilitaPioggia=" + probabilitaPioggia +
                ", condizioniMetereologiche='" + condizioniMetereologiche + '\'' +
                '}';
    }
}
