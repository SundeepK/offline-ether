package com.example.sundeep.offline_ether.mvc.presenters;

import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.entities.EtherTransaction;
import com.example.sundeep.offline_ether.mvc.views.AccountView;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.Property;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountPresenterTest {

    private static final String ETHER_ADDRESS = "ether:address";

    @Mock private AccountView accountView;
    @Mock private EtherApi etherApi;
    @Mock private QueryBuilder queryBuilder;
    @Mock private Query query;
    @Mock private SubscriptionBuilder subscriptionBuilder;
    @Mock private ToMany<EtherTransaction> toMany;
    @Mock private Box<EtherAddress> etherAddressBox;
    @Mock private DataSubscription subscription;

    private EtherAddress address;
    private AccountPresenter underTest;

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
                .setEtherTransactions(toMany)
                .build();
        givenEtherAddress(this::answerOneAddress);
        underTest = new AccountPresenter(accountView, etherAddressBox, ETHER_ADDRESS, etherApi);
    }

    @Test
    public void testItListensForTransactions() throws InterruptedException {
        verify(accountView).onTransactions(toMany);
    }

    @Test
    public void testItDoesNotCallViewWhenNoTransactions() throws InterruptedException {
        givenEtherAddress(this::zeroAddresses);

        verifyZeroInteractions(accountView);
    }

    @Test
    public void testItLoadsTransactions(){
        EtherTransaction eth1 = EtherTransaction.newBuilder().setHash("1").build();
        EtherTransaction eth2 = EtherTransaction.newBuilder().setHash("2").build();

        List<EtherTransaction> transactions = Arrays.asList(eth1, eth2);
        Observable<List<EtherTransaction>> observable = Observable.just(transactions);
        when(etherApi.getTransactions(ETHER_ADDRESS, 1)).thenReturn(observable);


        underTest.loadLast50Transactions();

        verify(etherAddressBox).put(address);
        verify(toMany).addAll(transactions);

    }

    private DataSubscription zeroAddresses(InvocationOnMock invocation) throws Throwable {
        DataObserver observer = (DataObserver) invocation.getArguments()[0];
        observer.onData(Collections.emptyList());
        return subscription;
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
        when(query.findFirst()).thenReturn(address);
        when(subscriptionBuilder.on(any())).thenReturn(subscriptionBuilder);
        when(subscriptionBuilder.onError(any())).thenReturn(subscriptionBuilder);
        when(queryBuilder.equal(any(Property.class), eq(ETHER_ADDRESS))).thenReturn(queryBuilder);
        when(etherAddressBox.query()).thenReturn(queryBuilder);

        underTest = new AccountPresenter(accountView, etherAddressBox, ETHER_ADDRESS, etherApi);
    }


}