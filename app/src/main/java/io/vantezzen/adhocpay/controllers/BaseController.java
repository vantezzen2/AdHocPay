package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.manager.Manager;

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
     * @throws NullPointerException the null pointer exception
     */
    public BaseController(Manager manager) throws NullPointerException {
        Validation.notNull(manager);

        this.manager = manager;
    }
}
