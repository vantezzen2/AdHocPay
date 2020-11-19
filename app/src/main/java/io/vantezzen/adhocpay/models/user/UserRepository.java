package io.vantezzen.adhocpay.models.user;

import java.util.List;

import io.vantezzen.adhocpay.models.Repository;

public class UserRepository extends Repository<User> {
    /**
     * Liefert einen Nutzer mit dem angegebenen Nutzernamen.
     * Dies liefert entweder die existierende Nutzer-Instanz, oder, falls diese noch nicht existiert,
     * erzeugt dies eine neue Instanz und speichert diese für zukünftige Aufrufe der Funktion
     *
     * @param name Nutzernamen
     * @return Nutzer
     */
    public User getUserByName(String name) {
        for (User user : getAll()) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        User user = new User(name, this);
        return user;
    }
}
