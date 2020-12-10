package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.manager.Manager;

public class ControllerManagerImpl implements ControllerManager {
    UserController userController;
    TransactionController transactionController;
    Manager manager;

    public ControllerManagerImpl(Manager manager) {
        this.manager = manager;

        userController = new UserController(manager);
        transactionController = new TransactionController(manager);
    }

    @Override
    public UserController getUserController() {
        return userController;
    }

    @Override
    public TransactionController getTransactionController() {
        return transactionController;
    }

    @Override
    public void onDataChange() {
        userController.onDataChange();
        transactionController.onDataChange();
    }
}
