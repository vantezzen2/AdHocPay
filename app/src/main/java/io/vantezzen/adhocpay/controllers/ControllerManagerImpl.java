package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.manager.Manager;

/**
 * The type Controller manager.
 */
public class ControllerManagerImpl implements ControllerManager {
    /**
     * The User controller.
     */
    UserController userController;
    /**
     * The Transaction controller.
     */
    TransactionController transactionController;
    /**
     * The Manager.
     */
    Manager manager;

    /**
     * Instantiates a new Controller manager.
     *
     * @param manager the manager
     */
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
