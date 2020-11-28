package io.vantezzen.adhocpay.utils;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.ZoneId;
import java.util.List;

import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;

/**
 * RecyclerView Adapter for the Transaction List
 * Based on the example adapter on https://developer.android.com/guide/topics/ui/layout/recyclerview
 */
public class TransactionListRecyclerViewAdapter extends RecyclerView.Adapter<TransactionListRecyclerViewAdapter.ViewHolder> {
    private final TransactionRepository respository;
    private List<Transaction> transactions;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView senderReceiverTextView;
        private final TextView amountTextView;
        private final TextView timeAgoTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            senderReceiverTextView = (TextView) view.findViewById(R.id.sender_receiver);
            amountTextView = (TextView) view.findViewById(R.id.amount);
            timeAgoTextView = (TextView) view.findViewById(R.id.time_ago);
        }

        public TextView getSenderReceiverTextView() {
            return senderReceiverTextView;
        }

        public TextView getAmountTextView() {
            return amountTextView;
        }
        public TextView getTimeAgoTextView() {
            return timeAgoTextView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param repository TransactionRepository containing the data to populate views to be used
     * by RecyclerView.
     */
    public TransactionListRecyclerViewAdapter(TransactionRepository repository) {
        this.respository = repository;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaction_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Zeige Transaktionen in ungekehrter Reihenfolge (neuste zu älteste) an
        Transaction t = respository.getAll().get(getItemCount() - position - 1);

        viewHolder.getSenderReceiverTextView().setText(
                t.getFromUser().getUsername() + " 👉 " + t.getToUser().getUsername()
        );

        viewHolder.getAmountTextView().setText(t.getAmount() + "€");

        // Calculate "x time ago" String
        // Source: https://stackoverflow.com/a/26640002/10590162
        long sent = t.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(sent);
        viewHolder.getTimeAgoTextView().setText(timeAgo);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return respository.getAll().size();
    }
}
