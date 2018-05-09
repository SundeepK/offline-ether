package com.example.sundeep.offline_ether.activities.modules;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.blockies.BlockieFactory;
import com.example.sundeep.offline_ether.mvc.presenters.AddressAdderPresenter;
import com.example.sundeep.offline_ether.mvc.views.AddressAdderView;
import com.example.sundeep.offline_ether.objectbox.AddressRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressAdderPresenterModule {

    @Provides
    public AddressAdderPresenter provideMainPresenter(AddressAdderView addressAdderView,
                                                      AddressRepository addressRepository,
                                                      EtherApi etherApi,
                                                      BlockieFactory blockieFactory) {
        return new AddressAdderPresenter(addressRepository, addressAdderView, etherApi, blockieFactory);
    }

}
