package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.exceptions.InvalidTransactionException;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;

/**
 * TransactionController: Kontrolliert den Umgang mit Transaktionen
 */
public class TransactionController extends BaseController {
    public TransactionController(Manager manager) {
        super(manager);
    }

    /**
     * Sende eine Transaktion an einen anderen Nutzer
     *
     * @param wert Wert der Transaktion
     * @param receiverName Empfänger
     * @return Neue Transaktion
     */
    public Transaction sendTransaction(float wert, String receiverName) throws InvalidTransactionException {
        UserRepository userRepository = manager.getUserRepository();
        TransactionRepository transactionRepository = manager.getTransactionRepository();

        // Hole Nutzerobjekte und prüfe die Anfrage
        User sender = manager.getMe();
        if (sender == null) {
            throw new InvalidTransactionException("Du bist noch nicht registriert");
        }

        if (!Validation.isValidUsername(receiverName)) {
            throw new InvalidTransactionException("Der angegebene Empfängername ist nicht richtig.");
        }

        User receiver = userRepository.getUserByName(receiverName);

        if (sender == receiver) {
            throw new InvalidTransactionException("Du kannst kein Guthaben an dich selber senden");
        }

        Transaction transaction = transactionRepository.sendTransaction(sender, receiver, wert, manager.getNetwork());
        if (transaction == null) {
            throw new InvalidTransactionException("Es ist ein Fehler während dem Senden der Transaktion aufgetreten. Versuche es später erneut");
        }
        return transaction;
    }

    @Override
    public void onDataChange() {
        // Aktualisiere die View
        manager.refreshView();
    }
}
