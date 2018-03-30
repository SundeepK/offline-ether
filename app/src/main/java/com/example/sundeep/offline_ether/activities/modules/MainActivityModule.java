package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.activities.MainActivity;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.di.PerActivity;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.presenters.MainPresenter;
import com.example.sundeep.offline_ether.mvc.views.MainView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public abstract class MainActivityModule {

    @Provides
    static MainPresenter provideMainPresenter(MainView mainView, EtherApi etherApi, BoxStore boxStore) {
        return new MainPresenter(etherApi, boxStore.boxFor(EtherAddress.class), mainView);
    }

    @Binds
    abstract MainView provideMainView(MainActivity mainActivity);

    @PerActivity
    @Provides
    static android.support.v4.app.FragmentManager providesFragmentManager(MainActivity mainActivity) {
        return mainActivity.getSupportFragmentManager();
    }

}
