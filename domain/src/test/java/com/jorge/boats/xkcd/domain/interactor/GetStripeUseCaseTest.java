package com.jorge.boats.xkcd.domain.interactor;

import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import com.jorge.boats.xkcd.domain.repository.XkcdStore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetStripeUseCaseTest {

  private static final long STRIPE_NUM_INVALID = -1;
  private static final long STRIPE_NUM_CURRENT = DomainStripe.STRIPE_NUM_CURRENT;
  private static final long STRIPE_NUM_ARBITRARY_VALID = 123;

  private GetStripeUseCase mSut;

  @Mock private XkcdStore mMockXkcdStore;
  @Mock private ThreadExecutor mMockThreadExecutor;
  @Mock private PostExecutionThread mMockPostExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mSut = new GetStripeUseCase(mMockXkcdStore, mMockThreadExecutor, mMockPostExecutionThread);
  }

  @Test
  public void testGetStripeWithInvalidNum() {
    mSut.setRequestedStripeNum(STRIPE_NUM_INVALID);
    runVerificationsForStripeNum(STRIPE_NUM_INVALID);
  }

  @Test
  public void testGetStripeWithCurrentNum() {
    mSut.setRequestedStripeNum(STRIPE_NUM_CURRENT);
    runVerificationsForCurrentStripe();
  }

  @Test
  public void testGetStripeWithArbitraryValidNum() {
    mSut.setRequestedStripeNum(STRIPE_NUM_ARBITRARY_VALID);
    runVerificationsForStripeNum(STRIPE_NUM_ARBITRARY_VALID);
  }

  private void runVerificationsForCurrentStripe() {
    mSut.buildUseCaseObservable();

    verify(mMockXkcdStore).currentStripe();
    runCommonVerifications();
  }

  private void runVerificationsForStripeNum(final long stripeNum) {
    mSut.buildUseCaseObservable();

    verify(mMockXkcdStore).stripeWithNum(stripeNum);
    runCommonVerifications();
  }

  private void runCommonVerifications() {
    verifyNoMoreInteractions(mMockXkcdStore);
    verifyZeroInteractions(mMockThreadExecutor);
    verifyZeroInteractions(mMockPostExecutionThread);
  }
}
