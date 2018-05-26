package com.github.sundeepk.offline.ether.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.sundeepk.offline.ether.fragments.GasFragment;
import com.github.sundeepk.offline.ether.fragments.OfflineTransaction;
import com.github.sundeepk.offline.ether.fragments.SendTransaction;

public class OfflineFlowFragmentAdapter extends FragmentPagerAdapter {

    private final String address;
    private final Bundle sharedBundle;
    private int NUM_ITEMS = 3;
    private String[] titles= new String[]{"First Fragment", "Second Fragment","Third Fragment"};

    public OfflineFlowFragmentAdapter(FragmentManager fm, String address, Bundle sharedBundle) {
        super(fm);
        this.address = address;
        this.sharedBundle = sharedBundle;
    }

    @Override
    public int getCount() {
        return  NUM_ITEMS ;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GasFragment gasFragment = new GasFragment();
                gasFragment.setArguments(sharedBundle);
                return gasFragment;
            case 1:
                OfflineTransaction offlineTransaction = new OfflineTransaction();
                offlineTransaction.setArguments(sharedBundle);
                return offlineTransaction;
            case 2:
                SendTransaction sendTransactionFragment = new SendTransaction();
                sendTransactionFragment.setArguments(sharedBundle);
                return sendTransactionFragment;
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return  titles[position];
    }

}