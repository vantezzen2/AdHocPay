package io.vantezzen.adhocpay.models.transaction;

import java.time.LocalDate;
import java.util.Date;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.application.Manager;
import io.vantezzen.adhocpay.models.Repository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.network.NetworkCommunicator;

public class TransactionRepository extends Repository<Transaction> {
    /**
     * Sende eine Transaktion an das Netzwerk
     *
     * @param sender Sender Nutzer
     * @param receiver Empfänger Nutzer
     * @param amount Betrag
     * @param communicator Netzwerkkommunikation
     * @return Transaktion falls erfolgreich, sonst null
     */
    public Transaction sendTransaction(User sender, User receiver, float amount, NetworkCommunicator communicator) throws NullPointerException {
        Validation.notNull(sender);
        Validation.notNull(receiver);
        Validation.notNull(communicator);

        Transaction transaction = new Transaction(sender, receiver, amount, new Date(), this);

        boolean success = communicator.sendTransaction(transaction);
        if (success) {
            return transaction;
        }
        return null;
    }
}
