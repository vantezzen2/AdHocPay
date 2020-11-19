package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.application.Manager;

/**
 * BaseController: Definiere allgemeine Methoden und Eigenschaften aller Controller
 */
public abstract class BaseController implements Controller {
    /**
     * Speichert die aktuelle Instanz des Managers
     */
    protected Manager manager;

    /**
     * Setze einen Controller auf.
     * Ein Controller benötigt hierbei in jedem Fall eine Verbindung zum aktuellen
     * Manager, damit er mit anderen Teilen der App kommunizieren kann
     *
     * @param manager Manager Instanz
     */
    public BaseController(Manager manager) {
        this.manager = manager;
    }
}
