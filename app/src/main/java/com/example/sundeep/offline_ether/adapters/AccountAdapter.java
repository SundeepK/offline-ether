package com.example.sundeep.offline_ether.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.blockies.Blockies;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.utils.EtherMath;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.EtherAddressViewHolder>{

    private List<EtherAddress> etherAddresses;
    private final static String TAG  = "AddressAdapter";

    public AccountAdapter(List<EtherAddress> etherAddresses){
        this.etherAddresses = etherAddresses;
    }

    @Override
    public int getItemCount() {
        return etherAddresses.size();
    }

    @Override
    public EtherAddressViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: " + i);

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_item, viewGroup, false);
        EtherAddressViewHolder pvh = new EtherAddressViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(EtherAddressViewHolder viewHolder, int i) {
        Log.d(TAG, "render: " + i);
        EtherAddress etherAddress = etherAddresses.get(i);
        viewHolder.address.setText(etherAddress.getAddress());
        viewHolder.balance.setText(EtherMath.weiAsEtherStr(etherAddress.getBalance()));
        viewHolder.addressPhoto.setImageBitmap(Blockies.createIcon(etherAddress.getAddress(), new Blockies.BlockiesOpts(30, 10, 10)));
    }

    public static class EtherAddressViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView lastTransaction;
        TextView balance;
        ImageView addressPhoto;

        EtherAddressViewHolder(View itemView) {
            super(itemView);
            address = (TextView)itemView.findViewById(R.id.address);
            lastTransaction = (TextView)itemView.findViewById(R.id.last_transaction);
            balance = (TextView)itemView.findViewById(R.id.balance);
            addressPhoto = (ImageView)itemView.findViewById(R.id.address_photo);
        }
    }

}
