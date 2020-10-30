package io.vantezzen.adhocpay.models.user;

import java.util.List;

import io.vantezzen.adhocpay.models.Repository;

public class UserRepository extends Repository<User> {
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
