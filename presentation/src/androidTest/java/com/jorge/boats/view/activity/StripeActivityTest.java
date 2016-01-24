package com.jorge.boats.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;
import com.jorge.boats.CustomViewMatchers;
import com.jorge.boats.R;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.view.stripe.StripeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class StripeActivityTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static final long STUB_STRIPE_NUM = 123;
  private static final long LOAD_WAIT_TIME_MILLISECONDS = 1000;
  private final DomainStripe mStubObject = new DomainStripe();

  public StripeActivityTest() {
    super(StripeActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    getActivity();

    this.mStubObject.setTitle(
        getInstrumentation().getTargetContext().getString(R.string.stub_stripe_title));
  }

  @NonNull private Intent createTargetIntent() {
    return StripeActivity.getCallingIntent(getInstrumentation().getTargetContext(),
        STUB_STRIPE_NUM);
  }

  public void testStripeLoaded() {
    final ViewInteraction titleInteraction = onView(withId(R.id.toolbar_title));

    titleInteraction.check(matches(isDisplayed()));

    waitForLoad();

    titleInteraction.check(
        matches(CustomViewMatchers.withText(equalTo(mStubObject.getTitle()))));

    onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
  }

  /**
   * This should be avoided if possible. The reason why it is used this time is because usage of
   * RxJava requires that we either use something like a TestSubscriber, which we <i>can't
   * because we don't have access to the UC</i>, mock the repository with Mockito and inject it to
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
