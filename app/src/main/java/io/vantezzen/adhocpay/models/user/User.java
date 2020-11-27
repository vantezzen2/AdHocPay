package io.vantezzen.adhocpay.models.user;

import java.util.ArrayList;
import java.util.List;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.models.Model;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;

public class User implements Model {
    // Daten des Models
    private String username;

    /**
     * Erstelle einen neuen Nutzer
     *
     * @param username Nutzername
     * @param repository Nutzerrepository
     * @throws NullPointerException Wenn repository = null
     */
    public User(String username, UserRepository repository) throws NullPointerException {
        Validation.notNull(repository);

        repository.add(this);

        this.username = username;
    }

    /**
     * Liefert alle Transaktionen dieses Nutzers
     *
     * @return List of transactions
     */
    public List<Transaction> transactions(TransactionRepository repository) throws NullPointerException {
        Validation.notNull(repository);

        List<Transaction> result = new ArrayList<>();

        for(Transaction transaction : repository.getAll()) {
            if (transaction.getFromUser() == this || transaction.getToUser() == this) {
                result.add(transaction);
            }
        }

        return result;
    }

    /**
     * Get the amount of credits this user has available
     *
     * @return Credits
     */
    public float getCredit(TransactionRepository repository) throws NullPointerException {
        Validation.notNull(repository);
        float credit = 50;

        for (Transaction transaction : transactions(repository)) {
            if (transaction.getFromUser() == this) {
                credit -= transaction.getAmount();
            }
            if (transaction.getToUser() == this) {
                credit += transaction.getAmount();
            }
        }

        // Round to two decimal places
        return Math.round(credit * 100.0) / 100.0f;
    }

    /**
     * Liefert den Nutzernamen dieses Nutzers
     *
     * @return Nutzername
     */
    public String getUsername() {
        return username;
    }
}
