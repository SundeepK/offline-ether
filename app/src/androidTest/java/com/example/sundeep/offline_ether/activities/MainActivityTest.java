package com.example.sundeep.offline_ether.activities;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.TestApp;
import com.example.sundeep.offline_ether.api.ether.EtherApi;
import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.example.sundeep.offline_ether.fragments.BalanceCurrencyFragment;
import com.example.sundeep.offline_ether.fragments.BalanceEtherFragment;
import com.example.sundeep.offline_ether.mvc.presenters.BalanceEtherPresenter;
import com.example.sundeep.offline_ether.mvc.presenters.MainPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.sundeep.offline_ether.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Inject
    MainPresenter mainPresenter;

    @Inject
    BalanceEtherPresenter balanceEtherPresenter;

    @Inject
    EtherApi etherApi;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TestApp app
                = (TestApp) instrumentation.getTargetContext().getApplicationContext();
        app.getAppComponent().inject(this);
    }

    @Test
    public void testItShowsAddresses() throws Exception {
        EtherAddress add1 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("1").setBalance("1").build();
        EtherAddress add2 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("2").setBalance("2").build();
        List<EtherAddress> etherAddresses = Arrays.asList(add1, add2);

        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(etherAddresses));
        onView(withId(R.id.address_recycler_view)).check(withItemCount(2));
        onView(withId(R.id.address_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testItShowsAddAddressActivity() throws InterruptedException {
        Intents.init();
        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().onAddAccount());

        intended(hasComponent(AddressScannerActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testItShowsNoAddressMessageIfNoAddressesAreRegistered() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(new ArrayList<>()));
        onView(withId(R.id.no_address_message)).check(matches(isDisplayed()));
    }

    @Test
    public void testItShowsEtherBalance() throws InterruptedException {
        BalanceEtherFragment fragment = (BalanceEtherFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(0);
        activityRule.getActivity().runOnUiThread(() -> fragment.onEtherBalanceLoad("17Eth"));
        onView(withText("17Eth")).check(matches(isDisplayed()));
    }

    @Test
    public void testItShowsCurrencyBalance() throws InterruptedException {
        BalanceCurrencyFragment fragment = (BalanceCurrencyFragment) activityRule.getActivity().getSupportFragmentManager().getFragments().get(1);
        activityRule.getActivity().runOnUiThread(() -> fragment.onTotalBalance("£12,000"));
        onView(withId(R.id.next_currency_button)).perform(click());
        onView(withText("£12,000")).check(matches(isDisplayed()));
    }

}