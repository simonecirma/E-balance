package com.c17.ebalance.ebalance.model.entity;

public class InteragisceBean {
    private int idParametro;
    private String tipoSorgente;
    private boolean flagPreferenzaSorgente;
    private int percentualeUtilizzoSorgente;
    private int prioritaSorgente;

    public InteragisceBean() { }

    public InteragisceBean(final int idParametro, final String tipoSorgente,
                           final boolean flagPreferenzaSorgente,
                           final int percentualeUtilizzoSorgente, final int prioritaSorgente) {
        this.idParametro = idParametro;
        this.tipoSorgente = tipoSorgente;
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
        this.prioritaSorgente = prioritaSorgente;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(final int idParametro) {
        this.idParametro = idParametro;
    }

    public String getTipoSorgente() {
        return tipoSorgente;
    }

    public void setTipoSorgente(final String tipoSorgente) {
        this.tipoSorgente = tipoSorgente;
    }

    public boolean getFlagPreferenzaSorgente() {
        return flagPreferenzaSorgente;
    }

    public void setFlagPreferenzaSorgente(final boolean flagPreferenzaSorgente) {
        this.flagPreferenzaSorgente = flagPreferenzaSorgente;
    }

    public int getPercentualeUtilizzoSorgente() {
        return percentualeUtilizzoSorgente;
    }

    public void setPercentualeUtilizzoSorgente(final int percentualeUtilizzoSorgente) {
        this.percentualeUtilizzoSorgente = percentualeUtilizzoSorgente;
    }

    public int getPrioritaSorgente() {
        return prioritaSorgente;
    }

    public void setPrioritaSorgente(final int prioritaSorgente) {
        this.prioritaSorgente = prioritaSorgente;
    }

    @Override
    public String toString() {
        return "interagisceBean{"
                + "idParametro=" + idParametro
                + ", tipoSorgente='" + tipoSorgente + '\''
                + ", flagPreferenzaSorgente=" + flagPreferenzaSorgente
                + ", percentualeUtilizzoSorgente=" + percentualeUtilizzoSorgente
                + ", prioritaSorgente=" + prioritaSorgente
                + '}';
    }
}
