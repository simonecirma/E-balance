package com.c17.ebalance.ebalance.model.entity;


import java.util.Date;

public class ArchivioConsumoBean {
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
    }

    public Date getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(final Date dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    public float getConsumoGiornaliero() {
        return consumoGiornaliero;
    }

    public void setConsumoGiornaliero(final float consumoGiornaliero) {
        this.consumoGiornaliero = consumoGiornaliero;
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
}
