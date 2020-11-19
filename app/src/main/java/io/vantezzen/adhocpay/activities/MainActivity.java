package io.vantezzen.adhocpay.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.application.Manager;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayDynamicData();
    }

    @Override
    public void onDataChange() {
        displayDynamicData();
    }

    /**
     * Aktualisiere dynamische Teile der View
     */
    protected void displayDynamicData() {
        // Zeige Credit/status informationen
        TextView text = findViewById(R.id.credits);
        Manager manager = AdHocPayApplication.getManager();
        TransactionRepository transactionRepository = manager.getTransactionRepository();
        text.setText(manager.getMe().getCredit(transactionRepository) + "€");

        // Setup Recycler View
        // TODO
    }

    /**
     * Sende eine Beispieltransaktion
     *
     * @param v View
     */
    public void onSendExample(View v) {
        ControllerManager controllerManager = AdHocPayApplication.getManager().getControllerManager();
        Transaction test = controllerManager.getTransactionController().sendTransaction(15.99f, "Hans");

        displayDynamicData();
    }
}