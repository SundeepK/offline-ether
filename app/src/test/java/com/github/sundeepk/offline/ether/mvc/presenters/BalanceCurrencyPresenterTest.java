package com.github.sundeepk.offline.ether.mvc.presenters;

import com.github.sundeepk.offline.ether.api.ether.EtherApi;
import com.github.sundeepk.offline.ether.entities.EtherAddress;
import com.github.sundeepk.offline.ether.mvc.views.BalanceView;
import com.github.sundeepk.offline.ether.objectbox.AddressRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceCurrencyPresenterTest {

   @Mock private DataSubscription dataSubscription;
   @Mock private EtherApi etherApi;
   @Mock private AddressRepository addressRepository;
   @Mock private BalanceView balanceView;

   private BalanceCurrencyPresenter underTest;

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
       underTest = new BalanceCurrencyPresenter(etherApi, addressRepository, balanceView);
   }

   @Test
   public void testItLoadsTotalBalance(){
       Locale.setDefault(Locale.UK);
       Map<String, String> currToPrice = new HashMap<>();
       currToPrice.put("USD", "100");
       currToPrice.put("GBP", "80");
       Observable<Map<String, String>> observable = Observable.just(currToPrice);
       when(etherApi.getPrices()).thenReturn(observable);

       when(addressRepository.observeAddressesChanges(any())).thenAnswer(this::answer2Addresses);

       underTest.observeAddressChanges();

       verify(balanceView).onTotalBalance("£" + 80 * 3 + ".00");
   }

    @Test
    public void testItDefaultAsUsdIfCurrentLocaleIsNotFoundInPrices(){
        Locale.setDefault(Locale.FRANCE);
        Map<String, String> currToPrice = new HashMap<>();
        currToPrice.put("USD", "100");
        currToPrice.put("GBP", "80");
        Observable<Map<String, String>> observable = Observable.just(currToPrice);
        when(etherApi.getPrices()).thenReturn(observable);

        when(addressRepository.observeAddressesChanges(any())).thenAnswer(this::answer2Addresses);

        underTest.observeAddressChanges();

        verify(balanceView).onTotalBalance("$" + 100 * 3 + ".00");
    }

    public DataSubscription answer2Addresses(InvocationOnMock invocation) throws Throwable {
        DataObserver o = (DataObserver) invocation.getArguments()[0];
        EtherAddress address = EtherAddress.newBuilder()
                .setBalance("1000000000000000000")
                .build();

        EtherAddress address2 = EtherAddress.newBuilder()
                .setBalance("2000000000000000000")
                .build();

        o.onData(Arrays.asList(address, address2));
        return dataSubscription;
    }

}