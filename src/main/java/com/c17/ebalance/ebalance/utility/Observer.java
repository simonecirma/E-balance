package com.c17.ebalance.ebalance.utility;

/**
 * L'interfaccia {@code Observer} rappresenta un oggetto che può osservare (ascoltare) gli oggetti osservabili.
 * Gli oggetti che implementano questa interfaccia saranno notificati in caso di cambiamenti negli oggetti osservati.
 */
public interface Observer {

    /**
     * Questo metodo viene chiamato quando l'oggetto osservato subisce un cambiamento.
     *
     * @param nomeMetodo Il nome del metodo che ha causato l'aggiornamento.
     */
    void update(String nomeMetodo);
}
