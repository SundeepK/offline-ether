package com.github.sundeepk.offline.ether.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.offline.ether.R;
import com.github.sundeepk.offline.ether.adapters.OfflineFlowFragmentAdapter;
import com.github.sundeepk.offline.ether.entities.GasPrice;
import com.github.sundeepk.offline.ether.entities.Nonce;
import com.github.sundeepk.offline.ether.fragments.GasFragment;

import dagger.android.support.DaggerAppCompatActivity;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.github.sundeepk.offline.ether.Constants.GAS_PRICE;
import static com.github.sundeepk.offline.ether.Constants.NONCE;
import static com.github.sundeepk.offline.ether.Constants.PUBLIC_ADDRESS;
import static com.github.sundeepk.offline.ether.Constants.TYPE;
import static com.github.sundeepk.offline.ether.Constants.WAIT_TIME;

public class OfflineTransactionActivity extends DaggerAppCompatActivity implements GasFragment.OnGasSelectedListener {

    private static final String TAG = "OfflineTransact";
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int layouts = 3;
    private FancyButton btnBack;
    private FancyButton btnNext;
    private FragmentPagerAdapter offlineFlowFragmentAdapter;
    private Bundle sharedBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.offline_transaction);
        String address = getIntent().getStringExtra(PUBLIC_ADDRESS);
        sharedBundle.putString(PUBLIC_ADDRESS, address);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
        btnBack.setVisibility(View.GONE);

        // adding bottom dots
        addBottomDots(0);

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        offlineFlowFragmentAdapter = new OfflineFlowFragmentAdapter(getSupportFragmentManager(), address, sharedBundle);
        viewPager.setAdapter(offlineFlowFragmentAdapter);

        btnNext.setText("Skip");
        btnNext.setOnClickListener(v -> viewPager.setCurrentItem(2));
        btnBack.setOnClickListener(v -> {
            if (sharedBundle.getString(GAS_PRICE) == null) {
                viewPager.setCurrentItem(0);
            } else {
                int current = getItem(-1);
                if (current >= 0) {
                    viewPager.setCurrentItem(current);
                }
            }
        });
    }

    private void setScrollNextButton() {
        btnNext.setOnClickListener(v -> {
            int current = getItem(+1);
            if (current < layouts) {
                viewPager.setCurrentItem(current);
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == 0) {
                btnBack.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                if (sharedBundle.getString(GAS_PRICE) == null) {
                    btnNext.setText("Skip");
                } else {
                    btnNext.setText("Next");
                }
            } else {
                if(position == 1){
                    btnNext.setVisibility(View.VISIBLE);
                    btnNext.setText("Scan QR");
                } else if (position > 1) {
                    btnNext.setVisibility(View.GONE);
                }
                btnBack.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onGasSelected(GasPrice gasPrice, Nonce nonce) {
        Log.d(TAG, "Nonce " + nonce.getNonce());
        sharedBundle.putFloat(GAS_PRICE, gasPrice.getGasPrice());
        sharedBundle.putFloat(WAIT_TIME, gasPrice.getWaitTime());
        sharedBundle.putString(TYPE, gasPrice.getType());
        sharedBundle.putString(NONCE, nonce.getNonce());
        btnNext.setText("Next");
        btnNext.setEnabled(true);
        setScrollNextButton();
    }
}
