package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AmministratoreBean implements Observable {

    private static List<Observer> observers = new ArrayList<>();

    private int idAmministratore;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String password;
    private boolean flagTipo;

    public AmministratoreBean() {
    }

    public AmministratoreBean(final int idAmministratore, final String nome,
                              final String cognome, final Date dataNascita,
                              final String email, final String password,
                              final boolean flagTipo) {
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
        notifyObservers("setIdAmministratore");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
        notifyObservers("setNome");
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(final String cognome) {
        this.cognome = cognome;
        notifyObservers("setCognome");
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(final Date dataNascita) {
        this.dataNascita = dataNascita;
        notifyObservers("setDataNascita");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
        notifyObservers("setEmail");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
        notifyObservers("setPassword");
    }

    public boolean getFlagTipo() {
        return flagTipo;
    }

    public void setFlagTipo(final boolean flagTipo) {
        this.flagTipo = flagTipo;
        notifyObservers("setFlagTipo");
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

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }

}
