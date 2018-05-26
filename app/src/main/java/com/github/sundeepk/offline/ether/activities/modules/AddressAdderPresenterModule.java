package com.github.sundeepk.offline.ether.activities.modules;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.blockies.BlockieFactory;
import com.github.sundeepk.offline.ether.mvc.presenters.AddressAdderPresenter;
import com.github.sundeepk.offline.ether.mvc.views.AddressAdderView;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;

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
