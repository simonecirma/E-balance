package com.c17.ebalance.ebalance.utility;

/**
 * L'interfaccia {@code Observable} rappresenta un oggetto che può essere osservato da altri oggetti (osservatori).
 * Gli oggetti che implementano questa interfaccia possono gestire una lista di osservatori e notificarli
 * riguardo a cambiamenti di stato.
 */
public interface Observable {

    /**
     * Aggiunge un osservatore alla lista di osservatori.
     *
     * @param observer L'osservatore da aggiungere.
     */
    void addObserver(Observer observer);

    /**
     * Rimuove un osservatore dalla lista di osservatori.
     *
     * @param observer L'osservatore da rimuovere.
     */
    void removeObserver(Observer observer);

    /**
     * Notifica gli osservatori riguardo a un cambiamento specificato.
     *
     * @param nomeMetodo Il nome del metodo che ha causato la notifica.
     */
    void notifyObservers(String nomeMetodo);
}
