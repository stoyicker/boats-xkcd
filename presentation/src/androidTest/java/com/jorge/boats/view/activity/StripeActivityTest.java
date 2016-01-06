package com.jorge.boats.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;
import com.jorge.boats.R;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.view.stripe.StripeActivity;
import rx.observers.TestSubscriber;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class StripeActivityTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static final long STUB_STRIPE_NUM = 123;
  private static final long LOAD_WAIT_TIME_MILLISECONDS = 1000;
  private final DomainStripe mStubObject = new DomainStripe();

  private StripeActivity mSut;

  public StripeActivityTest() {
    super(StripeActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    this.mSut = getActivity();

    this.mStubObject.setTitle("Centrifugal Force");
  }

  @NonNull private Intent createTargetIntent() {
    return StripeActivity.getCallingIntent(getInstrumentation().getTargetContext(),
        STUB_STRIPE_NUM);
  }

  public void testStripeTitle() {
    final ViewInteraction viewInteraction = onView(withId(R.id.toolbar_title));

    viewInteraction.check(matches(isDisplayed()));
    viewInteraction.check(matches(withText(mStubObject.getTitle())));
  }

  /**
   * This should be avoided if possible. The reason why it is used this time is because usage of
   * RxJava requires that we either use something like {@link TestSubscriber}, which we <i>can't
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
