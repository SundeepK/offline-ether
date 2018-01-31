package com.example.sundeep.offline_ether;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.example.sundeep.offline_ether.activities.AccountActivity;
import com.example.sundeep.offline_ether.activities.RecyclerItemClickListener;
import com.example.sundeep.offline_ether.entities.EtherAddress;

import java.util.List;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class AddressRecyclerItemListener implements RecyclerItemClickListener.OnItemClickListener {

    private final Activity activity;
    private final List<EtherAddress> addressList;

    public AddressRecyclerItemListener(Activity activity, List<EtherAddress> addressList) {
        this.activity = activity;
        this.addressList = addressList;
    }


    @Override
    public void onItemClick(View view, int position) {
        ImageView img = view.findViewById(R.id.address_photo);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, img, ViewCompat.getTransitionName(img));
        Intent intent = new Intent(activity, AccountActivity.class);
        intent.putExtra(PUBLIC_ADDRESS, addressList.get(position).getAddress());
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    public void onLongItemClick(View view, int position) {
    }

}
