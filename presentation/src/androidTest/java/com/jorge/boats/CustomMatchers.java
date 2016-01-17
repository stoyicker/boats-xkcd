package com.jorge.boats;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class CustomMatchers {

  private CustomMatchers() {
    throw new IllegalAccessError("No instances.");
  }

  public static Matcher<View> withText(final @NonNull Matcher<String> expectedMatcher) {
    return new BoundedMatcher<View, TextView>(TextView.class) {

      @Override protected boolean matchesSafely(final @NonNull TextView item) {
        return expectedMatcher.matches(item.getText().toString());
      }

      @Override public void describeTo(final Description description) {
        description.appendText("with text: ");
        expectedMatcher.describeTo(description);
      }
    };
  }
}
