package com.jorge.boats.navigation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.test.ActivityInstrumentationTestCase2;
import com.jorge.boats.R;
import com.jorge.boats.view.stripe.StripeActivity;

public class NavigationLayoutTest extends ActivityInstrumentationTestCase2<StripeActivity> {

  private static final long STUB_STRIPE_NUM = 123;
  private static final long LOAD_WAIT_TIME_MILLISECONDS = 1000;
  private static final long SHARE_WAIT_TIME_MILLISECONDS = 5000;

  private Activity mActivity;

  public NavigationLayoutTest() {
    super(StripeActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());

    mActivity = getActivity();
  }

  @NonNull private Intent createTargetIntent() {
    return StripeActivity.getCallingIntent(getInstrumentation().getTargetContext(),
        STUB_STRIPE_NUM);
  }

  public void testShare() {
    final IntentFilter intentFilter = new IntentFilter();
    final Instrumentation.ActivityMonitor receiverActivityMonitor =
        getInstrumentation().addMonitor(intentFilter, null, false);

    intentFilter.addAction(Intent.ACTION_CHOOSER);

    waitForLoad();

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override public void run() {
        mActivity.findViewById(R.id.fab_index_one).performClick();
      }
    });

    assertNotNull(receiverActivityMonitor.waitForActivityWithTimeout(SHARE_WAIT_TIME_MILLISECONDS));
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
