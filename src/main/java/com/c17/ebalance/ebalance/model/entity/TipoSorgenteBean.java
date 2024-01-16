package com.c17.ebalance.ebalance.model.entity;

public class TipoSorgenteBean {
    private String tipo;

    public TipoSorgenteBean() { }

    public TipoSorgenteBean(final String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "tipoSorgenteBean{"
                + "tipo='" + tipo + '\''
                + '}';
    }
}
