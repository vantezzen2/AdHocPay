package io.vantezzen.adhocpay.network;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sharksystem.asap.ASAP;
import net.sharksystem.asap.ASAPChannel;
import net.sharksystem.asap.ASAPEngineFS;
import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPMessages;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.asap.apps.ASAPMessageReceivedListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.vantezzen.adhocpay.Validation;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserDeserializer;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.network.asap.ASAPApp;
import io.vantezzen.adhocpay.utils.LocalDateTimeSerializerDeserializer;

/**
 * ASAPCommunication: Handle communicating with the ASAP framework
 */
public class ASAPCommunication implements ASAPMessageReceivedListener, NetworkCommunicator {
    private Manager application = null;
    private boolean started = false;
    private ControllerManager controllerManager = null;
    private ASAPStorage asapStorage = null;

    private String LOG_START = "ASAPCommunication";

    /**
     * Setze eine neue ASAPCommunication Klasse auf
     *
     * @param application Application Manager
     * @param c           Controller Manager
     */
    public ASAPCommunication(Manager application, ControllerManager c) {
        this.application = application;
        this.controllerManager = c;

        // Set up the ASAPApplication instance
        ASAPApp.initializeApplication(application.getActivity());

        // We are the listener for all new ASAP messages
        application.registerASAPListener(this);
    }

    @Override
    public void setup() {
        if (started) {
            this.application.log(LOG_START, "Already started");
            return;
        }

        // Setze ASAPStorage auf
        try {
            asapStorage = application.getAsapStorage(application.getAppName());
        } catch (IOException e) {
            // TODO: Handle these
            e.printStackTrace();
        } catch (ASAPException e) {
            e.printStackTrace();
        }

        Activity activity = application.getActivity();
        if (!(activity instanceof ASAPActivity)) {
            this.application.log(LOG_START, "Not currently in a ASAP Activity");
            // TODO: Handle this?
        } else {
            ASAPActivity act = (ASAPActivity) application.getActivity();
            act.startBluetooth();
            act.startBluetoothDiscovery();
            act.startBluetoothDiscoverable();
        }

        this.application.log(LOG_START, "Communication started");

        restoreData();

        started = true;
    }

    /**
     * Restore data that we stored from previous runs
     */
    private void restoreData() {
        ASAPChannel channel;
        ASAPMessages messages;
        try {
            channel = asapStorage.getChannel(application.getDefaultUri());
            messages = channel.getMessages();
        } catch (IOException | ASAPException e) {
            // TODO: Handle these
            Log.d(LOG_START, e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            this.application.log("AdHocPayApplication", "Restoring " + messages.size() + " messages");
        } catch (IOException e) {}
        asapMessagesReceived(messages);
    }

    /**
     * Sende eine String Nachricht über ASAP.
     *
     * @param stringMessage Message that should be sent
     * @param uri URI to use for transmitting
     * @return True if the message got transferred successfully
     */
    private boolean transmit(String stringMessage, CharSequence uri) {
        CharSequence appName = application.getAppName();

        byte[] message = stringMessage.getBytes();

        if (!(application.getActivity() instanceof ASAPActivity)) {
            // We cannot transmit on non-asap activities
            this.application.log(LOG_START, "Can't transmit message as we aren't in an ASAP Activity");
            return false;
        }

        try {
            ASAPActivity asapActivity = (ASAPActivity) application.getActivity();
            asapActivity.sendASAPMessage(appName, uri, message, true);
        } catch(ASAPException e) {
            this.application.log(LOG_START, "Error while sending message: " + e);
            return false;
        }

        this.application.log(LOG_START, "Successfully sent message");

        return true;
    }

    /**
     * Importiere die Transaktion aus einer String-Nachricht
     *
     * @param msg Nachricht
     */
    private void addTransaction(String msg) {
        // Füge die Transaktion hinzu
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerDeserializer());
        gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer(application.getUserRepository()));

        Gson gson = gsonBuilder.create();
        Transaction transaction = gson.fromJson(msg, Transaction.class);
        application.getTransactionRepository().receiveTransaction(transaction);
    }

    @Override
    public void asapMessagesReceived(ASAPMessages asapMessages) {
        Iterator<byte[]> msgInter;

        this.application.log(LOG_START, "Receiving new message...");

        try {
            msgInter = asapMessages.getMessages();
        } catch (IOException e) {
            // Ignore invalid messages
            this.application.log(LOG_START, "Error while receiving message: " + e);
            return;
        }

        // Wir müssen geteilte Nachrichten wieder zusammensetzen
        String message = "";
        while(msgInter.hasNext()) {
            byte[] msgBytes = msgInter.next();
            String msg = new String(msgBytes);
            this.application.log("ASAPCommunication", "message received: " + msg);

            if (msg.equals("__START__")) {
                // Fange eine neue Nachricht an und beende die vorherige Nachricht
                if (message.length() > 0) {
                    this.application.log("ASAPCommunication", "Komplette Transaktions-Nachricht: " + message);
                    addTransaction(message);
                }

                this.application.log("ASAPCommunication", "Fange das Empfangen einer neue Transaktion an");

                message = "";
            } else {
                message += msg;
            }
        }

        if (message.length() > 0) {
            addTransaction(message);
        }

        this.application.log(LOG_START, "Messages received");

        // Inform Controllers about data update
        if (this.controllerManager != null) {
            this.controllerManager.onDataChange();
        }
    }

    @Override
    public boolean sendTransaction(Transaction t) throws NullPointerException {
        Validation.notNull(t);

        String message = t.toJson();

        // Wir müssen die message in Teile von höchstens 127 byte teilen, siehe https://github.com/SharedKnowledge/ASAPAndroid/issues/6

        transmit("__START__", application.getDefaultUri());
        int stringPos = 0;
        int partLength = 120;
        while(stringPos < message.length()) {
            transmit(
                    message.substring(stringPos, stringPos + partLength),
                    application.getDefaultUri()
            );
            stringPos += partLength;
        }

        this.application.log(LOG_START, "Transaktion gesendet: " + message);

        return true;
    }

    @Override
    public void setControllerManager(ControllerManager c) throws NullPointerException {
        Validation.notNull(c);
        this.controllerManager = c;
    }

    /**
     * Gets asap app.
     *
     * @return the asap app
     */
    public ASAPApp getASAPApp() {
        return ASAPApp.getInstance();
    }
}
