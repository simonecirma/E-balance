package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code ReportBean} rappresenta un oggetto contenente le informazioni di un report.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ReportBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idReport;
    private Date dataEmissione;
    private int idAmministratore;
    private String nomeReport;

    /**
     * Costruttore vuoto di default.
     */
    public ReportBean() {
    }

    /**
     * Costruttore che inizializza un oggetto {@code ReportBean} con le informazioni fornite.
     *
     * @param idReport          Identificatore del report.
     * @param dataEmissione     Data di emissione del report.
     * @param idAmministratore  Identificatore dell'amministratore associato al report.
     * @param nomeReport        Nome del report.
     */
    public ReportBean(final int idReport, final Date dataEmissione,
                      final int idAmministratore, String nomeReport) {
        this.idReport = idReport;
        this.dataEmissione = dataEmissione;
        this.idAmministratore = idAmministratore;
        this.nomeReport = nomeReport;
    }

    /**
     * Restituisce l'identificatore del report.
     *
     * @return Identificatore del report.
     */
    public int getIdReport() {
        return idReport;
    }

    /**
     * Imposta l'identificatore del report e notifica gli osservatori.
     *
     * @param idReport Nuovo identificatore del report.
     */
    public void setIdReport(int idReport) {
        this.idReport = idReport;
        notifyObservers("setIdReport");
    }

    /**
     * Restituisce la data di emissione del report.
     *
     * @return Data di emissione del report.
     */
    public Date getDataEmissione() {
        return dataEmissione;
    }

    /**
     * Imposta la data di emissione del report e notifica gli osservatori.
     *
     * @param dataEmissione Nuova data di emissione del report.
     */
    public void setDataEmissione(Date dataEmissione) {
        this.dataEmissione = dataEmissione;
        notifyObservers("setDataEmissione");
    }

    /**
     * Restituisce l'identificatore dell'amministratore associato al report.
     *
     * @return Identificatore dell'amministratore associato al report.
     */
    public int getIdAmministratore() {
        return idAmministratore;
    }

    /**
     * Imposta l'identificatore dell'amministratore associato al report e notifica gli osservatori.
     *
     * @param idAmministratore Nuovo identificatore dell'amministratore associato al report.
     */
    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    /**
     * Restituisce il nome del report.
     *
     * @return Nome del report.
     */
    public String getNomeReport() {
        return nomeReport;
    }

    /**
     * Imposta il nome del report e notifica gli osservatori.
     *
     * @param nomeReport Nuovo nome del report.
     */
    public void setNomeReport(String nomeReport) {
        this.nomeReport = nomeReport;
        notifyObservers("setNomeReport");
    }

    /**
     * Restituisce una stringa rappresentante l'oggetto {@code ReportBean}.
     *
     * @return Stringa rappresentante l'oggetto.
     */
    @Override
    public String toString() {
        return "ReportBean{"
                + "idReport=" + idReport
                + ", dataEmissione=" + dataEmissione
                + ", idAmministratore=" + idAmministratore
                + ", nomeReport='" + nomeReport + '\''
                + '}';
    }

    /**
     * Aggiunge un osservatore alla lista degli osservatori.
     *
     * @param observer Osservatore da aggiungere.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un osservatore dalla lista degli osservatori.
     *
     * @param observer Osservatore da rimuovere.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori chiamando il loro metodo di aggiornamento.
     *
     * @param nomeMetodo Nome del metodo che ha generato la notifica.
     */
    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }
}
