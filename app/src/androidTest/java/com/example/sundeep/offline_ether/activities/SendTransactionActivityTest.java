package com.example.sundeep.offline_ether.activities;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sundeep.offline_ether.R;
import com.example.sundeep.offline_ether.TestApp;
import com.example.sundeep.offline_ether.entities.SentTransaction;
import com.example.sundeep.offline_ether.entities.TransactionError;
import com.example.sundeep.offline_ether.mvc.presenters.SendTransactionPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.sundeep.offline_ether.Constants.SIGNED_TRANSACTION;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SendTransactionActivityTest {

    private static final String TRANSACTION_1 = "transaction1";

    @Inject
    SendTransactionPresenter sendTransactionPresenter;

    @Rule
    public ActivityTestRule<SendTransactionActivity> activityRule =
            new ActivityTestRule<SendTransactionActivity>(SendTransactionActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent result = new Intent();
                    result.putExtra(SIGNED_TRANSACTION, TRANSACTION_1);
                    return result;
                }
            };

    @Before
    public void setUp(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TestApp app
                = (TestApp) instrumentation.getTargetContext().getApplicationContext();
        app.getAppComponent().inject(this);
    }

    @Test
    public void testItSendsTransaction() throws Exception {
        onView(withId(R.id.send_transaction_button)).perform(click());

        verify(sendTransactionPresenter).sendTransaction(TRANSACTION_1);
    }

    @Test
    public void testItShowsProgressBeforeSending() throws Exception {
        SendTransactionActivity activity = activityRule.getActivity();

        activity.runOnUiThread(activity::beforeSendingTransaction);

        onView(withId(R.id.send_transaction_progress)).check(matches(isDisplayed()));
        onView(withId(R.id.send_transaction_button)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testItShowsSuccessWhenTransactionIsSent() throws Exception {
        SendTransactionActivity activity = activityRule.getActivity();

        activity.runOnUiThread(activity::beforeSendingTransaction);
        activity.runOnUiThread(() -> activity.onTransactionSent(new SentTransaction(null, "Success")));

        onView(withId(R.id.send_transaction_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.transaction_send_msg_textview)).check(matches(withText("Transaction successfully sent.")));

        onView(withText("OK")).perform(click());

        assertTrue(activityRule.getActivity().isFinishing());
    }

    @Test
    public void testItErrorWhenUnableToSendTransactions() throws Exception {
        SendTransactionActivity activity = activityRule.getActivity();

        activity.runOnUiThread(() -> activity.onTransactionSent(new SentTransaction(new TransactionError(500, "Error"), "")));

        onView(withId(R.id.send_transaction_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.transaction_send_msg_textview)).check(matches(withText("Error occurred sending transaction. \nError")));
    }



}