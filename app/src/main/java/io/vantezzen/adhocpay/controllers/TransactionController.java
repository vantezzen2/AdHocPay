package io.vantezzen.adhocpay.controllers;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.application.Manager;
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
    public Transaction sendTransaction(float wert, String receiverName) {
        UserRepository userRepository = manager.getUserRepository();
        TransactionRepository transactionRepository = manager.getTransactionRepository();

        // Hole Nutzerobjekte
        User sender = manager.getMe();
        User receiver = userRepository.getUserByName(receiverName);

        Transaction transaction = transactionRepository.sendTransaction(sender, receiver, wert, manager.getNetwork());
        if (transaction == null) {
            // TODO: Handle this
        }
        return transaction;
    }

    @Override
    public void onDataChange() {
        // Tue nichts
    }
}
