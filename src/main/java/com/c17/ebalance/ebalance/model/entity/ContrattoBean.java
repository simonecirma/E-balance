package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code ContrattoBean} rappresenta un oggetto di tipo contratto archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ContrattoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();

    private int idContratto;
    private String nomeEnte;
    private float consumoMedioAnnuale;
    private float costoMedioUnitario;
    private Date dataSottoscrizione;
    private int durata;
    private float prezzoVendita;
    private int idAmministatore;

    /**
     * Costruttore di default.
     */
    public ContrattoBean() {
    }

    /**
     * Costruttore con parametri per inizializzare un oggetto ContrattoBean.
     *
     * @param idContratto          Identificatore del contratto.
     * @param nomeEnte             Nome dell'ente.
     * @param consumoMedioAnnuale  Consumo medio annuale.
     * @param costoMedioUnitario   Costo medio unitario.
     * @param dataSottoscrizione    Data di sottoscrizione del contratto.
     * @param durata               Durata del contratto in anni.
     * @param prezzoVendita        Prezzo di vendita.
     * @param idAmministatore      Identificatore dell'amministratore associato al contratto.
     */
    public ContrattoBean(final int idContratto, final String nomeEnte, final float consumoMedioAnnuale,
                         final float costoMedioUnitario, final Date dataSottoscrizione, final int durata,
                         final float prezzoVendita, final int idAmministatore) {
        this.idContratto = idContratto;
        this.nomeEnte = nomeEnte;
        this.consumoMedioAnnuale = consumoMedioAnnuale;
        this.costoMedioUnitario = costoMedioUnitario;
        this.dataSottoscrizione = dataSottoscrizione;
        this.durata = durata;
        this.prezzoVendita = prezzoVendita;
        this.idAmministatore = idAmministatore;
    }

    /**
     * Restituisce l'identificatore del contratto.
     *
     * @return Identificatore del contratto.
     */
    public int getIdContratto() {
        return idContratto;
    }

    /**
     * Imposta l'identificatore del contratto e notifica gli osservatori.
     *
     * @param idContratto Nuovo identificatore del contratto.
     */
    public void setIdContratto(final int idContratto) {
        this.idContratto = idContratto;
        notifyObservers("setIdContratto");
    }

    /**
     * Restituisce il nome dell'ente associato al contratto.
     *
     * @return Nome dell'ente.
     */
    public String getNomeEnte() {
        return nomeEnte;
    }

    /**
     * Imposta il nome dell'ente associato al contratto e notifica gli osservatori.
     *
     * @param nomeEnte Nuovo nome dell'ente.
     */
    public void setNomeEnte(final String nomeEnte) {
        this.nomeEnte = nomeEnte;
        notifyObservers("setNomeEnte");
    }

    /**
     * Restituisce il consumo medio annuale associato al contratto.
     *
     * @return Consumo medio annuale.
     */
    public float getConsumoMedioAnnuale() {
        return consumoMedioAnnuale;
    }

    /**
     * Imposta il consumo medio annuale associato al contratto e notifica gli osservatori.
     *
     * @param consumoMedioAnnuale Nuovo consumo medio annuale.
     */
    public void setConsumoMedioAnnuale(final float consumoMedioAnnuale) {
        this.consumoMedioAnnuale = consumoMedioAnnuale;
        notifyObservers("setConsumoMedioAnnuale");
    }

    /**
     * Restituisce il costo medio unitario associato al contratto.
     *
     * @return Costo medio unitario.
     */
    public float getCostoMedioUnitario() {
        return costoMedioUnitario;
    }

    /**
     * Imposta il costo medio unitario associato al contratto e notifica gli osservatori.
     *
     * @param costoMedioUnitario Nuovo costo medio unitario.
     */
    public void setCostoMedioUnitario(final float costoMedioUnitario) {
        this.costoMedioUnitario = costoMedioUnitario;
        notifyObservers("setCostoMedioUnitario");
    }

    /**
     * Restituisce la data di sottoscrizione del contratto.
     *
     * @return Data di sottoscrizione.
     */
    public Date getDataSottoscrizione() {
        return dataSottoscrizione;
    }

    /**
     * Imposta la data di sottoscrizione del contratto e notifica gli osservatori.
     *
     * @param dataSottoscrizione Nuova data di sottoscrizione.
     */
    public void setDataSottoscrizione(final Date dataSottoscrizione) {
        this.dataSottoscrizione = dataSottoscrizione;
        notifyObservers("setDataSottoscrizione");
    }

    /**
     * Restituisce la durata del contratto in anni.
     *
     * @return Durata del contratto in anni.
     */
    public int getDurata() {
        return durata;
    }

    /**
     * Imposta la durata del contratto in anni e notifica gli osservatori.
     *
     * @param durata Nuova durata del contratto in anni.
     */
    public void setDurata(final int durata) {
        this.durata = durata;
        notifyObservers("setDurata");
    }

    /**
     * Restituisce il prezzo di vendita associato al contratto.
     *
     * @return Prezzo di vendita.
     */
    public float getPrezzoVendita() {
        return prezzoVendita;
    }

    /**
     * Imposta il prezzo di vendita associato al contratto e notifica gli osservatori.
     *
     * @param prezzoVendita Nuovo prezzo di vendita.
     */
    public void setPrezzoVendita(final float prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
        notifyObservers("setPrezzoVendita");
    }

    /**
     * Restituisce l'identificatore dell'amministratore associato al contratto.
     *
     * @return Identificatore dell'amministratore.
     */
    public int getIdAmministatore() {
        return idAmministatore;
    }

    /**
     * Imposta l'identificatore dell'amministratore associato al contratto e notifica gli osservatori.
     *
     * @param idAmministatore Nuovo identificatore dell'amministratore.
     */
    public void setIdAmministatore(final int idAmministatore) {
        this.idAmministatore = idAmministatore;
        notifyObservers("setIdAmministatore");
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto ContrattoBean.
     *
     * @return Stringa che rappresenta l'oggetto ContrattoBean.
     */
    @Override
    public String toString() {
        return "ContrattoBean{"
                + "idContratto=" + idContratto
                + ", nomeEnte='" + nomeEnte + '\''
                + ", consumoMedioAnnuale=" + consumoMedioAnnuale
                + ", costoMedioUnitario=" + costoMedioUnitario
                + ", dataSottoscrizione=" + dataSottoscrizione
                + ", durata=" + durata
                + ", prezzoVendita=" + prezzoVendita
                + ", idAmministatore=" + idAmministatore
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
