package com.c17.ebalance.ebalance.model.entity;
import java.util.Date;

public class ReportBean {
    private int idReport;
    private Date dataEmissione;
    private int idAmministratore;

    public ReportBean() { }

    public ReportBean(final int idReport, final Date dataEmissione,
                      final int idAmministratore) {
        this.idReport = idReport;
        this.dataEmissione = dataEmissione;
        this.idAmministratore = idAmministratore;
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

    @Override
    public String toString() {
        return "reportBean{"
                + "idReport=" + idReport
                + ", dataEmissione=" + dataEmissione
                + ", idAmministratore=" + idAmministratore
                + '}';
    }
}
