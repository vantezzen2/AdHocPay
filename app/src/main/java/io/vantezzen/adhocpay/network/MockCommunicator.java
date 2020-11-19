package io.vantezzen.adhocpay.network;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.Transaction;

public class MockCommunicator implements NetworkCommunicator {
    Transaction lastSentTransaction = null;

    boolean response = true;

    @Override
    public void setup() {
        // Tue nichts
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
     * @return Transaktion
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
