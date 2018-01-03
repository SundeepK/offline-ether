package com.example.sundeep.offline_ether.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.EtherTransaction;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.EtherTransactionViewHolder> {

    private final static String TAG  = "AddressAdapter";
    private List<EtherTransaction> etherTransactions;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E d MMM yyyy H:m");

    public TransactionsAdapter(List<EtherTransaction> etherTransactions){
        this.etherTransactions = etherTransactions;
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
        Date date = new Date(etherTransactions.get(i).getTimeStamp() * 1000);
        viewHolder.date.setText(dateFormat.format(date));
        BigDecimal balance = new BigDecimal(etherTransactions.get(i).getValue());
        viewHolder.value.setText(balance.divide(new BigDecimal("10E18"), 4, BigDecimal.ROUND_HALF_UP).toString() + " ETH".toString());
        viewHolder.confirmationsTextView.setText(etherTransactions.get(i).getConfirmations() + "");
    }

    public static class EtherTransactionViewHolder extends RecyclerView.ViewHolder {
        TextView confirmationsTextView;
        TextView date;
        TextView value;
        TextView isOutGoing;
        ImageView confirmations;

        EtherTransactionViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            value = itemView.findViewById(R.id.value);
            isOutGoing = itemView.findViewById(R.id.isOutGoing);
            confirmationsTextView = itemView.findViewById(R.id.confirmations_textview);
            confirmations = itemView.findViewById(R.id.confirmations_imageview);
        }
    }

}
