package io.vantezzen.adhocpay.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.utils.TransactionListRecyclerViewAdapter;

public class MainActivity extends BaseActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.transactionlist);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TransactionListRecyclerViewAdapter(AdHocPayApplication.getManager().getTransactionRepository()));

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