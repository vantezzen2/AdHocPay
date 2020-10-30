package io.vantezzen.adhocpay.models.transaction;

import com.google.gson.Gson;

import java.util.Date;

import io.vantezzen.adhocpay.models.Model;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;

public class Transaction implements Model {
    // Interne Variablen
    private User fromUser;
    private User toUser;
    private float amount;
    private Date time;

    private final String LOG_START = "Model:Transaction";

    public Transaction(User fromUser, User toUser, float amount, Date time, TransactionRepository repository) {
        repository.add(this);

        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.time = time;
    }

    public Transaction(String fromUser, String toUser, float amount, Date time, UserRepository users, TransactionRepository repository) {
        this(users.getUserByName(fromUser), users.getUserByName(toUser), amount, time, repository);
    }

    private void addToList() {
        ModelStorage.get().addTransaction(this);
    }

    // Getters
    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public float getAmount() {
        return amount;
    }

    public Date getTime() {
        return time;
    }

    /**
     * Konvertiere die Transaction zu einem JSON Objekt.
     *
     * @return JSON
     */
    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        return json;
    }
}
