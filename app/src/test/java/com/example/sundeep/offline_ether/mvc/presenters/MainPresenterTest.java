package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.Balance;
import com.example.sundeep.offline_ether.entities.Balances;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.mvc.views.MainView;
import com.google.common.collect.ImmutableSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.SubscriptionBuilder;
import io.objectbox.relation.ToMany;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {


    private static final String ETHER_ADDRESS = "ether:address";

    @Mock private MainView mainView;
    @Mock private EtherApi etherApi;
    @Mock private QueryBuilder queryBuilder;
    @Mock private Query query;
    @Mock private SubscriptionBuilder subscriptionBuilder;
    @Mock private ToMany<EtherTransaction> toMany;
    @Mock private Box<EtherAddress> etherAddressBox;
    @Mock private DataSubscription subscription;

    private MainPresenter underTest;
    private EtherAddress address;

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
        address = EtherAddress.newBuilder()
                .setBalance("1")
                .setAddress("add1")
                .setEtherTransactions(toMany)
                .build();
    }

    @Test
    public void testItListensForAddresses() throws InterruptedException {
        // given
        givenEtherAddress(this::answerOneAddress);

        // when
        underTest = new MainPresenter(etherApi, etherAddressBox, mainView);
        underTest.observeAddressChange();

        verify(mainView).loadBalances(Collections.singletonList(address));
    }

    @Test
    public void testItLoadsTransactions(){
        // given
        givenEtherAddress(this::answerOneAddress);

        // when
        underTest = new MainPresenter(etherApi, etherAddressBox, mainView);
        underTest.observeAddressChange();

        Balance balance = new Balance("add1", "2");

        Observable<Balances> observable = Observable.just(new Balances(Collections.singletonList(balance)));
        when(etherApi.getBalance(ImmutableSet.of("add1"))).thenReturn(observable);

        underTest.loadBalances();

        verify(etherAddressBox).put(Collections.singletonList(EtherAddress.newBuilder(address).setBalance("2").build()));

    }

    private DataSubscription answerOneAddress(InvocationOnMock invocation) throws Throwable {
        DataObserver observer = (DataObserver) invocation.getArguments()[0];

        observer.onData(Collections.singletonList(address));
        return subscription;
    }

    private void givenEtherAddress(Answer answers) {
        when(subscriptionBuilder.observer(any(DataObserver.class))).then(answers);
        when(query.subscribe()).thenReturn(subscriptionBuilder);
        when(queryBuilder.build()).thenReturn(query);
        when(query.find()).thenReturn(Collections.singletonList(address));
        when(subscriptionBuilder.on(any())).thenReturn(subscriptionBuilder);
        when(etherAddressBox.query()).thenReturn(queryBuilder);
    }


}