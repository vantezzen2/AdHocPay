package io.vantezzen.adhocpay.activities;

import android.os.Bundle;
import android.util.Log;

import net.sharksystem.asap.android.apps.ASAPActivity;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.network.ASAPCommunication;

/**
 * The type Base activity.
 */
public abstract class BaseActivity extends ASAPActivity {
    /**
     * Erzeuge eine neue BaseActivity Instanz.
     */
    public BaseActivity() {
        super(
                (
                        (AdHocPayApplication.getManager().getNetworkCommunicator())
                ).getASAPApp());

        Log.d("BaseActivity", "Wurde erstellt");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("BaseActivity", "Starte ASAP");
        AdHocPayApplication.setActivity(this);
        super.onCreate(savedInstanceState);

        // Stelle sicher, dass unsere Verbindung zum Netzwerk steht
        Log.d("BaseActivity", "Setze Netzwerk auf");
        AdHocPayApplication.getManager().setupNetwork();
    }

    /**
     * Reagiere auf Änderungen in den Daten
     */
    abstract public void onDataChange();
}
