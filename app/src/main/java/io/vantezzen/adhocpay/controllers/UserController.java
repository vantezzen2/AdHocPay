package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.models.user.User;

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
        Validation.notNull(username);

        if (manager.getSetting("username") != null) {
            // Nutzer ist bereits registriert
            return false;
        }

        manager.storeSetting("username", username);
        return true;
    }

    /**
     * Liefert einen Nutzer für seine Nutzerinformationen
     *
     * @param username Nutzername des Nutzers
     * @return Nutzer
     */
    public User getUser(String username) {
        return null;
    }

    @Override
    public void onDataChange() {
        // Tue nichts
    }
}
