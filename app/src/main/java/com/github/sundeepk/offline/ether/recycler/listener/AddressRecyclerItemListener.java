package com.github.sundeepk.offline.ether.recycler.listener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.activities.AccountActivity;
import com.github.sundeepk.offline.ether.entities.EtherAddress;

import java.util.List;

import static com.github.sundeepk.offline.ether.Constants.PUBLIC_ADDRESS;

public class AddressRecyclerItemListener implements RecyclerItemClickListener.OnItemClickListener {

    private final Activity activity;
    private final List<EtherAddress> addressList;
    private final OnAccountDeleteListener onAccountDeleteListener;

    public interface OnAccountDeleteListener {
        public void onAccountDelete(EtherAddress etherAddress);
    }

    public AddressRecyclerItemListener(Activity activity, List<EtherAddress> addressList, OnAccountDeleteListener onAccountDeleteListener) {
        this.activity = activity;
        this.addressList = addressList;
        this.onAccountDeleteListener = onAccountDeleteListener;
    }

    @Override
    public void onItemClick(View view, int position, MotionEvent e) {
        ImageButton btnMore = view.findViewById(R.id.more_options);
        if (!AddressRecyclerItemListener.isViewClicked(btnMore, e)) {
            ImageView img = view.findViewById(R.id.address_photo);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, img, ViewCompat.getTransitionName(img));
            Intent intent = new Intent(activity, AccountActivity.class);
            intent.putExtra(PUBLIC_ADDRESS, addressList.get(position).getAddress());
            activity.startActivity(intent, options.toBundle());
        } else {
            showPopupMenu(btnMore, position);
        }
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.address_cardview_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(getMenuListener(position));
        popup.show();
    }

    private PopupMenu.OnMenuItemClickListener getMenuListener(int position) {
        return item -> {
            if (R.id.delete_address == item.getItemId()) {
                onAccountDeleteListener.onAccountDelete(addressList.get(position));
            }
            return false;
        };
    }

    @Override
    public void onLongItemClick(View view, int position) {
    }

    public static boolean isViewClicked(View view, MotionEvent e) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect.contains((int) e.getRawX(), (int) e.getRawY());
    }

}
