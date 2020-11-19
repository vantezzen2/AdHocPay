package io.vantezzen.adhocpay.controllers;

/**
 * Controller: Controller der App, welcher einen bestimmten Bereich kontrolliert
 */
public interface Controller {
    /**
     * Reagiere auf Änderungen in den Werten der Repositories
     */
    void onDataChange();
}
