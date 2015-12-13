package com.jorge.boats.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;
import com.jorge.boats.R;
import com.jorge.boats.UIThread;
import com.jorge.boats.data.executor.JobExecutor;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.io.task.TypefaceLoadTask;
import com.jorge.boats.view.stripe.StripeActivity;
import com.jorge.boats.view.stripe.StripeView;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import rx.observers.TestSubscriber;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.verify;

public class StripePresenterTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static long STRIPE_ID_CURRENT = StripePresenter.STRIPE_ID_CURRENT;
  private static long STRIPE_ID_ARBITRARY = 123;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  private StripePresenter mSut;

  //TODO Rethink how to setup mockups (requires a custom runner, which is not possible here)
  @Mock private StripeView mockView;

  private final PostExecutionThread mUiThread = new UIThread();
  private final ThreadExecutor mJobExecutor = new JobExecutor();

  private TypefaceLoadTask mTypefaceLoadTask;

  public StripePresenterTest() {
    super(StripeActivity.class);
  }

  private Intent createTargetIntent() {
    return new Intent(getInstrumentation().getContext(), StripeActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    getActivity();

    mTypefaceLoadTask =
        new TypefaceLoadTask(getInstrumentation().getContext(), mJobExecutor, mUiThread);
    mSut = new StripePresenter(mTypefaceLoadTask);
    mSut.setView(mockView);
  }

  public void test_TitleTypefaceTaskCurrent() {
    test_TitleTypefaceTask(STRIPE_ID_CURRENT);
  }

  public void test_TitleTypefaceTaskArbitrary() {
    test_TitleTypefaceTask(STRIPE_ID_ARBITRARY);
  }

  private void test_TitleTypefaceTask(final long stripeId) {
    @SuppressWarnings("unchecked") final TestSubscriber<Typeface> typefaceLoadTaskTestSubscriber =
        new TestSubscriber();

    if (stripeId < STRIPE_ID_CURRENT) mExceptionExpectation.expect(IllegalArgumentException.class);
    mSut.initialize(stripeId);

    verify(mTypefaceLoadTask).execute(typefaceLoadTaskTestSubscriber);

    typefaceLoadTaskTestSubscriber.awaitTerminalEvent();
    typefaceLoadTaskTestSubscriber.assertNoErrors();
    typefaceLoadTaskTestSubscriber.assertCompleted();

    onView(withId(R.id.toolbar_title)).check(new ViewAssertion() {

      @Override public void check(final @NonNull View titleView,
          final NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) throw noViewFoundException;

        final Context context = StripePresenterTest.this.getInstrumentation().getContext();

        assertEquals(
            Typeface.createFromAsset(context.getAssets(), context.getString(R.string.app_font)),
            ((TextView) titleView).getTypeface());
      }
    });
  }
}
