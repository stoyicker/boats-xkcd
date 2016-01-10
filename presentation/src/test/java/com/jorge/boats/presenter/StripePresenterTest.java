package com.jorge.boats.presenter;

import com.jorge.boats.PresentationModuleTestCase;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.mapper.PresentationEntityMapper;
import com.jorge.boats.task.TypefaceLoadTask;
import com.jorge.boats.view.stripe.StripeView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rx.Subscriber;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class StripePresenterTest extends PresentationModuleTestCase {

  private static final long STRIPE_NUM_CURRENT = DomainStripe.STRIPE_NUM_CURRENT;
  private StripePresenter mSut;

  @Mock private TypefaceLoadTask mMockTypefaceLoad;
  @Mock private GetStripeUseCase mMockStripeLoad;
  @Mock private PresentationEntityMapper mMockEntityMapper;
  @Mock private StripeView mMockStripeView;

  @Before @Override public void setUp() {
    super.setUp();

    mSut = new StripePresenter(mMockTypefaceLoad, mMockStripeLoad, mMockEntityMapper);
  }

  @Test public void testInitialize() {
    mSut.initialize();
    //noinspection unchecked
    verify(mMockTypefaceLoad).execute(any(Subscriber.class));
  }

  @Test public void testLoadCurrent() {
    mSut.initialize();
    mSut.setView(mMockStripeView);

    mSut.switchToStripeNum(STRIPE_NUM_CURRENT);

    verify(mMockStripeView).hideRetry();
    verify(mMockStripeView).showLoading();
  }
}
