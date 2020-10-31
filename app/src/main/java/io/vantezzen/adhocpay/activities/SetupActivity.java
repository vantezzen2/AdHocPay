package io.vantezzen.adhocpay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.application.Manager;

public class SetupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
    }

    @Override
    public void onDataChange() {

    }

    /**
     * Gehe mit dem Klick des Registrieren Buttons um.
     * Dies registriert den neuen Benutzer über den UserController
     *
     * @param view Button
     */
    public void onSetupConfirm(View view) {
        EditText nutzername = findViewById(R.id.nutzernameInput);
        TextView errorMessage = findViewById(R.id.errorMessage);

        if (nutzername.getText().toString().length() == 0) {
            errorMessage.setText(R.string.setup_error_empty);
            return;
        }

        // Setze unseren neuen Nutzernamen
        Manager manager = AdHocPayApplication.getInstance().getManager();
        manager.getControllerManager().getUserController().registerUser(nutzername.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}