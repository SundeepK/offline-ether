package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.mvc.presenters.MainPresenter;
import com.example.sundeep.offline_ether.mvc.views.MainView;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

@Module
public class MainPresenterModule {

    @Provides
    public MainPresenter provideMainPresenter(MainView mainView, EtherApi etherApi, BoxStore boxStore) {
        return new MainPresenter(etherApi, boxStore.boxFor(EtherAddress.class), mainView);
    }

}
