package io.vantezzen.adhocpay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.exceptions.InvalidTransactionException;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.utils.TransactionListRecyclerViewAdapter;

/**
 * The type Main activity.
 */
public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TransactionListRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.transactionlist);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new TransactionListRecyclerViewAdapter(AdHocPayApplication.getManager().getTransactionRepository());
        recyclerView.setAdapter(recyclerViewAdapter);

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
        if(text != null) {
            TransactionRepository transactionRepository = manager.getTransactionRepository();
            text.setText(manager.getMe().getCredit(transactionRepository) + "€");
        }

        TextView hello = findViewById(R.id.helloUserText);
        if (hello != null) {
            hello.setText("Hallo, " + manager.getMe().getUsername() + "!");
        }

        // Aktualisiere den RecyclerView
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Öffne die Seite, um Transaktionen zu senden
     *
     * @param v Button
     */
    public void onSendTransaction(View v) {
        Intent i = new Intent(this, SendTransactionActivity.class);
        this.startActivity(i);
    }
}