package io.vantezzen.adhocpay.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import io.vantezzen.adhocpay.models.transaction.Transaction;

import static org.junit.Assert.*;

public class oldModelStorageTest {
    @Before
    public void cleanModelStorage() {
        ModelStorage.forget();
    }

    @Test
    public void will_hold_current_instance() {
        ModelStorage instance1 = ModelStorage.get();
        ModelStorage instance2 = ModelStorage.get();

        assertEquals(instance1, instance2);
    }

    @Test
    public void will_store_users() {
        assertEquals(0, ModelStorage.get().getUsers().size());

        User user1 = new User("test1");

        assertEquals(1, ModelStorage.get().getUsers().size());
        assertEquals(user1, ModelStorage.get().getUsers().get(0));

        User user2 = new User("test2");

        assertEquals(2, ModelStorage.get().getUsers().size());
        assertEquals(user1, ModelStorage.get().getUsers().get(0));
        assertEquals(user2, ModelStorage.get().getUsers().get(1));
    }

    @Test
    public void will_store_transactions() {
        assertEquals(0, ModelStorage.get().getTransactions().size());

        Transaction transaction1 = new Transaction("a", "b", 1, new Date());

        assertEquals(1, ModelStorage.get().getTransactions().size());
        assertEquals(transaction1, ModelStorage.get().getTransactions().get(0));

        Transaction transaction2 = new Transaction("b", "c", 2, new Date());

        assertEquals(2, ModelStorage.get().getTransactions().size());
        assertEquals(transaction1, ModelStorage.get().getTransactions().get(0));
        assertEquals(transaction2, ModelStorage.get().getTransactions().get(1));
    }
}