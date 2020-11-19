package io.vantezzen.adhocpay.models.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    @Test
    public void testKannNutzerVonNutzernamenBekommen() {
        UserRepository r = new UserRepository();

        User a = r.getUserByName("karl");

        assertEquals("karl", a.getUsername());
        assertEquals(1, r.getAll().size());
        assertEquals(a, r.getAll().get(0));

        // Kann gleichen Nutzer liefern
        User b = r.getUserByName("karl");

        assertEquals("karl", b.getUsername());
        assertEquals(a, b);
        assertEquals(1, r.getAll().size());

        // Kann andere Nutzer liefern
        User c = r.getUserByName("peter");

        assertEquals("peter", c.getUsername());
        assertNotEquals(a, c);
        assertEquals(2, r.getAll().size());
        assertEquals(c, r.getAll().get(1));
    }

    @Test
    public void testFailsWennNullAlsNutzernameGegebenWird() {
        UserRepository r = new UserRepository();

        try {
            r.getUserByName(null);

            fail("Ungültiger Name wurde akzeptiert");
        } catch (NullPointerException e) {}

        assertEquals(0, r.getAll().size());
    }
}