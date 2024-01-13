package com.c17.ebalance.ebalance.model.entity;

public class condizioneMeteoBean {
    private String condizione;

    public condizioneMeteoBean() {}

    public condizioneMeteoBean(String condizione) {
        this.condizione = condizione;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    @Override
    public String toString() {
        return "condizioneMeteoBean{" +
                "condizione='" + condizione + '\'' +
                '}';
    }
}
