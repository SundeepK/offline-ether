package com.github.sundeepk.offline.ether.mvc.presenters;

import android.support.v4.view.ViewPager;

import com.github.sundeepk.offline.ether.mvc.views.MainView;

public class CurrencySelectedPageListener implements ViewPager.OnPageChangeListener {

    private final MainView mainView;

    public CurrencySelectedPageListener(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mainView.onEtherSelected();
        } else {
            mainView.onCurrencySelected();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
