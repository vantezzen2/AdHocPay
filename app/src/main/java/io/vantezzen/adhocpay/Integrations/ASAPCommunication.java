package io.vantezzen.adhocpay.Integrations;

import android.app.Activity;
import android.util.Log;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPMessages;
import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.asap.android.apps.ASAPMessageReceivedListener;
import net.sharksystem.asap.android.apps.ASAPUriContentChangedListener;

import java.io.IOException;
import java.util.Iterator;

import io.vantezzen.adhocpay.AdHocPayApplication;

/**
 * ASAPCommunication: Handle communicating with the ASAP framework
 */
public class ASAPCommunication implements ASAPMessageReceivedListener, ASAPUriContentChangedListener {
    private AdHocPayApplication application = null;
    private boolean started = false;

    private String LOG_START = "ASAPCommunication";

    public ASAPCommunication(AdHocPayApplication application) {
        this.application = application;

        // We are the listener for all new ASAP messages
        application.addASAPMessageReceivedListener(AdHocPayApplication.ASAP_APPNAME, this);
    }

    public void startASAP() {
        if (started) {
            Log.d(LOG_START, "Already started");
            return;
        }
        Activity activity = application.getActivity();
        if (!(activity instanceof ASAPActivity)) {
            Log.d(LOG_START, "Not currently in a ASAP Activity");
            return;
        }

        ASAPActivity act = (ASAPActivity) application.getActivity();
        act.startBluetooth();
        act.startBluetoothDiscovery();
        act.startBluetoothDiscoverable();

        Log.d(LOG_START, "Communication started");

        started = true;
    }

    /**
     * Transmit a message using ASAP.
     *
     * @param stringMessage Message that should be sent
     * @param uri URI to use for transmitting
     * @return True if the message got transferred successfully
     */
    public boolean transmit(String stringMessage, CharSequence uri) {
        CharSequence appName = AdHocPayApplication.ASAP_APPNAME;

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

    /**
     * Transmit a message using ASAP.
     * This will transmit on AdHocPay's default URI
     *
     * @param message Message to sent
     * @return True if the message got transferred successfully
     */
    public boolean transmit(String message) {
        return transmit(message, AdHocPayApplication.DEFAULT_URI);
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
        }

        Log.d(LOG_START, "Messages received");
    }

    @Override
    public void asapUriContentChanged(CharSequence charSequence) {
        Log.d(LOG_START, "URI CHANGED" + charSequence);
    }
}
