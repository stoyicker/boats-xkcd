package com.jorge.boats.presenter;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
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

public class StripePresenterTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static final long STRIPE_ID_INVALID = -1;
  private static final long STRIPE_ID_CURRENT = StripePresenter.STRIPE_ID_CURRENT;
  private static final long STRIPE_ID_ARBITRARY = 123;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  private StripePresenter mSut;

  //TODO Rethink how to setup the view (mocking requires a custom runner, which is not possible here)
  @Mock private StripeView mockView;

  private final PostExecutionThread mUiThread = new UIThread();
  private final ThreadExecutor mJobExecutor = new JobExecutor();

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

    mSut = new StripePresenter(
        new TypefaceLoadTask(getInstrumentation().getTargetContext(), mJobExecutor, mUiThread));
    mSut.setView(mockView);
  }

  public void test_InitializeIdCurrent() {
    testInitialize(STRIPE_ID_CURRENT);
  }

  public void test_InitializeIdValidArbitrary() {
    testInitialize(STRIPE_ID_ARBITRARY);
  }

  public void test_initializeIdInvalid() {
    mExceptionExpectation.expect(IllegalArgumentException.class);
    testInitialize(STRIPE_ID_INVALID);
  }

  private void testInitialize(final long stripeId) {
    mSut.initialize(stripeId);
  }
}
