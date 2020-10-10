package io.vantezzen.adhocpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
        if (AdHocPayApplication.getInstance().isSetup()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, SetupActivity.class);
        }

        this.startActivity(intent);
    }
}