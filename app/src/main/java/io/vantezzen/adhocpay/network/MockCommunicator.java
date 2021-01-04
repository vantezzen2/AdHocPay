package io.vantezzen.adhocpay.network;

import org.mockito.Mockito;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.network.asap.ASAPApp;

/**
 * The type Mock communicator.
 */
public class MockCommunicator implements NetworkCommunicator {
    /**
     * The Last sent transaction.
     */
    Transaction lastSentTransaction = null;

    /**
     * The Response.
     */
    boolean response = true;

    @Override
    public void setup() {
        // Tue nichts
    }

    @Override
    public ASAPApp getASAPApp() {
        ASAPApp mock = Mockito.mock(ASAPApp.class);

        return mock;
    }

    @Override
    public boolean sendTransaction(Transaction t) {
        lastSentTransaction = t;
        return response;
    }

    @Override
    public void setControllerManager(ControllerManager c) {
        // Tue nichts
    }

    /**
     * Liefert die letzte Transaktion, welche gesendet wurde
     *
     * @return Transaktion last sent transaction
     */
    public Transaction getLastSentTransaction() {
        return lastSentTransaction;
    }

    /**
     * Setze, welche Rückmeldung geliefert werden soll, wenn eine Nachricht gesendet wird
     *
     * @param response Antwort
     */
    public void setResponse(boolean response) {
        this.response = response;
    }
}
