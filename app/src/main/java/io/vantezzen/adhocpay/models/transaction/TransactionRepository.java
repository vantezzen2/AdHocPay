package io.vantezzen.adhocpay.models.transaction;

import io.vantezzen.adhocpay.models.Repository;
import io.vantezzen.adhocpay.models.user.User;

public class TransactionRepository extends Repository<Transaction> {
    public Transaction sendTransaction(User sender, User receiver, float amount) {
        // TODO
        return null;
    }
}
