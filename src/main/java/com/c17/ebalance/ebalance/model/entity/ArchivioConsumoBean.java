package com.c17.ebalance.ebalance.model.entity;


import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArchivioConsumoBean implements Observable {
    private static List<Observer> observers = new ArrayList<>();


    private int idConsumo;
    private Date dataConsumo;
    private float consumoGiornaliero;
    private int idEdificio;

    public ArchivioConsumoBean() { }

    public ArchivioConsumoBean(final int idConsumo, final Date dataConsumo,
                               final float consumoGiornaliero, final int idEdificio) {
        this.idConsumo = idConsumo;
        this.dataConsumo = dataConsumo;
        this.consumoGiornaliero = consumoGiornaliero;
        this.idEdificio = idEdificio;
    }

    public int getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(final int idConsumo) {
        this.idConsumo = idConsumo;
        notifyObservers("setIdConsumo");
    }

    public Date getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(final Date dataConsumo) {
        this.dataConsumo = dataConsumo;
        notifyObservers("setDataConsumo");
    }

    public float getConsumoGiornaliero() {
        return consumoGiornaliero;
    }

    public void setConsumoGiornaliero(final float consumoGiornaliero) {
        this.consumoGiornaliero = consumoGiornaliero;
        notifyObservers("setConsumoGiornaliero");
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(final int idEdificio) {
        this.idEdificio = idEdificio;
    }

    @Override
    public String toString() {
        return "consumoBean{"
                + "idConsumo=" + idConsumo
                + ", dataConsumo=" + dataConsumo
                + ", consumoGiornaliero=" + consumoGiornaliero
                + ", idEdificio=" + idEdificio
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
