package io.vantezzen.adhocpay.controllers;

import org.junit.Test;

import io.vantezzen.adhocpay.exceptions.InvalidTransactionException;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.manager.ManagerMock;

import static org.junit.Assert.*;

public class TransactionControllerTest {
    @Test
    public void testKannTransaktionSenden() throws InvalidTransactionException {
        Manager m = new ManagerMock();
        TransactionController t = new TransactionController(m);

        t.sendTransaction(19.99f, "karl");

        assertEquals(
                1,
                m.getTransactionRepository().getAll().size()
        );
        assertEquals(
                m.getUserRepository().getUserByName("karl"),
                m.getTransactionRepository().getAll().get(0).getToUser()
        );
        assertEquals(
                m.getMe(),
                m.getTransactionRepository().getAll().get(0).getFromUser()
        );
    }

    @Test
    public void testFailsWennUngueltigerEmpfaengerAngegebenWird() throws InvalidTransactionException {
        Manager m = new ManagerMock();
        TransactionController t = new TransactionController(m);

        try {
            t.sendTransaction(19.99f, null);
            fail("Ungültige Transaktion wurde gesendet");
        } catch (NullPointerException e) {}

        // Darf keine Transaktion hinzufügen
        assertEquals(
                0,
                m.getTransactionRepository().getAll().size()
        );
    }

    @Test
    public void testFailsWennNegativerWertAngegebenWird() throws InvalidTransactionException {
        Manager m = new ManagerMock();
        TransactionController t = new TransactionController(m);

        try {
            t.sendTransaction(-19.99f, "karl");
            fail("Ungültige Transaktion wurde gesendet");
        } catch (IllegalArgumentException e) {}

        // Darf keine Transaktion hinzufügen
        assertEquals(
                0,
                m.getTransactionRepository().getAll().size()
        );
    }
}