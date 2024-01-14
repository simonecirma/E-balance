package com.c17.ebalance.ebalance.model.entity;
import java.sql.Date;
import java.time.LocalDate;

public class amministratoreBean {

    private int idAmministratore;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String password;
    private boolean flagTipo;

    public amministratoreBean() {}

    public amministratoreBean(int idAmministratore, String nome, String cognome,
                              Date dataNascita, String email,
                              String password, boolean flagTipo) {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getFlagTipo() {
        return flagTipo;
    }

    public void setFlagTipo(boolean flagTipo) {
        this.flagTipo = flagTipo;
    }

    @Override
    public String toString() {
        return "amministratoreBean{" +
                "idAmministratore=" + idAmministratore +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", flagTipo=" + flagTipo +
                '}';
    }

}
