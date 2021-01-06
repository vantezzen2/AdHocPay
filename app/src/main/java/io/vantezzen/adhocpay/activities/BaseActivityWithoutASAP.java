package io.vantezzen.adhocpay.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import net.sharksystem.asap.android.apps.ASAPActivity;

import io.vantezzen.adhocpay.AdHocPayApplication;

/**
 * BaseActivity ohne ASAP Unterstützung
 */
public abstract class BaseActivityWithoutASAP extends AppCompatActivity {
    /**
     * Erzeuge eine neue BaseActivity Instanz.
     */
    public BaseActivityWithoutASAP() {
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