package com.example.sundeep.offline_ether.activities.modules;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.example.sundeep.offline_ether.activities.AccountActivity;
import com.example.sundeep.offline_ether.di.PerActivity;
import com.example.sundeep.offline_ether.mvc.views.AccountView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AccountActivityModule {

    @PerActivity
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(AccountActivity accountActivity) {
        return new LinearLayoutManager(accountActivity);
    }

    @PerActivity
    @Provides
    static DividerItemDecoration provideDividerItemDecoration(AccountActivity accountActivity, LinearLayoutManager linearLayoutManager) {
        return new DividerItemDecoration(accountActivity, linearLayoutManager.getOrientation());
    }

    @Binds
    abstract AccountView provideMainView(AccountActivity mainActivity);

}
