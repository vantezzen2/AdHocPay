package io.vantezzen.adhocpay.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.controllers.TransactionController;
import io.vantezzen.adhocpay.exceptions.InvalidTransactionException;

public class SendTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_transaction);
    }

    /**
     * Sende die Transaktion
     *
     * @param v Button view
     */
    public void onSendTransaction(View v) {
        TextView errorMessage = findViewById(R.id.errorMessage);
        EditText receiverName = findViewById(R.id.nutzernameInput);
        EditText amountField = findViewById(R.id.wertInput);

        String receiver = receiverName.getText().toString();
        Float amount = Float.parseFloat(amountField.getText().toString());

        TransactionController t = AdHocPayApplication.getManager().getControllerManager().getTransactionController();
        try {
            t.sendTransaction(amount, receiver);
        } catch (InvalidTransactionException e) {
            errorMessage.setText(e.getMessage());
            return;
        }

        // Leite zurück zur Startseite
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
    }
}