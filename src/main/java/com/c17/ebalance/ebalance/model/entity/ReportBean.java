package com.c17.ebalance.ebalance.model.entity;
import java.util.Date;

public class ReportBean {
    private int idReport;
    private Date dataEmissione;
    private int idAmministratore;
    private String nomeReport;

    public ReportBean() { }

    public ReportBean(final int idReport, final Date dataEmissione,
                      final int idAmministratore, String nomeReport) {
        this.idReport = idReport;
        this.dataEmissione = dataEmissione;
        this.idAmministratore = idAmministratore;
        this.nomeReport=nomeReport;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(final int idReport) {
        this.idReport = idReport;
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(final Date dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    public String getNomeReport() {
        return nomeReport;
    }
    public void setNomeReport(String nomeReport) {
        this.nomeReport = nomeReport;
    }

    @Override
    public String toString() {
        return "ReportBean{" +
                "idReport=" + idReport +
                ", dataEmissione=" + dataEmissione +
                ", idAmministratore=" + idAmministratore +
                ", nomeReport='" + nomeReport + '\'' +
                '}';
    }
}
