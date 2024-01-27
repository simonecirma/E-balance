package com.c17.ebalance.ebalance.model.entity;
import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportBean implements Observable  {
    private static List<Observer> observers = new ArrayList<>();
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

    public void setIdReport(int idReport) {
        this.idReport = idReport;
        notifyObservers("setIdReport");
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
        notifyObservers("setDataEmissione");
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    public String getNomeReport() {
        return nomeReport;
    }
    public void setNomeReport(String nomeReport) {
        this.nomeReport = nomeReport;
        notifyObservers("setNomeReport");
    }

    @Override
    public String toString() {
        return "ReportBean{"
               + "idReport=" + idReport
               + ", dataEmissione=" + dataEmissione
               + ", idAmministratore=" + idAmministratore
               + ", nomeReport='" + nomeReport + '\''
               + '}';
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }
}
