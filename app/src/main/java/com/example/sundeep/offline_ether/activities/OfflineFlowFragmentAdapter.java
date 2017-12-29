package com.example.sundeep.offline_ether.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sundeep.offline_ether.fragments.GasFragment;

import static com.example.sundeep.offline_ether.Constants.PUBLIC_ADDRESS;

public class OfflineFlowFragmentAdapter extends FragmentPagerAdapter {

    private final String address;
    private int NUM_ITEMS = 3;
    private String[] titles= new String[]{"First Fragment", "Second Fragment","Third Fragment"};

    public OfflineFlowFragmentAdapter(FragmentManager fm, String address) {
        super(fm);
        this.address = address;
    }

    @Override
    public int getCount() {
        return  NUM_ITEMS ;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putString(PUBLIC_ADDRESS, address);
        switch (position) {
            case 0:
                GasFragment gasFragment = new GasFragment();
                gasFragment.setArguments(args);
                return gasFragment;
            case 1:
                GasFragment gasFragment2 = new GasFragment();
                gasFragment2.setArguments(args);
                return gasFragment2;
            case 2:
                GasFragment gasFragment3 = new GasFragment();
                gasFragment3.setArguments(args);
                return gasFragment3;
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