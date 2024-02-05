package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code ArchivioProduzioneBean} rappresenta un oggetto di tipo produzione energetica archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ArchivioProduzioneBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idProduzione;
    private Date dataProduzione;
    private float produzioneGiornaliera;
    private int idSorgente;

    /**
     * Costruttore vuoto per la classe {@code ArchivioProduzioneBean}.
     */
    public ArchivioProduzioneBean() {
    }

    /**
     * Costruttore della classe {@code ArchivioProduzioneBean} con parametri.
     *
     * @param idProduzione          Identificativo della produzione energetica archiviata.
     * @param dataProduzione        Data della produzione energetica archiviata.
     * @param produzioneGiornaliera Produzione energetica giornaliera.
     * @param idSorgente            Identificativo della sorgente associata alla produzione energetica.
     */
    public ArchivioProduzioneBean(final int idProduzione, final Date dataProduzione,
                                  final float produzioneGiornaliera, final int idSorgente) {
        this.idProduzione = idProduzione;
        this.dataProduzione = dataProduzione;
        this.produzioneGiornaliera = produzioneGiornaliera;
        this.idSorgente = idSorgente;
    }

    /**
     * Restituisce l'identificativo della produzione energetica archiviata.
     *
     * @return Identificativo della produzione energetica archiviata.
     */
    public int getIdProduzione() {
        return idProduzione;
    }

    /**
     * Imposta l'identificativo della produzione energetica archiviata.
     *
     * @param idProduzione Nuovo identificativo della produzione energetica archiviata.
     */
    public void setIdProduzione(final int idProduzione) {
        this.idProduzione = idProduzione;
        notifyObservers("setIdProduzione");
    }

    /**
     * Restituisce la data della produzione energetica archiviata.
     *
     * @return Data della produzione energetica archiviata.
     */
    public Date getDataProduzione() {
        return dataProduzione;
    }

    /**
     * Imposta la data della produzione energetica archiviata.
     *
     * @param dataProduzione Nuova data della produzione energetica archiviata.
     */
    public void setDataProduzione(final Date dataProduzione) {
        this.dataProduzione = dataProduzione;
        notifyObservers("setDataProduzione");
    }

    /**
     * Restituisce la produzione energetica giornaliera.
     *
     * @return Produzione energetica giornaliera.
     */
    public float getProduzioneGiornaliera() {
        return produzioneGiornaliera;
    }

    /**
     * Imposta la produzione energetica giornaliera.
     *
     * @param produzioneGiornaliera Nuovo valore della produzione energetica giornaliera.
     */
    public void setProduzioneGiornaliera(final float produzioneGiornaliera) {
        this.produzioneGiornaliera = produzioneGiornaliera;
        notifyObservers("setProduzioneGiornaliera");
    }

    /**
     * Restituisce l'identificativo della sorgente associata alla produzione energetica.
     *
     * @return Identificativo della sorgente associata alla produzione energetica.
     */
    public int getIdSorgente() {
        return idSorgente;
    }

    /**
     * Imposta l'identificativo della sorgente associata alla produzione energetica.
     *
     * @param idSorgente Nuovo identificativo della sorgente.
     */
    public void setIdSorgente(final int idSorgente) {
        this.idSorgente = idSorgente;
        notifyObservers("setIdSorgente");
    }

    /**
     * Override del metodo toString che restituisce una rappresentazione testuale dell'oggetto.
     *
     * @return Stringa che rappresenta l'oggetto {@code ArchivioProduzioneBean}.
     */
    @Override
    public String toString() {
        return "ArchivioProduzioneBean{"
                + "idProduzione=" + idProduzione
                + ", dataProduzione=" + dataProduzione
                + ", produzioneGiornaliera=" + produzioneGiornaliera
                + ", idSorgente=" + idSorgente
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
