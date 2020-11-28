package io.vantezzen.adhocpay.controllers;

import org.junit.Test;

import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.manager.ManagerMock;

import static org.junit.Assert.*;

public class UserControllerTest {
    @Test
    public void testKannRegistrieren() {
        Manager m = new ManagerMock();
        UserController c = new UserController(m);

        boolean erfolg = c.registerUser("karl");
        assertTrue(erfolg);
        assertEquals("karl", m.getSetting("username"));
    }

    @Test
    public void testKannNichtMehrmalsRegistrieren() {
        Manager m = new ManagerMock();
        UserController c = new UserController(m);

        c.registerUser("peter");

        boolean erfolg = c.registerUser("karl");
        assertFalse(erfolg);
        assertEquals("peter", m.getSetting("username"));
    }

    @Test
    public void testFailsWennNullAlsNameGegebenWird() {
        Manager m = new ManagerMock();
        UserController c = new UserController(m);

        try {
            c.registerUser(null);
            fail("Ungültiger Name wurde akzeptiert");
        } catch (NullPointerException e) {}
    }
}