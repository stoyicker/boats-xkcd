package com.jorge.boats;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.jorge.boats.view.stripe.StripeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OpeningActivityTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  public OpeningActivityTest() {
    super(StripeActivity.class);
  }

  private Intent createTargetIntent() {
    return new Intent(getInstrumentation().getContext(), StripeActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    getActivity();
  }

  public void test_ActivityNotNull() {
    onView(withId(android.R.id.content)).check(matches(notNullValue()));
  }

  public void test_ActivityDisplayed() {
    onView(withId(android.R.id.content)).check(matches(isDisplayed()));
  }
}
