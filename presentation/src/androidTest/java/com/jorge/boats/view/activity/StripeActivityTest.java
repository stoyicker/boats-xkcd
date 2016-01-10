package com.jorge.boats.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;
import com.jorge.boats.R;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.view.stripe.StripeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class StripeActivityTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static final long STUB_STRIPE_NUM = 123;
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
    titleInteraction.check(matches(withText(mStubObject.getTitle())));

    onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
  }
}
