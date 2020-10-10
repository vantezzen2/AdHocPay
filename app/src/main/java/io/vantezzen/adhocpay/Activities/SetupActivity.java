package io.vantezzen.adhocpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.sharksystem.asap.android.apps.ASAPActivity;

import io.vantezzen.adhocpay.Activities.MainActivity;
import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;

public class SetupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
    }

    public void onSetupConfirm(View view) {
        EditText nutzername = findViewById(R.id.nutzernameInput);
        TextView errorMessage = findViewById(R.id.errorMessage);

        if (nutzername.getText().toString().length() == 0) {
            errorMessage.setText(R.string.setup_error_empty);
            return;
        }

        // Setze unseren neuen Nutzernamen
        AdHocPayApplication.getInstance().setUsername(nutzername.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}