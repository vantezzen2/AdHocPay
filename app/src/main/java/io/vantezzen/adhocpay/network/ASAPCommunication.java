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
import net.sharksystem.asap.android.apps.ASAPMessageReceivedListener;

import java.io.IOException;
import java.util.Iterator;

import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserDeserializer;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.application.Manager;
import io.vantezzen.adhocpay.controllers.Controller;

/**
 * ASAPCommunication: Handle communicating with the ASAP framework
 */
public class ASAPCommunication implements ASAPMessageReceivedListener, NetworkCommunicator {
    private Manager application = null;
    private boolean started = false;
    private Controller c = null;
    private ASAPStorage asapStorage = null;

    private String LOG_START = "ASAPCommunication";
    private String id;

    public ASAPCommunication(Manager application, Controller c) {
        this.application = application;
        this.c = c;

        // We are the listener for all new ASAP messages
        application.registerASAPListener(this);
    }

    @Override
    public void setup() {
        if (started) {
            Log.d(LOG_START, "Already started");
            return;
        }

        // Setze ASAP auf
        this.id = ASAP.createUniqueID();

        // Setze ASAPStorage auf
        String folder = application.getApplicationRootFolder(application.getAppName());
        try {
            asapStorage = ASAPEngineFS.getASAPStorage(
                    application.getOwnerId(),
                    folder,
                    application.getAppName()
            );
        } catch (IOException e) {
            // TODO: Handle these
            e.printStackTrace();
        } catch (ASAPException e) {
            e.printStackTrace();
        }

        if (asapStorage == null) {
            try {
                asapStorage = application.getAsapStorage(application.getAppName());
            } catch (IOException e) {
                // TODO: Handle these
                e.printStackTrace();
            } catch (ASAPException e) {
                e.printStackTrace();
            }
        }

        Activity activity = application.getActivity();
        if (!(activity instanceof ASAPActivity)) {
            Log.d(LOG_START, "Not currently in a ASAP Activity");
            // TODO: Handle this
            return;
        }

        ASAPActivity act = (ASAPActivity) application.getActivity();
        act.startBluetooth();
        act.startBluetoothDiscovery();
        act.startBluetoothDiscoverable();

        Log.d(LOG_START, "Communication started");

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
        } catch (ASAPException e) {
            // TODO: Handle these
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            Log.d("AdHocPayApplication", "Restoring " + messages.size() + " messages");
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

        Activity activity = application.getActivity();

        if (!(activity instanceof ASAPActivity)) {
            // We cannot transmit on non-asap activities
            Log.d(LOG_START, "Can't transmit message as we aren't in an ASAP Activity");
            return false;
        }

        try {
            ((ASAPActivity) activity).sendASAPMessage(appName, uri, message, true);
        } catch(ASAPException e) {
            Log.d(LOG_START, "Error while sending message: " + e);
            return false;
        }

        Log.d(LOG_START, "Successfully sent message");

        return true;
    }

    @Override
    public void asapMessagesReceived(ASAPMessages asapMessages) {
        Iterator<byte[]> msgInter;

        Log.d(LOG_START, "Receiving new message...");

        try {
            msgInter = asapMessages.getMessages();
        } catch (IOException e) {
            // Ignore invalid messages
            Log.d(LOG_START, "Error while receiving message: " + e);
            return;
        }
        while(msgInter.hasNext()) {
            byte[] msgBytes = msgInter.next();
            String msg = new String(msgBytes);
            Log.d("ASAPCommunication", "message received: " + msg);

            // Teste, dass die Nachricht ein JSON String ist
            if(msg.charAt(0) != '{') continue;

            // Füge die Transaktion hinzu
            Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserDeserializer(application.getUserRepository())).create();
            gson.fromJson(msg, Transaction.class);
        }

        Log.d(LOG_START, "Messages received");

        // Inform Activity about data update
        if (this.c != null) {
            this.c.onDataChange();
        }
    }

    @Override
    public boolean sendTransaction(Transaction t) {
        String message = t.toJson();

        Log.d(LOG_START, "Sending transaction: " + message);

        return transmit(message, application.getDefaultUri());
    }

    @Override
    public void setController(Controller c) {
        this.c = c;
    }
}
