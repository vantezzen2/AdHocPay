package io.vantezzen.adhocpay.controllers;

/**
 * ControllerManager: Verwalte die Controller der App
 */
public interface ControllerManager {
    /**
     * Liefert den aktuellen UserController
     *
     * @return UserController
     */
    UserController getUserController();

    /**
     * Liefert den aktuellen TransactionController
     *
     * @return TransactionController
     */
    TransactionController getTransactionController();

    /**
     * Methode, welche aufgerufen wird, wenn sich Daten ändern.
     * Dies sollte die einzelnen Controller informieren
     */
    void onDataChange();
}
