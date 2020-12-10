package io.vantezzen.adhocpay.models.transaction;

import java.time.LocalDateTime;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.exceptions.InvalidTransactionException;
import io.vantezzen.adhocpay.models.Repository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.network.NetworkCommunicator;

/**
 * Transaction Repository: Verwaltet die Transaction Models
 */
public class TransactionRepository extends Repository<Transaction> {
    /**
     * Erstelle Testdaten.
     * Dies wird nur zum aktuellen Testen der App benötigt und wird später entfernt
     */
    public void createSomeTestData(UserRepository u) {
        new Transaction(
                "karl",
                "peter",
                15.99f,
                LocalDateTime.of(2020, 11, 14, 15, 00),
                u,
                this
        );
        new Transaction(
                "hans",
                "peter",
                0.99f,
                LocalDateTime.of(2020, 11, 28, 10, 59),
                u,
                this
        );
        new Transaction(
                "peter",
                "marko",
                14.17f,
                LocalDateTime.now(),
                u,
                this
        );
        new Transaction(
                "heinrich",
                "marko",
                100.00f,
                LocalDateTime.now(),
                u,
                this
        );
    }
    public TransactionRepository(UserRepository u, boolean addTestData) {
        super();
        if (addTestData) {
            createSomeTestData(u);
        }
    }

    /**
     * Sende eine Transaktion an das Netzwerk
     *
     * @param sender Sender Nutzer
     * @param receiver Empfänger Nutzer
     * @param amount Betrag
     * @param communicator Netzwerkkommunikation
     * @return Transaktion falls erfolgreich, sonst null
     */
    public Transaction sendTransaction(User sender, User receiver, float amount, NetworkCommunicator communicator) throws NullPointerException, IllegalArgumentException, InvalidTransactionException {
        Validation.notNull(sender);
        Validation.notNull(receiver);
        Validation.notNull(communicator);

        if (amount <= 0) {
            throw new InvalidTransactionException("Der Betrag müss größer als 0 sein");
        }
        if (Float.isNaN(amount)) {
            throw new InvalidTransactionException("Bitte gebe einen gültigen Wert ein");
        }

        Transaction transaction = new Transaction(sender, receiver, amount, LocalDateTime.now(), this);

        boolean success = communicator.sendTransaction(transaction);
        if (success) {
            return transaction;
        }

        throw new InvalidTransactionException("Die Transaktion konnte nicht gesendet werden. Versuche es später erneut");
    }
}
