package com.jorge.boats.data;

import com.jorge.boats.data.db.DatabaseStripe;
import com.jorge.boats.data.db.XkcdDatabaseHandler;
import com.jorge.boats.data.helper.ValueGenerator;
import com.jorge.boats.data.mapper.DatabaseEntityMapper;
import com.jorge.boats.data.mapper.DomainEntityMapper;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.data.net.XkcdClient;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdStore;
import java.net.UnknownHostException;
import java.util.Collections;
import okhttp3.internal.http.RealResponseBody;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.Observable;
import rx.observers.TestSubscriber;

import static com.jorge.boats.data.helper.ValueGenerator.generateLong;
import static com.jorge.boats.data.helper.ValueGenerator.generateRandomDataStripe;
import static com.jorge.boats.data.helper.ValueGenerator.generateRandomDatabaseStripe;
import static com.jorge.boats.data.helper.ValueGenerator.generateRandomDomainStripe;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class XkcdStoreImplTest extends DataModuleTestCase {

  private XkcdStore mSut;

  @Mock private XkcdClient mMockClient;
  @Mock private DatabaseEntityMapper mMockDatabaseEntityMapper;
  @Mock private DomainEntityMapper mMockDomainEntityMapper;
  @Mock private XkcdDatabaseHandler mMockXkcdDatabaseHandler;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  @Before @Override public void setUp() {
    super.setUp();

    mSut = new XkcdStoreImpl(mMockClient, mMockDatabaseEntityMapper, mMockDomainEntityMapper,
        mMockXkcdDatabaseHandler);
  }

  private static Throwable generateNoInternetStubThrowable() {
    return new UnknownHostException("Stub for no connection.");
  }

  private static Throwable generateNotFoundStubThrowable() {
    //Could probably be way better
    return new HttpException(Response.error(404, new RealResponseBody(null, null)));
  }

  @Test public void testGetStripeCurrentSuccessful() {
    final DataStripe sourceStripe = generateRandomDataStripe();
    final DomainStripe targetStripe = generateRandomDomainStripe();

    given(mMockClient.getCurrentStripe()).willReturn(Observable.just(sourceStripe));
    //The mapper is tested elsewhere, so there is no need to use real functionality here
    given(mMockDomainEntityMapper.transform(sourceStripe)).willReturn(targetStripe);

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.currentStripe().subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Collections.singletonList(targetStripe));
    testSubscriber.assertCompleted();
  }

  @Test public void testGetStripeCurrentNoConnection() {
    final Throwable error = generateNoInternetStubThrowable();

    given(mMockClient.getCurrentStripe()).willReturn(Observable.<DataStripe>error(error));

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.currentStripe().subscribe(testSubscriber);

    testSubscriber.assertError(error);
    testSubscriber.assertReceivedOnNext(Collections.<DomainStripe>emptyList());
    testSubscriber.assertNotCompleted();
  }

  @Test public void testGetStripeWithValidNumNoCachedSuccessful() {
    final DataStripe sourceStripe = generateRandomDataStripe();
    final DomainStripe targetStripe = generateRandomDomainStripe();
    final long generatedNum;

    given(mMockClient.getStripeWithId(
        generatedNum = ValueGenerator.generateLong(ValueGenerator.Value.REGULAR))).willReturn(
        Observable.just(sourceStripe));
    //The mapper is tested elsewhere, so there is no need to use real functionality here
    given(mMockDomainEntityMapper.transform(sourceStripe)).willReturn(targetStripe);
    //The database is also tested somewhere else, so we can mock it here
    given(mMockXkcdDatabaseHandler.queryForStripeWithNum(generatedNum)).willReturn(null);

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.stripeWithNum(generatedNum).subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Collections.singletonList(targetStripe));
    testSubscriber.assertCompleted();
  }

  @Test public void testGetStripeWithValidNumNoCachedNoConnection() {
    final Throwable error = generateNoInternetStubThrowable();
    final long generatedNum;

    given(mMockXkcdDatabaseHandler.queryForStripeWithNum(
        generatedNum = ValueGenerator.generateLong(ValueGenerator.Value.REGULAR))).willReturn(null);
    given(mMockClient.getStripeWithId((generatedNum))).willReturn(
        Observable.<DataStripe>error(error));

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.stripeWithNum(generatedNum).subscribe(testSubscriber);

    testSubscriber.assertError(error);
    testSubscriber.assertReceivedOnNext(Collections.<DomainStripe>emptyList());
    testSubscriber.assertNotCompleted();
  }

  @Test public void testGetStripeWithValidNumCached() {
    final DatabaseStripe sourceStripe = generateRandomDatabaseStripe();
    final DataStripe intermediateStripe = generateRandomDataStripe();
    final DomainStripe targetStripe = generateRandomDomainStripe();

    given(mMockXkcdDatabaseHandler.queryForStripeWithNum(anyLong())).willReturn(sourceStripe);
    given(mMockDatabaseEntityMapper.transform(sourceStripe)).willReturn(intermediateStripe);
    given(mMockDomainEntityMapper.transform(intermediateStripe)).willReturn(targetStripe);

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.stripeWithNum(generateLong(ValueGenerator.Value.REGULAR)).subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Collections.singletonList(targetStripe));
    testSubscriber.assertCompleted();

    testSubscriber.awaitTerminalEvent();

    verify(mMockClient, never()).getStripeWithId(anyLong());
  }

  @Test public void testGetStripeWithInvalidNum() {
    final long stripeNum = generateLong(ValueGenerator.Value.NULL);

    mExceptionExpectation.expect(IllegalArgumentException.class);
    mExceptionExpectation.expectMessage("Illegal stripe number " + stripeNum);

    mSut.stripeWithNum(stripeNum);
  }

  @Test public void testGetStripeWithNotFoundNumNoCachedSuccessful() {
    final Throwable error = generateNotFoundStubThrowable();
    final long generatedNum;

    given(mMockClient.getStripeWithId(
        (generatedNum = ValueGenerator.generateLong(ValueGenerator.Value.REGULAR)))).willReturn(
        Observable.<DataStripe>error(error));
    given(mMockXkcdDatabaseHandler.queryForStripeWithNum(generatedNum)).willReturn(null);

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.stripeWithNum(generatedNum).subscribe(testSubscriber);

    testSubscriber.assertError(error);
    testSubscriber.assertReceivedOnNext(Collections.<DomainStripe>emptyList());
    testSubscriber.assertNotCompleted();
  }
}
