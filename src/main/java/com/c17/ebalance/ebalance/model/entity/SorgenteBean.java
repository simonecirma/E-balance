package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code SorgenteBean} rappresenta un oggetto contenente le informazioni di una sorgente di energia.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class SorgenteBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idSorgente;
    private String tipologia;
    private Date dataInstallazione;
    private float produzioneAttuale;
    private boolean flagStatoSorgente;
    private boolean flagAttivazioneSorgente;

    /**
     * Costruttore vuoto di default.
     */
    public SorgenteBean() {
    }

    /**
     * Costruttore che inizializza un oggetto {@code SorgenteBean} con le informazioni fornite.
     *
     * @param idSorgente               Identificatore della sorgente.
     * @param tipologia               Tipologia della sorgente.
     * @param dataInstallazione       Data di installazione della sorgente.
     * @param produzioneAttuale       Produzione attuale della sorgente.
     * @param flagStatoSorgente       Flag che rappresenta lo stato della sorgente.
     * @param flagAttivazioneSorgente Flag che rappresenta l'attivazione della sorgente.
     */
    public SorgenteBean(final int idSorgente, final String tipologia, final Date dataInstallazione,
                        final float produzioneAttuale, final boolean flagStatoSorgente,
                        final boolean flagAttivazioneSorgente) {
        this.idSorgente = idSorgente;
        this.tipologia = tipologia;
        this.dataInstallazione = dataInstallazione;
        this.produzioneAttuale = produzioneAttuale;
        this.flagStatoSorgente = flagStatoSorgente;
        this.flagAttivazioneSorgente = flagAttivazioneSorgente;
    }

    /**
     * Restituisce l'identificatore della sorgente.
     *
     * @return Identificatore della sorgente.
     */
    public int getIdSorgente() {
        return idSorgente;
    }

    /**
     * Imposta l'identificatore della sorgente e notifica gli osservatori.
     *
     * @param idSorgente Nuovo identificatore della sorgente.
     */
    public void setIdSorgente(final int idSorgente) {
        this.idSorgente = idSorgente;
        notifyObservers("setIdSorgente");
    }

    /**
     * Restituisce la tipologia della sorgente.
     *
     * @return Tipologia della sorgente.
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * Imposta la tipologia della sorgente e notifica gli osservatori.
     *
     * @param tipologia Nuova tipologia della sorgente.
     */
    public void setTipologia(final String tipologia) {
        this.tipologia = tipologia;
        notifyObservers("setTipologia");
    }

    /**
     * Restituisce la data di installazione della sorgente.
     *
     * @return Data di installazione della sorgente.
     */
    public Date getDataInstallazione() {
        return dataInstallazione;
    }

    /**
     * Imposta la data di installazione della sorgente e notifica gli osservatori.
     *
     * @param dataInstallazione Nuova data di installazione della sorgente.
     */
    public void setDataInstallazione(final Date dataInstallazione) {
        this.dataInstallazione = dataInstallazione;
        notifyObservers("setDataInstallazione");
    }

    /**
     * Restituisce la produzione attuale della sorgente.
     *
     * @return Produzione attuale della sorgente.
     */
    public float getProduzioneAttuale() {
        return produzioneAttuale;
    }

    /**
     * Imposta la produzione attuale della sorgente e notifica gli osservatori.
     *
     * @param produzioneAttuale Nuova produzione attuale della sorgente.
     */
    public void setProduzioneAttuale(final float produzioneAttuale) {
        this.produzioneAttuale = produzioneAttuale;
        notifyObservers("setProduzioneAttuale");
    }

    /**
     * Restituisce lo stato della sorgente.
     *
     * @return True se la sorgente è attiva, false altrimenti.
     */
    public boolean getFlagStatoSorgente() {
        return flagStatoSorgente;
    }

    /**
     * Imposta lo stato della sorgente e notifica gli osservatori.
     *
     * @param flagStatoSorgente Nuovo stato della sorgente.
     */
    public void setFlagStatoSorgente(final boolean flagStatoSorgente) {
        this.flagStatoSorgente = flagStatoSorgente;
        notifyObservers("setFlagStatoSorgente");
    }

    /**
     * Restituisce il flag di attivazione della sorgente.
     *
     * @return True se la sorgente è attivata, false altrimenti.
     */
    public boolean getFlagAttivazioneSorgente() {
        return flagAttivazioneSorgente;
    }

    /**
     * Imposta il flag di attivazione della sorgente e notifica gli osservatori.
     *
     * @param flagAttivazioneSorgente Nuovo flag di attivazione della sorgente.
     */
    public void setFlagAttivazioneSorgente(final boolean flagAttivazioneSorgente) {
        this.flagAttivazioneSorgente = flagAttivazioneSorgente;
        notifyObservers("setFlagAttivazioneSorgente");
    }

    /**
     * Restituisce una stringa rappresentante l'oggetto {@code SorgenteBean}.
     *
     * @return Stringa rappresentante l'oggetto.
     */
    @Override
    public String toString() {
        return "SorgenteBean{"
                + "idSorgente=" + idSorgente
                + ", tipologia='" + tipologia + '\''
                + ", dataInstallazione=" + dataInstallazione
                + ", produzioneAttuale=" + produzioneAttuale
                + ", flagStatoSorgente=" + flagStatoSorgente
                + ", flagAttivazioneSorgente=" + flagAttivazioneSorgente
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
