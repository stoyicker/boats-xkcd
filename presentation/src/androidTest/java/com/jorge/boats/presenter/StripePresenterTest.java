package com.jorge.boats.presenter;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.jorge.boats.UIThread;
import com.jorge.boats.data.executor.JobExecutor;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.executor.PostExecutionThread;
import com.jorge.boats.domain.executor.ThreadExecutor;
import com.jorge.boats.task.TypefaceLoadTask;
import com.jorge.boats.view.stripe.StripeActivity;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class StripePresenterTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static final long STRIPE_ID_INVALID = -1;
  private static final long STRIPE_ID_CURRENT = DomainStripe.STRIPE_NUM_CURRENT;
  private static final long STRIPE_ID_ARBITRARY = 123;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  private StripePresenter mSut;

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
    final StripeActivity stripeActivity = getActivity();

    mSut = new StripePresenter(
        new TypefaceLoadTask(getInstrumentation().getTargetContext(), mJobExecutor, mUiThread));
    mSut.setView(stripeActivity);
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

  private void testInitialize(final long stripeNum) {
    mSut.initialize(stripeNum);
  }
}
