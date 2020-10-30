package io.vantezzen.adhocpay.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Before
    public void cleanModelStorage() {
        ModelStorage.forget();
    }

    @Test
    public void will_construct() {
        User user = new User("test1");

        assertEquals("test1", user.getUsername());
    }

    @Test
    public void will_add_itself_to_the_modelstorage() {
        assertEquals(0, ModelStorage.get().getUsers().size());

        User user = new User("test2");

        assertEquals(1, ModelStorage.get().getUsers().size());
        assertEquals(user, ModelStorage.get().getUsers().get(0));
    }

    @Test
    public void can_reuse_users_with_the_same_username() {
        User user1 = User.getUserByName("test1");
        User user2 = User.getUserByName("test2");
        User user3 = User.getUserByName("test1");

        assertEquals(user1, user3);
        assertNotEquals(user1, user2);
        assertNotEquals(user3, user2);

        assertNotEquals(null, user1);
        assertNotEquals(null, user2);
        assertNotEquals(null, user3);
    }

    // TODO
}