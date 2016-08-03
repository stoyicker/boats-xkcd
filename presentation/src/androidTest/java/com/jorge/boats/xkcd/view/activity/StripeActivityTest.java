package com.jorge.boats.xkcd.view.activity;

import com.jorge.boats.xkcd.CustomViewMatchers;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.view.stripe.StripeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Note that, because I'm loading a real stripe, I have to assume that the load works correctly so
 * only positive cases are tested.
 */
public class StripeActivityTest extends ActivityInstrumentationTestCase2<StripeActivity> {

    private static final long STUB_STRIPE_NUM = 123;
    private static final long LOAD_WAIT_TIME_MILLISECONDS = 1000;

    private final DomainStripe mStubObject = new DomainStripe();

    private Activity mActivity;

    public StripeActivityTest() {
        super(StripeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        mActivity = getActivity();

        this.mStubObject.setTitle(
                getInstrumentation().getTargetContext().getString(R.string.stub_stripe_title));
        this.mStubObject.setAlt(
                getInstrumentation().getTargetContext().getString(R.string.stub_stripe_description));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (mActivity != null) mActivity.finish();
    }

    @NonNull
    private Intent createTargetIntent() {
        return StripeActivity.getCallingIntent(getInstrumentation().getTargetContext(),
                STUB_STRIPE_NUM);
    }

    public void testPrevious() {
        final ViewInteraction titleInteraction = onView(withId(R.id.title)), descriptionInteraction =
                onView(withId(R.id.description));

        waitForLoad();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mActivity.findViewById(R.id.fab_index_zero).performClick();
            }
        });

        titleInteraction.check(matches(CustomViewMatchers.withText(equalTo(
                getInstrumentation().getTargetContext().getString(R.string.stub_previous_stripe_title)))));
        descriptionInteraction.check(matches(CustomViewMatchers.withText(equalTo(
                getInstrumentation().getTargetContext().getString(R.string.stub_previous_stripe_description)))));

        onView(withId(R.id.retry)).check(matches(not((isDisplayed()))));
    }

    public void testNext() {
        final ViewInteraction titleInteraction = onView(withId(R.id.title)), descriptionInteraction =
                onView(withId(R.id.description));

        waitForLoad();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mActivity.findViewById(R.id.fab_index_three).performClick();
            }
        });

        titleInteraction.check(matches(CustomViewMatchers.withText(equalTo(
                getInstrumentation().getTargetContext().getString(R.string.stub_next_stripe_title)))));
        descriptionInteraction.check(matches(CustomViewMatchers.withText(equalTo(
                getInstrumentation().getTargetContext().getString(R.string.stub_next_stripe_description)))));

        onView(withId(R.id.retry)).check(matches(not((isDisplayed()))));
    }

    /**
     * This should be avoided if possible. The reason why it is used this time is because usage of
     * RxJava requires that we either use something like a TestSubscriber, which we <i>can't
     * because we don't have access to the UC</i>, mock the repository with Mockito and inject it
     * to
     * the activity (which I'm not fully sure how I would do just yet) for usage with
     * rxPresso (recommended) or wait to ensure completion, which is the option chosen.
     */
    private static void waitForLoad() {
        try {
            Thread.sleep(LOAD_WAIT_TIME_MILLISECONDS);
        } catch (final InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }
}
