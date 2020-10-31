package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.application.Manager;

public class ControllerManagerImpl implements ControllerManager {
    UserController userController;
    Manager manager;

    public ControllerManagerImpl(Manager manager) {
        this.manager = manager;

        userController = new UserController(manager);
    }

    @Override
    public UserController getUserController() {
        return userController;
    }

    @Override
    public void onDataChange() {
        userController.onDataChange();
    }
}
