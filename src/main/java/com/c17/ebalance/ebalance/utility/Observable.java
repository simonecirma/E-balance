package com.c17.ebalance.ebalance.utility;

public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(String nomeMetodo);
}
