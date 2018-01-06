package com.example.sundeep.offline_ether.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sundeep.offline_ether.fragments.BalanceCurrency;
import com.example.sundeep.offline_ether.fragments.BalanceEther;

public class BalanceSlidePagerAdapter extends FragmentStatePagerAdapter {
    public BalanceSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BalanceEther();
            case 1:
                return new BalanceCurrency();
            case 2:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}