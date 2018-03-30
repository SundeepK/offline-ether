package com.example.sundeep.offline_ether.activities;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private static class MainActivityTestRule extends IntentsTestRule {

        private MainActivityTestRule() {
            super(MainActivity.class);

        }

        @Override public void beforeActivityLaunched() {
//            Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
//            App app = (App) instrumentation.getTargetContext().getApplicationContext();
//            ToMany toMany = Mockito.mock(ToMany.class);
//            SubscriptionBuilder subscriptionBuilder = Mockito.mock(SubscriptionBuilder.class);
//            Query query = Mockito.mock(Query.class);
//            QueryBuilder queryBuilder = Mockito.mock(QueryBuilder.class);
//            BoxStore boxStore = Mockito.mock(BoxStore.class);
//            Box box = Mockito.mock(Box.class);
//
//            when(boxStore.boxFor(EtherAddress.class)).thenReturn(box);
//            app.setBoxStore(boxStore);
//
//            when(subscriptionBuilder.observer(any(DataObserver.class))).then(answers);
//            when(query.subscribe()).thenReturn(subscriptionBuilder);
//            when(queryBuilder.build()).thenReturn(query);
//            when(query.findFirst()).thenReturn(address);
//            when(subscriptionBuilder.on(any())).thenReturn(subscriptionBuilder);
//            when(subscriptionBuilder.onError(any())).thenReturn(subscriptionBuilder);
//            when(queryBuilder.equal(any(Property.class), eq(ETHER_ADDRESS))).thenReturn(queryBuilder);
//            when(etherAddressBox.query()).thenReturn(queryBuilder);
        }

    }

    @Rule
    public IntentsTestRule<MainActivity> activityRule = new MainActivityTestRule();

    @Before
    public void setUp(){

    }

    @Test
    public void testItShowsAddresses() throws InterruptedException {
//        EtherAddress add1 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("1").setBalance("1").build();
//        EtherAddress add2 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("2").setBalance("2").build();
//        List<EtherAddress> etherAddresses = Arrays.asList(add1, add2);
//
//        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(etherAddresses));
//        onView(withId(R.id.address_recycler_view)).check(withItemCount(2));
//        onView(withId(R.id.address_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testItShowsAddAddressActivity() throws InterruptedException {
//        Intents.init();
//        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().onAddAccount());
//
//        intended(hasComponent(AddressScannerActivity.class.getName()));
//        Intents.release();
    }

    @Test
    public void testItShowsNoAddressMessageIfNoAddressesAreRegistered() throws InterruptedException {
//        App application = (App) activityRule.getActivity().getApplication();
////        application.setBoxStore(boxStore);
//        onView(withId(R.id.no_address_message)).check(matches(isDisplayed()));
    }



}