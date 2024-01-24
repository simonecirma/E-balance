package com.c17.ebalance.ebalance.model.entity;

public class BatteriaBean {
    private int idBatteria;
    private boolean flagStatoBatteria;
    private float capacitaMax;
    private float percentualeCarica;

    public BatteriaBean() { }

    public BatteriaBean(final int idBatteria, final boolean flagStatoBatteria,
                        final float capacitaMax, final float percentualeCarica) {
        this.idBatteria = idBatteria;
        this.flagStatoBatteria = flagStatoBatteria;
        this.capacitaMax = capacitaMax;
        this.percentualeCarica = percentualeCarica;
    }

    public int getIdBatteria() {
        return idBatteria;
    }

    public void setIdBatteria(final int idBatteria) {
        this.idBatteria = idBatteria;
    }

    public boolean getFlagStatoBatteria() {
        return flagStatoBatteria;
    }

    public void setFlagStatoBatteria(final boolean flagStatoBatteria) {
        this.flagStatoBatteria = flagStatoBatteria;
    }

    public float getCapacitaMax() {
        return capacitaMax;
    }

    public void setCapacitaMax(final float capacitaMax) {
        this.capacitaMax = capacitaMax;
    }

    public float getPercentualeCarica() {
        return percentualeCarica;
    }

    public void setPercentualeCarica(final float percentualeCarica) {
        this.percentualeCarica = percentualeCarica;
    }

    @Override
    public String toString() {
        return "batteriaBean{"
                + "idBatteria=" + idBatteria
                + ", flagStatoBatteria=" + flagStatoBatteria
                + ", capacitaMax=" + capacitaMax
                + ", percentualeCarica=" + percentualeCarica
                + '}';
    }
}
