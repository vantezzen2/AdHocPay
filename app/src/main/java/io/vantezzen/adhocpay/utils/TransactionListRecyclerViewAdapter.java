package io.vantezzen.adhocpay.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.vantezzen.adhocpay.R;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;

/**
 * RecyclerView Adapter for the Transaction List
 * Based on the example adapter on https://developer.android.com/guide/topics/ui/layout/recyclerview
 */
public class TransactionListRecyclerViewAdapter extends RecyclerView.Adapter<TransactionListRecyclerViewAdapter.ViewHolder> {
    private final TransactionRepository respository;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView senderTextView;
        private final TextView receiverTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            senderTextView = (TextView) view.findViewById(R.id.sender);
            receiverTextView = (TextView) view.findViewById(R.id.receiver);
        }

        public TextView getSenderTextView() {
            return senderTextView;
        }

        public TextView getReceiverTextView() {
            return receiverTextView;
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
        Transaction t = respository.getAll().get(position);
        viewHolder.getSenderTextView().setText(t.getFromUser().getUsername());
        viewHolder.getReceiverTextView().setText(t.getToUser().getUsername());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return respository.getAll().size();
    }
}
