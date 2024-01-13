package com.c17.ebalance.ebalance.model.entity;
import java.util.*;

public class reportBean {
    private int idReport;
    private Date dataEmissione;
    private int idAmministratore;

    public reportBean() {}

    public reportBean(int idReport, Date dataEmissione, int idAmministratore) {
        this.idReport = idReport;
        this.dataEmissione = dataEmissione;
        this.idAmministratore = idAmministratore;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    @Override
    public String toString() {
        return "reportBean{" +
                "idReport=" + idReport +
                ", dataEmissione=" + dataEmissione +
                ", idAmministratore=" + idAmministratore +
                '}';
    }
}
