package io.vantezzen.adhocpay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.application.Manager;

/**
 * StartupActivity: Startet die AdHoc Verbindung und leitet auf die richtige
 * Activity weiter.
 */
public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        AdHocPayApplication.initializeApplication(this);

        // Starte die App
        this.finish();

        Intent intent;
        Manager manager = AdHocPayApplication.getInstance().getManager();
        if (manager.getMe() != null) {
            // Nutzer hat sich bereits registriert - starte den Hauptbildschirm
            intent = new Intent(this, MainActivity.class);
        } else {
            // Nutzer hat sich noch nicht registriert - starte den Setup Bildschirm
            intent = new Intent(this, SetupActivity.class);
        }

        this.startActivity(intent);
    }
}