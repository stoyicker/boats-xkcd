package com.jorge.boats.xkcd.domain.interactor;

import com.jorge.boats.xkcd.domain.executor.PostExecutionThread;
import com.jorge.boats.xkcd.domain.executor.ThreadExecutor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.BDDMockito.given;

public class UseCaseTest {

  private static final List<Integer> STUB = Arrays.asList(1, 2, 3);

  private UseCase<List<Integer>> mSut;

  private final ThreadExecutor mThreadExecutor = new ThreadExecutor() {
    @Override public void execute(@SuppressWarnings("NullableProblems") final Runnable command) {
      command.run();
    }
  };

  @Mock private PostExecutionThread mMockPostExecutionThread;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    mSut = new IntegerListUseCase(mThreadExecutor, mMockPostExecutionThread);

    given(mMockPostExecutionThread.getScheduler()).willReturn(Schedulers.immediate());
  }

  @Test public void testBuildUseCaseObservableReturnsCorrectResult() {
    final TestSubscriber<List<Integer>> testSubscriber = new TestSubscriber<>();

    mSut.execute(testSubscriber);

    testSubscriber.assertReceivedOnNext(Collections.singletonList(STUB));
    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
  }

  @Test public void testDestroy() {
    final TestSubscriber<List<Integer>> testSubscriber = new TestSubscriber<>();

    mSut.execute(testSubscriber);

    mSut.destroy();

    testSubscriber.assertUnsubscribed();
    testSubscriber.assertNoErrors();
  }

  private static class IntegerListUseCase extends UseCase<List<Integer>> {

    private IntegerListUseCase(final ThreadExecutor threadExecutor,
        final PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override protected Observable<List<Integer>> buildUseCaseObservable() {
      return Observable.just(STUB);
    }
  }
}