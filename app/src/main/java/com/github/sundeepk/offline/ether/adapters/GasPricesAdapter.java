package com.github.sundeepk.offline.ether.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.entities.GasPrice;

import java.text.SimpleDateFormat;
import java.util.List;

public class GasPricesAdapter extends RecyclerView.Adapter<GasPricesAdapter.GasPriceViewHolder> {

    private final static String TAG  = "AddressAdapter";
    private List<GasPrice> gasPrices;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E d MMM yyyy H:m");

    public GasPricesAdapter(List<GasPrice> gasPrices){
        this.gasPrices = gasPrices;
    }

    @Override
    public int getItemCount() {
        return gasPrices.size();
    }

    @Override
    public GasPriceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: " + i);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gas_price_item, viewGroup, false);
        return new GasPriceViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(GasPriceViewHolder viewHolder, int position) {
        GasPrice gasPrice = gasPrices.get(position);
        viewHolder.gasTitle.setText(gasPrice.getType());
        viewHolder.gasWait.setText(gasPrice.getWaitTime() + "mins");
        viewHolder.gasPrice.setText(gasPrice.getGasPrice() + "Gwei");
        viewHolder.itemView.setBackgroundColor(gasPrice.isSelected()? Color.parseColor("#FF212121") : Color.TRANSPARENT);
    }

    public static class GasPriceViewHolder extends RecyclerView.ViewHolder {
        TextView gasTitle;
        TextView gasWait;
        TextView gasPrice;

        GasPriceViewHolder(View itemView) {
            super(itemView);
            gasWait = itemView.findViewById(R.id.gasWait);
            gasTitle = itemView.findViewById(R.id.gasTitle);
            gasPrice = itemView.findViewById(R.id.gasPrice);
        }

    }

}
