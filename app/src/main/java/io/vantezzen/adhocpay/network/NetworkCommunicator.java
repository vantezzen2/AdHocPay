package io.vantezzen.adhocpay.network;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.controllers.Controller;
import io.vantezzen.adhocpay.network.asap.ASAPApp;

/**
 * Network Communicator: Kommuniziere mit dem Netzwerk, um Transaktionen auszutauschen
 */
public interface NetworkCommunicator {
    /**
     * Starte das Netwerk-Interface.
     * Dies benötigt im Fall von ASAPCommunication, dass wir bereits auf einer ASAPActivity
     * sind.
     */
    void setup();

    /**
     * Liefert die ASAP App Instanz
     *
     * @return ASAP App
     */
    ASAPApp getASAPApp();

    /**
     * Sende eine Transaktion an das Netwerk
     *
     * @param t Transaktion, welche gesendet werden soll
     * @return Ob die Transaktion erfolgreich gesendet wurde
     * @throws NullPointerException the null pointer exception
     */
    boolean sendTransaction(Transaction t) throws NullPointerException;

    /**
     * Setze den ControllerManager, welcher bei Nachrichten informiert werden soll
     *
     * @param c ControllerManager
     * @throws NullPointerException the null pointer exception
     */
    void setControllerManager(ControllerManager c) throws NullPointerException;
}
