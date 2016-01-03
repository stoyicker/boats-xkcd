package com.jorge.boats.presenter;

import com.jorge.boats.PresentationModuleTestCase;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.interactor.GetStripeUseCase;
import com.jorge.boats.mapper.PresentationEntityMapper;
import com.jorge.boats.task.TypefaceLoadTask;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import rx.Subscriber;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class StripePresenterTest extends PresentationModuleTestCase {

  private static final long STRIPE_NUM_INVALID = -1;
  private static final long STRIPE_NUM_CURRENT = DomainStripe.STRIPE_NUM_CURRENT;
  private static final long STRIPE_NUM_ARBITRARY = 123;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  private StripePresenter mSut;

  @Mock private TypefaceLoadTask mTypefaceLoad;
  @Mock private GetStripeUseCase mStripeLoad;
  @Mock private PresentationEntityMapper mEntityMapper;

  @Before @Override public void setUp() {
    super.setUp();

    mSut = new StripePresenter(mTypefaceLoad, mStripeLoad, mEntityMapper);
  }

  @Test public void testInitialize() {
    mSut.initialize();
    //noinspection unchecked
    verify(mTypefaceLoad).execute(any(Subscriber.class));
  }

  //TODO Add tests (on the activity? - see sample) to see if the presenter sets the title correctly
  //TODO Add tests in the interactor package (domain) to verify that it provides the correct data
  //TODO Add presentation mapper tests (in this module [presentation]), but the corresponding pkg
}
