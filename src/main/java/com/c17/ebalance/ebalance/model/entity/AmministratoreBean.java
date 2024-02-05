package com.c17.ebalance.ebalance.model.entity;

import com.c17.ebalance.ebalance.utility.Observable;
import com.c17.ebalance.ebalance.utility.Observer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code AmministratoreBean} rappresenta un oggetto di tipo amministratore.
 * Implementa l'interfaccia {@link Observable} per supportare la notifica degli osservatori.
 */
public class AmministratoreBean implements Observable {

    private static List<Observer> observers = new ArrayList<>();

    private int idAmministratore;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String password;
    private boolean flagTipo;

    /**
     * Costruttore vuoto per la classe {@code AmministratoreBean}.
     */
    public AmministratoreBean() {
    }

    /**
     * Costruttore della classe {@code AmministratoreBean} con parametri.
     *
     * @param idAmministratore Identificativo dell'amministratore.
     * @param nome             Nome dell'amministratore.
     * @param cognome          Cognome dell'amministratore.
     * @param dataNascita      Data di nascita dell'amministratore.
     * @param email            Indirizzo email dell'amministratore.
     * @param password         Password dell'amministratore.
     * @param flagTipo         Flag che indica il tipo di amministratore.
     */
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

    /**
     * Restituisce l'identificativo dell'amministratore.
     *
     * @return Identificativo dell'amministratore.
     */
    public int getIdAmministratore() {
        return idAmministratore;
    }

    /**
     * Imposta l'identificativo dell'amministratore.
     *
     * @param idAmministratore Nuovo identificativo dell'amministratore.
     */
    public void setIdAmministratore(int idAmministratore) {
        this.idAmministratore = idAmministratore;
        notifyObservers("setIdAmministratore");
    }

    /**
     * Restituisce il nome dell'amministratore.
     *
     * @return Nome dell'amministratore.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'amministratore.
     *
     * @param nome Nuovo nome dell'amministratore.
     */
    public void setNome(final String nome) {
        this.nome = nome;
        notifyObservers("setNome");
    }

    /**
     * Restituisce il cognome dell'amministratore.
     *
     * @return Cognome dell'amministratore.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'amministratore.
     *
     * @param cognome Nuovo cognome dell'amministratore.
     */
    public void setCognome(final String cognome) {
        this.cognome = cognome;
        notifyObservers("setCognome");
    }

    /**
     * Restituisce la data di nascita dell'amministratore.
     *
     * @return Data di nascita dell'amministratore.
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta la data di nascita dell'amministratore.
     *
     * @param dataNascita Nuova data di nascita dell'amministratore.
     */
    public void setDataNascita(final Date dataNascita) {
        this.dataNascita = dataNascita;
        notifyObservers("setDataNascita");
    }

    /**
     * Restituisce l'indirizzo email dell'amministratore.
     *
     * @return Indirizzo email dell'amministratore.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email dell'amministratore.
     *
     * @param email Nuovo indirizzo email dell'amministratore.
     */
    public void setEmail(final String email) {
        this.email = email;
        notifyObservers("setEmail");
    }

    /**
     * Restituisce la password dell'amministratore.
     *
     * @return Password dell'amministratore.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'amministratore.
     *
     * @param password Nuova password dell'amministratore.
     */
    public void setPassword(final String password) {
        this.password = password;
        notifyObservers("setPassword");
    }

    /**
     * Restituisce il flag che indica il tipo di amministratore.
     *
     * @return True se l'amministratore è di tipo "admin", false se è di tipo "user".
     */
    public boolean getFlagTipo() {
        return flagTipo;
    }

    /**
     * Imposta il flag che indica il tipo di amministratore.
     *
     * @param flagTipo Nuovo valore del flag.
     */
    public void setFlagTipo(final boolean flagTipo) {
        this.flagTipo = flagTipo;
        notifyObservers("setFlagTipo");
    }

    /**
     * Override del metodo toString che restituisce una rappresentazione testuale dell'oggetto.
     *
     * @return Stringa che rappresenta l'oggetto {@code AmministratoreBean}.
     */
    @Override
    public String toString() {
        return "AmministratoreBean{"
                + "idAmministratore=" + idAmministratore
                + ", nome='" + nome + '\''
                + ", cognome='" + cognome + '\''
                + ", dataNascita=" + dataNascita
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", flagTipo=" + flagTipo
                + '}';
    }

    /**
     * Aggiunge un osservatore alla lista degli osservatori.
     *
     * @param observer Osservatore da aggiungere.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un osservatore dalla lista degli osservatori.
     *
     * @param observer Osservatore da rimuovere.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori chiamando il loro metodo di aggiornamento.
     *
     * @param nomeMetodo Nome del metodo che ha generato la notifica.
     */
    @Override
    public void notifyObservers(String nomeMetodo) {
        for (Observer observer : observers) {
            observer.update(nomeMetodo);
        }
    }
}
