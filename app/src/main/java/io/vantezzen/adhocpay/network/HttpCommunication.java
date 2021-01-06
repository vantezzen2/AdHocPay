package io.vantezzen.adhocpay.network;

import android.content.Intent;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserDeserializer;
import io.vantezzen.adhocpay.network.asap.ASAPApp;
import io.vantezzen.adhocpay.utils.LocalDateTimeSerializerDeserializer;

/**
 * HttpCommunication: Erlaubt die Transaktion-Übertragung über einen zentralen Server.
 * Diese Art der Kommunikation ist zur Zeit nur implementiert, da ASAP bei den Nachrichten
 * zu oft abstürzt
 */
public class HttpCommunication implements NetworkCommunicator {
    public static final String SERVER = "192.168.0.35:3000";
    private final Manager manager;
    private OkHttpClient client;
    private Set<Integer> fetchedIds;

    public HttpCommunication(Manager manager) {
        this.manager = manager;
        client = new OkHttpClient();
        fetchedIds = new HashSet();

        Handler handler = new Handler();

        // Starte unsere GET-Loop, bei welcher wir alle 10 Sekunden nach neuen Transaktionen suchen
        new Thread(() -> {
            boolean isInterrupted = false;
            // Füge die Transaktion hinzu
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerDeserializer());
            gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer(manager.getUserRepository()));
            Gson gson = gsonBuilder.create();

            while (!isInterrupted) {
                try {
                    manager.log("HttpThread", "Neuer Durchlauf");
                    String transactionStr = makeGetRequest("http://" + SERVER + "/get/0");
                    manager.log("HttpThread", "Antwort von Länge " + transactionStr.length());

                    Transaction[] transactions = gson.fromJson(transactionStr, Transaction[].class);
                    manager.log("HttpThread", transactions.length + " Transaktionen");

                    boolean hasChangedData = false;
                    for(Transaction transaction : transactions) {
                        if (!fetchedIds.contains(transaction.id)) {
                            manager.getTransactionRepository().receiveTransaction(transaction);
                            fetchedIds.add(transaction.id);

                            manager.log("HttpThread", "Neue Transaktion hinzugefügt: " + transaction.id);
                            hasChangedData = true;
                        }
                    }
                    if (hasChangedData) {
                        // Update des UI Threads
                        handler.post(() -> manager.getControllerManager().onDataChange());
                    }


                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    isInterrupted = true;
                }
            }
        }).start();
    }

    @Override
    public void setup() {}

    @Override
    public ASAPApp getASAPApp() {
        return null;
    }

    private String makeGetRequest(String url) {
        manager.log("HttpComm", "HTTP Req zu: " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean sendTransaction(Transaction t) throws NullPointerException {
        manager.log("HttpComm", "Sende neue Transaktion" + t.toJson());

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://" + SERVER + "/add").newBuilder();
        urlBuilder.addQueryParameter("fromUser", t.getFromUser().getUsername());
        urlBuilder.addQueryParameter("toUser", t.getToUser().getUsername());
        urlBuilder.addQueryParameter("amount", String.valueOf(t.getAmount()));
        String url = urlBuilder.build().toString();

        new Thread(() -> {
            String res = makeGetRequest(url);
            manager.log("HttpComm", "Antwort: " + res);
            fetchedIds.add(Integer.valueOf(res));
        }).start();

        return true;
    }

    @Override
    public void setControllerManager(ControllerManager c) throws NullPointerException {

    }
}
