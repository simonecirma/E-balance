package com.c17.ebalance.ebalance.model.entity;

public class ConsumoEdificioBean {
    private int idEdificio;
    private String nomeEdificio;
    private float consumoAttuale;

    public ConsumoEdificioBean() { }

    public ConsumoEdificioBean(final int idEdificio, final String nomeEdificio,
                               final float consumoAttuale) {
        this.idEdificio = idEdificio;
        this.nomeEdificio = nomeEdificio;
        this.consumoAttuale = consumoAttuale;
    }

    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(final int idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getNomeEdificio() {
        return nomeEdificio;
    }

    public void setNomeEdificio(final String nomeEdificio) {
        this.nomeEdificio = nomeEdificio;
    }

    public float getConsumoAttuale() {
        return consumoAttuale;
    }

    public void setConsumoAttuale(final float consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
    }

    @Override
    public String toString() {
        return "consumoEdificioBean{"
                + "idEdificio=" + idEdificio
                + ", nomeEdificio='" + nomeEdificio + '\''
                + ", consumoAttuale=" + consumoAttuale
                + '}';
    }
}
