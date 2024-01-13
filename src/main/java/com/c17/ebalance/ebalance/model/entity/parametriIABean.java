package com.c17.ebalance.ebalance.model.entity;

public class parametriIABean {
    private int idParametro;
    private String piano;
    private boolean flagAttivazioneParametro;
    private int idAmministratore;

    public parametriIABean() {}

    public parametriIABean(int idParametro, String piano, boolean flagAttivazioneParametro, int idAmministratore) {
        this.idParametro = idParametro;
        this.piano = piano;
        this.flagAttivazioneParametro = flagAttivazioneParametro;
        this.idAmministratore = idAmministratore;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public boolean isFlagAttivazioneParametro() {
        return flagAttivazioneParametro;
    }

    public void setFlagAttivazioneParametro(boolean flagAttivazioneParametro) {
        this.flagAttivazioneParametro = flagAttivazioneParametro;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    @Override
    public String toString() {
        return "parametriIABean{" +
                "idParametro=" + idParametro +
                ", piano='" + piano + '\'' +
                ", flagAttivazioneParametro=" + flagAttivazioneParametro +
                ", idAmministratore=" + idAmministratore +
                '}';
    }
}
