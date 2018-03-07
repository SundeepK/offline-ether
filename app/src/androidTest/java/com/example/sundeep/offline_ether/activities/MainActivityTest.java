package com.example.sundeep.offline_ether.activities;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.entities.EtherAddress;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.sundeep.offline_ether.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testItShowsAddresses() throws InterruptedException {
        EtherAddress add1 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("1").setBalance("1").build();
        EtherAddress add2 = EtherAddress.newBuilder().setBlockie(new byte[0]).setAddress("2").setBalance("2").build();
        List<EtherAddress> etherAddresses = Arrays.asList(add1, add2);

        activityRule.getActivity().runOnUiThread(() -> activityRule.getActivity().loadBalances(etherAddresses));
        onView(withId(R.id.address_recycler_view)).check(withItemCount(2));
        onView(withId(R.id.address_recycler_view)).check(matches(isDisplayed()));
    }

}