package com.github.sundeepk.offline.ether.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.sundeepk.offline.ether.fragments.BalanceCurrencyFragment;
import com.github.sundeepk.offline.ether.fragments.BalanceEtherFragment;

import javax.inject.Inject;

public class BalanceSlidePagerAdapter extends FragmentStatePagerAdapter {

    @Inject
    public BalanceSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BalanceEtherFragment();
            case 1:
                return new BalanceCurrencyFragment();
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