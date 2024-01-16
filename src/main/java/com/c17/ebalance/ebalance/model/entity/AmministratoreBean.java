package com.c17.ebalance.ebalance.model.entity;
import java.sql.Date;

public class AmministratoreBean {

    private int idAmministratore;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String password;
    private boolean flagTipo;

    public AmministratoreBean() {
    }

    public AmministratoreBean(final int idAmministratore, final String nome, final String cognome, final Date dataNascita, final String email, final String password, final boolean flagTipo) {
        this.idAmministratore = idAmministratore;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.password = password;
        this.flagTipo = flagTipo;
    }

    public int getIdAmministratore() {
        return idAmministratore;
    }

    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(final String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(final Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean getFlagTipo() {
        return flagTipo;
    }
    public void setFlagTipo(final boolean flagTipo) {
        this.flagTipo = flagTipo;
    }

    @Override
    public String toString() {
        return "amministratoreBean{"
                + "idAmministratore=" + idAmministratore
                + ", nome='" + nome + '\''
                + ", cognome='" + cognome + '\''
                + ", dataNascita=" + dataNascita
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", flagTipo=" + flagTipo
                + '}';
    }

}
