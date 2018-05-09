package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.activities.AddressAdderActivity;
import com.example.sundeep.offline_ether.mvc.views.AddressAdderView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AddressAdderActivityModule {

    @Binds
    abstract AddressAdderView provideMainView(AddressAdderActivity addressAdderActivity);


}
