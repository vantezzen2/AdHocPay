package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.application.Manager;

/**
 * BaseController: Definiere allgemeine Methoden und Eigenschaften aller Controller
 */
public abstract class BaseController implements Controller {
    protected Manager manager;

    public BaseController(Manager manager) {
        this.manager = manager;
    }
}
