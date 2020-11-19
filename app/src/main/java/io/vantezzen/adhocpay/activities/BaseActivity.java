package io.vantezzen.adhocpay.activities;

import android.os.Bundle;

import net.sharksystem.asap.android.apps.ASAPActivity;

import io.vantezzen.adhocpay.AdHocPayApplication;

public abstract class BaseActivity extends ASAPActivity {
    /**
     * Erzeuge eine neue BaseActivity Instanz.
     */
    public BaseActivity() {
        super(AdHocPayApplication.getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Stelle sicher, dass unsere Verbindung zum Netzwerk steht
        AdHocPayApplication.getManager().setupNetwork();
    }

    /**
     * Reagiere auf Änderungen in den Daten
     */
    abstract public void onDataChange();
}
