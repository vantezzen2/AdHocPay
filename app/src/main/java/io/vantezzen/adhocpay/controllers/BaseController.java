package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.application.Manager;

public abstract class BaseController implements Controller {
    protected Manager manager;

    public BaseController(Manager manager) {
        this.manager = manager;
    }
}
