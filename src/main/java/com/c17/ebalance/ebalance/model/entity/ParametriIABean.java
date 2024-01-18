package com.c17.ebalance.ebalance.model.entity;

public class ParametriIABean {
    private int idParametro;
    private String piano;
    private boolean flagAttivazioneParametro;
    private int idAmministratore;

    public ParametriIABean() { }

    public ParametriIABean(final int idParametro, final String piano,
                           final boolean flagAttivazioneParametro, final int idAmministratore) {
        this.idParametro = idParametro;
        this.piano = piano;
        this.flagAttivazioneParametro = flagAttivazioneParametro;
        this.idAmministratore = idAmministratore;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(final int idParametro) {
        this.idParametro = idParametro;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(final String piano) {
        this.piano = piano;
    }

    public boolean getFlagAttivazioneParametro() {
        return flagAttivazioneParametro;
    }

    public void setFlagAttivazioneParametro(final boolean flagAttivazioneParametro) {
        this.flagAttivazioneParametro = flagAttivazioneParametro;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(final int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    @Override
    public String toString() {
        return "parametriIABean{"
                + "idParametro=" + idParametro
                + ", piano='" + piano + '\''
                + ", flagAttivazioneParametro=" + flagAttivazioneParametro
                + ", idAmministratore=" + idAmministratore
                + '}';
    }
}
