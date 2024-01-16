package com.c17.ebalance.ebalance.model.entity;

public class CondizioneMeteoBean {
    private String condizione;

    public CondizioneMeteoBean() { }

    public CondizioneMeteoBean(final String condizione) {
        this.condizione = condizione;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(final String condizione) {
        this.condizione = condizione;
    }

    @Override
    public String toString() {
        return "condizioneMeteoBean{"
                + "condizione='" + condizione + '\''
                + '}';
    }
}
