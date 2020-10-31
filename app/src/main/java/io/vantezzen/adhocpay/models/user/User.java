package io.vantezzen.adhocpay.models.user;

import java.util.ArrayList;
import java.util.List;

import io.vantezzen.adhocpay.models.Model;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;

public class User implements Model {
    private String username;

    private final String LOG_START = "Model:User";

    public User(String username, UserRepository repository) {
        repository.add(this);

        this.username = username;
    }

    /**
     * Liefert alle Transaktionen dieses Nutzers
     *
     * @return List of transactions
     */
    public List<Transaction> transactions(TransactionRepository repository) {
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
    public float getCredit(TransactionRepository repository) {
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

    public String getUsername() {
        return username;
    }
}
