package com.example.sundeep.offline_ether.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.EtherTransactionViewHolder> {

    private final static String TAG  = "TransactionsAdapter";
    private final Drawable outOrange;
    private final Drawable inDrawable;
    private final Drawable loadingDrawable;
    private final int padding;
    private final String address;
    private final List<EtherTransaction> etherTransactions;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("E d MMM yyyy H:m");

    public TransactionsAdapter(List<EtherTransaction> etherTransactions, String address,
                               Drawable outDrawable, Drawable inDrawable,
                               Drawable loadingDrawable, int padding){
        this.etherTransactions = etherTransactions;
        this.address = address;
        this.outOrange = outDrawable;
        this.inDrawable = inDrawable;
        this.loadingDrawable = loadingDrawable;
        this.padding = padding;
    }

    @Override
    public int getItemCount() {
        return etherTransactions.size();
    }

    @Override
    public TransactionsAdapter.EtherTransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: " + i);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_item, viewGroup, false);
        return new EtherTransactionViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(TransactionsAdapter.EtherTransactionViewHolder viewHolder, int i) {
        EtherTransaction etherTransaction = etherTransactions.get(i);
        Date date = new Date(etherTransaction.getTimeStamp() * 1000);
        viewHolder.date.setText(dateFormat.format(date));
        BigDecimal balance = new BigDecimal(etherTransaction.getValue());
        viewHolder.value.setText(balance.divide(new BigDecimal("10E18"), 4, BigDecimal.ROUND_HALF_UP).toString() + " ETH".toString());
        viewHolder.confirmationsTextView.setText(etherTransaction.getConfirmations() + "");
        if (etherTransaction.getConfirmations() <= 0) {
            viewHolder.inOrOut.setText("PEND");
            viewHolder.inOrOut.setBackground(loadingDrawable);
            viewHolder.shimmer.startShimmerAnimation();
        } else {
            if (etherTransaction.getFrom().equalsIgnoreCase(address)) {
                viewHolder.inOrOut.setBackground(outOrange);
                viewHolder.inOrOut.setText("OUT");
            } else {
                viewHolder.inOrOut.setText("IN");
                viewHolder.inOrOut.setBackground(inDrawable);
            }
        }
        viewHolder.inOrOut.setPadding(padding, padding, padding, padding);
    }

    public static class EtherTransactionViewHolder extends RecyclerView.ViewHolder {
        private final ShimmerFrameLayout shimmer;
        TextView confirmationsTextView;
        TextView date;
        TextView value;
        TextView isOutGoing;
        TextView inOrOut;

        EtherTransactionViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            value = itemView.findViewById(R.id.value);
            isOutGoing = itemView.findViewById(R.id.outgoing);
            confirmationsTextView = itemView.findViewById(R.id.confirmations_textview);
            inOrOut = itemView.findViewById(R.id.status_textview);
            shimmer = itemView.findViewById(R.id.shimmer_view_container);
        }
    }

}
