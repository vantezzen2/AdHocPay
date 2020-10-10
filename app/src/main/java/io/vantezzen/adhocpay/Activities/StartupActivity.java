package io.vantezzen.adhocpay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.vantezzen.adhocpay.Activities.MainActivity;
import io.vantezzen.adhocpay.Activities.SetupActivity;
import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;

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