package io.vantezzen.adhocpay.models.transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.Date;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.models.Model;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.utils.LocalDateTimeSerializerDeserializer;

public class Transaction implements Model {
    // Interne Variablen
    private User fromUser;
    private User toUser;
    private float amount;
    private LocalDateTime time;

    private final String LOG_START = "Model:Transaction";

    public Transaction() {
        time = LocalDateTime.now();
    }

    /**
     * Erzeuge eine neue Transaktion
     *
     * @param fromUser Sendender Nutzer
     * @param toUser Empfangender Nutzer
     * @param amount Betrag
     * @param time Zeitpunkt der Transaktion
     * @param repository Transaktions Repository
     */
    public Transaction(User fromUser, User toUser, float amount, LocalDateTime time, TransactionRepository repository) throws NullPointerException {
        Validation.notNull(fromUser);
        Validation.notNull(toUser);
        Validation.notNull(time);
        Validation.notNull(repository);

        repository.add(this);

        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.time = time;
    }

    /**
     * Erzeuge eine Neue Transaktion mit Hilfe einer UserRepository
     *
     * @param fromUser Name des Senders
     * @param toUser Name des Empfängers
     * @param amount Wert
     * @param time Zeitpunkt
     * @param users Nutzerrepository
     * @param repository Transaktionsrepository
     */
    public Transaction(String fromUser, String toUser, float amount, LocalDateTime time, UserRepository users, TransactionRepository repository) {
        this(users.getUserByName(fromUser), users.getUserByName(toUser), amount, time, repository);
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

    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Konvertiere die Transaction zu einem JSON Objekt.
     *
     * @return JSON
     */
    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerDeserializer());

        Gson gson = gsonBuilder.create();
        String json = gson.toJson(this);

        return json;
    }
}
