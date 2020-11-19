package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.application.Manager;

/**
 * UserController: Managed mit den Umgang mit Nutzern
 */
public class UserController extends BaseController {
    public UserController(Manager manager) {
        super(manager);
    }

    /**
     * Registriere den Nutzer mit einem Nutzernamen
     *
     * @param username Nutzername
     * @return False, falls der Nutzer bereits registriert ist
     */
    public boolean registerUser(String username) {
        if (manager.getSetting("username") != null) {
            // Nutzer ist bereits registriert
            return false;
        }

        manager.storeSetting("username", username);
        return true;
    }

    @Override
    public void onDataChange() {
        // Tue nichts
    }
}
