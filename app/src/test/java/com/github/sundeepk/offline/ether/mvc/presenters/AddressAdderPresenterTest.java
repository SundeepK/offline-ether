package com.github.sundeepk.offline.ether.mvc.presenters;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.blockies.BlockieFactory;
import com.github.sundeepk.offline.ether.entities.Balance;
import com.github.sundeepk.offline.ether.entities.Balances;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.views.AddressAdderView;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressAdderPresenterTest {

    private static final byte[] BLOCKIE = new byte[]{1};

    @Mock private AddressRepository addressRepository;
    @Mock private AddressAdderView addressAdderView;
    @Mock private EtherApi etherApi;

    @Mock private BlockieFactory blockieFactory;
    private AddressAdderPresenter underTest;

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }

    @Before
    public void setUp(){
        when(blockieFactory.getBlockie(anyString())).thenReturn(BLOCKIE);
        underTest = new AddressAdderPresenter(addressRepository, addressAdderView, etherApi, blockieFactory);
    }

    @Test
    public void testItSavesNewAddress(){
        Balance balance = new Balance("add1", "2");

        Observable<Balances> observable = Observable.just(new Balances(Collections.singletonList(balance)));
        when(etherApi.getBalance(Collections.singletonList("add1"))).thenReturn(observable);

        underTest.saveAddress("add1");

        EtherAddress expected = EtherAddress.newBuilder()
                .setAddress("add1")
                .setBalance("2")
                .setBlockie(BLOCKIE)
                .build();

        verify(addressRepository).put(expected);
        verify(addressAdderView).onAddressUpdate(expected);
    }

    @Test
    public void testItUpdatesExistingAddress(){
        Balance balance = new Balance("add1", "2");
        EtherAddress address = EtherAddress.newBuilder()
                .setBalance("1")
                .setAddress("add1")
                .setId(1)
                .build();

        Observable<Balances> observable = Observable.just(new Balances(Collections.singletonList(balance)));
        when(etherApi.getBalance(Collections.singletonList("add1"))).thenReturn(observable);
        when(addressRepository.findOne("add1")).thenReturn(address);

        underTest.saveAddress("add1");

        EtherAddress expected = EtherAddress.newBuilder()
                .setAddress("add1")
                .setBalance("2")
                .setBlockie(BLOCKIE)
                .setId(1)
                .build();

        verify(addressRepository).put(expected);
        verify(addressAdderView).onAddressUpdate(expected);
    }
}