package io.vantezzen.adhocpay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.manager.Manager;

/**
 * StartupActivity: Startet die AdHoc Verbindung und leitet auf die richtige
 * Activity weiter.
 */
public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdHocPayApplication.setActivity(this);
        setContentView(R.layout.activity_startup);

        Log.d("SetupActivity", "Starte App");
        Log.d("SetupActivity", "Ist Testing: " + (AdHocPayApplication.isTesting() ? "y" : "n"));

        if (!AdHocPayApplication.isTesting()) {
            AdHocPayApplication.initializeApplication();
        }

        // Starte die App
        this.finish();

        Intent intent;
        Manager manager = AdHocPayApplication.getManager();
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