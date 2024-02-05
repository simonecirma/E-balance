package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code ArchivioConsumoBean} rappresenta un oggetto di tipo consumo energetico archiviato.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class ArchivioConsumoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();
    private int idConsumo;
    private Date dataConsumo;
    private float consumoGiornaliero;
    private int idEdificio;

    /**
     * Costruttore vuoto per la classe {@code ArchivioConsumoBean}.
     */
    public ArchivioConsumoBean() {
    }

    /**
     * Costruttore della classe {@code ArchivioConsumoBean} con parametri.
     *
     * @param idConsumo         Identificativo del consumo energetico archiviato.
     * @param dataConsumo       Data del consumo energetico archiviato.
     * @param consumoGiornaliero Consumo energetico giornaliero.
     * @param idEdificio        Identificativo dell'edificio associato al consumo energetico.
     */
    public ArchivioConsumoBean(final int idConsumo, final Date dataConsumo,
                               final float consumoGiornaliero, final int idEdificio) {
        this.idConsumo = idConsumo;
        this.dataConsumo = dataConsumo;
        this.consumoGiornaliero = consumoGiornaliero;
        this.idEdificio = idEdificio;
    }

    /**
     * Restituisce l'identificativo del consumo energetico archiviato.
     *
     * @return Identificativo del consumo energetico archiviato.
     */
    public int getIdConsumo() {
        return idConsumo;
    }

    /**
     * Imposta l'identificativo del consumo energetico archiviato.
     *
     * @param idConsumo Nuovo identificativo del consumo energetico archiviato.
     */
    public void setIdConsumo(final int idConsumo) {
        this.idConsumo = idConsumo;
        notifyObservers("setIdConsumo");
    }

    /**
     * Restituisce la data del consumo energetico archiviato.
     *
     * @return Data del consumo energetico archiviato.
     */
    public Date getDataConsumo() {
        return dataConsumo;
    }

    /**
     * Imposta la data del consumo energetico archiviato.
     *
     * @param dataConsumo Nuova data del consumo energetico archiviato.
     */
    public void setDataConsumo(final Date dataConsumo) {
        this.dataConsumo = dataConsumo;
        notifyObservers("setDataConsumo");
    }

    /**
     * Restituisce il consumo energetico giornaliero.
     *
     * @return Consumo energetico giornaliero.
     */
    public float getConsumoGiornaliero() {
        return consumoGiornaliero;
    }

    /**
     * Imposta il consumo energetico giornaliero.
     *
     * @param consumoGiornaliero Nuovo valore del consumo energetico giornaliero.
     */
    public void setConsumoGiornaliero(final float consumoGiornaliero) {
        this.consumoGiornaliero = consumoGiornaliero;
        notifyObservers("setConsumoGiornaliero");
    }

    /**
     * Restituisce l'identificativo dell'edificio associato al consumo energetico.
     *
     * @return Identificativo dell'edificio associato al consumo energetico.
     */
    public int getIdEdificio() {
        return idEdificio;
    }

    /**
     * Imposta l'identificativo dell'edificio associato al consumo energetico.
     *
     * @param idEdificio Nuovo identificativo dell'edificio.
     */
    public void setIdEdificio(final int idEdificio) {
        this.idEdificio = idEdificio;
        notifyObservers("setIdEdificio");
    }

    /**
     * Override del metodo toString che restituisce una rappresentazione testuale dell'oggetto.
     *
     * @return Stringa che rappresenta l'oggetto {@code ArchivioConsumoBean}.
     */
    @Override
    public String toString() {
        return "ArchivioConsumoBean{"
                + "idConsumo=" + idConsumo
                + ", dataConsumo=" + dataConsumo
                + ", consumoGiornaliero=" + consumoGiornaliero
                + ", idEdificio=" + idEdificio
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
