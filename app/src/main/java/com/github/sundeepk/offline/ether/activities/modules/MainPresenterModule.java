package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.presenters.MainPresenter;
import com.github.sundeepk.offline.ether.mvc.views.MainView;

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
