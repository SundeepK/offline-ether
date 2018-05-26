package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.activities.AddressAdderActivity;
import com.github.sundeepk.offline.ether.mvc.views.AddressAdderView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AddressAdderActivityModule {

    @Binds
    abstract AddressAdderView provideMainView(AddressAdderActivity addressAdderActivity);


}
