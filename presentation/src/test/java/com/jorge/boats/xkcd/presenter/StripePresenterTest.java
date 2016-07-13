package com.jorge.boats.xkcd.presenter;

import com.jorge.boats.xkcd.PresentationModuleTestCase;
import com.jorge.boats.xkcd.UIThread;
import com.jorge.boats.xkcd.data.executor.JobExecutor;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.domain.interactor.GetStripeUseCase;
import com.jorge.boats.xkcd.domain.repository.XkcdStore;
import com.jorge.boats.xkcd.helper.ValueGenerator;
import com.jorge.boats.xkcd.mapper.PresentationEntityMapper;
import com.jorge.boats.xkcd.view.task.TypefaceLoadTask;
import com.jorge.boats.xkcd.view.stripe.StripeContentView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;
import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class StripePresenterTest extends PresentationModuleTestCase {

  private static final long STRIPE_NUM_CURRENT = DomainStripe.STRIPE_NUM_CURRENT;
  private StripePresenter mSut;

  @Mock private TypefaceLoadTask mMockTypefaceLoad;
  @Mock private XkcdStore mMockXkcdStore;
  @Mock private PresentationEntityMapper mMockEntityMapper;
  @Mock private StripeContentView mMockStripeView;

  @Before @Override public void setUp() {
    super.setUp();

    mSut = new StripePresenter(mMockTypefaceLoad,
        new GetStripeUseCase(mMockXkcdStore, new JobExecutor(), new UIThread()));
  }

  @Test public void testInitialize() {
    mSut.initialize();
    //noinspection unchecked
    verify(mMockTypefaceLoad).execute(any(Subscriber.class));
  }

  @Test public void testLoadCurrent() {
    given(mMockXkcdStore.currentStripe()).willReturn(
        Observable.just(ValueGenerator.generateRandomDomainStripe()));
    mSut.initialize();
    mSut.setStripeContentView(mMockStripeView);

    mSut.switchToStripeNum(STRIPE_NUM_CURRENT);

    verify(mMockStripeView).hideRetry();
    verify(mMockStripeView).showLoading();
  }
}
