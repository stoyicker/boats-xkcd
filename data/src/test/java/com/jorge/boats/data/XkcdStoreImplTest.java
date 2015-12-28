package com.jorge.boats.data;

import com.jorge.boats.data.db.XkcdDatabaseHandler;
import com.jorge.boats.data.mapper.DatabaseEntityMapper;
import com.jorge.boats.data.mapper.DomainEntityMapper;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.data.net.XkcdClient;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdStore;
import java.net.UnknownHostException;
import java.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static com.jorge.boats.data.ValueGenerator.generateLong;
import static com.jorge.boats.data.ValueGenerator.generateString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

//TODO Reenable the test for not found stripe when the retrofit2 package is available through the dependency
public class XkcdStoreImplTest extends DataModuleTestSuite {

  private XkcdStore mSut;

  @Mock private XkcdClient mMockClient;
  @Mock private DatabaseEntityMapper mMockDatabaseEntityMapper;
  @Mock private DomainEntityMapper mMockDomainEntityMapper;
  @Mock private XkcdDatabaseHandler mMockXkcdDatabaseHandler;

  @Rule public final ExpectedException mExceptionExpectation = ExpectedException.none();

  @Before @Override public void setUp() {
    super.setUp();
    MockitoAnnotations.initMocks(this);

    mSut = new XkcdStoreImpl(mMockClient, mMockDatabaseEntityMapper, mMockDomainEntityMapper,
        mMockXkcdDatabaseHandler);
  }

  private static DataStripe generateRandomDataStripe() {
    final DataStripe ret = new DataStripe();

    ret.setAlt(generateString(ValueGenerator.Value.NULL));
    ret.setDay(generateString(ValueGenerator.Value.NULL));
    ret.setImg(generateString(ValueGenerator.Value.NULL));
    ret.setLink(generateString(ValueGenerator.Value.NULL));
    ret.setMonth(generateString(ValueGenerator.Value.NULL));
    ret.setYear(generateString(ValueGenerator.Value.NULL));
    ret.setNews(generateString(ValueGenerator.Value.NULL));
    ret.setNum(generateLong(ValueGenerator.Value.NULL));
    ret.setTitle(generateString(ValueGenerator.Value.NULL));
    ret.setSafe_title(generateString(ValueGenerator.Value.NULL));
    ret.setTranscript(generateString(ValueGenerator.Value.NULL));

    return ret;
  }

  private static DomainStripe generateRandomDomainStripe() {
    final DomainStripe ret = new DomainStripe();

    ret.setAlt(generateString(ValueGenerator.Value.NULL));
    ret.setDay(generateString(ValueGenerator.Value.NULL));
    ret.setImg(generateString(ValueGenerator.Value.NULL));
    ret.setLink(generateString(ValueGenerator.Value.NULL));
    ret.setMonth(generateString(ValueGenerator.Value.NULL));
    ret.setYear(generateString(ValueGenerator.Value.NULL));
    ret.setNews(generateString(ValueGenerator.Value.NULL));
    ret.setNum(generateLong(ValueGenerator.Value.NULL));
    ret.setTitle(generateString(ValueGenerator.Value.NULL));
    ret.setSafe_title(generateString(ValueGenerator.Value.NULL));
    ret.setTranscript(generateString(ValueGenerator.Value.NULL));

    return ret;
  }

  private static Throwable generateNoInternetStubThrowable() {
    return new UnknownHostException("Stub for no connection.");
  }

  //private static Throwable generateNotFoundStubThrowable() {
  //  final retrofit2.HttpException ret = Mockito.mock(retrofit2.HttpException.class);
  //
  //  given(ret.code()).willReturn(404);
  //  given(ret.message()).willReturn("Not found.");
  //
  //  return ret;
  //}

  @Test public void testGetStripeCurrentSuccessful() {
    final DataStripe sourceStripe = generateRandomDataStripe();
    final DomainStripe targetStripe = generateRandomDomainStripe();

    given(mMockClient.getCurrentStripe()).willReturn(Observable.just(sourceStripe));
    //The mapper is tested elsewhere, so there is no need to use real functionality here
    given(mMockDomainEntityMapper.transform(any(DataStripe.class))).willReturn(targetStripe);

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

    testSubscriber.assertError(UnknownHostException.class);
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
    given(mMockDomainEntityMapper.transform(any(DataStripe.class))).willReturn(targetStripe);
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

    given(mMockClient.getStripeWithId(
        (generatedNum = ValueGenerator.generateLong(ValueGenerator.Value.REGULAR)))).willReturn(
        Observable.<DataStripe>error(error));
    given(mMockXkcdDatabaseHandler.queryForStripeWithNum(generatedNum)).willReturn(null);

    final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();

    mSut.stripeWithNum(generatedNum).subscribe(testSubscriber);

    testSubscriber.assertError(UnknownHostException.class);
    testSubscriber.assertReceivedOnNext(Collections.<DomainStripe>emptyList());
    testSubscriber.assertNotCompleted();
  }

  @Test public void testGetStripeWithInvalidNum() {
    mExceptionExpectation.expect(IllegalArgumentException.class);

    mSut.stripeWithNum(generateLong(ValueGenerator.Value.NULL));
  }

  //@Test public void testGetStripeWithNotFoundNumNoCachedSuccessful() {
  //  final Throwable error = generateNotFoundStubThrowable();
  //  final long generatedNum;
  //
  //  given(mMockClient.getStripeWithId(
  //      (generatedNum = ValueGenerator.generateLong(ValueGenerator.Value.REGULAR)))).willReturn(
  //      Observable.<DataStripe>error(error));
  //  given(mMockXkcdDatabaseHandler.queryForStripeWithNum(generatedNum)).willReturn(null);
  //
  //  final TestSubscriber<DomainStripe> testSubscriber = new TestSubscriber<>();
  //
  //  mSut.stripeWithNum(generatedNum).subscribe(testSubscriber);
  //
  //  testSubscriber.assertError(retrofit2.HttpException.class);
  //  testSubscriber.assertReceivedOnNext(Collections.<DomainStripe>emptyList());
  //  testSubscriber.assertNotCompleted();
  //}
  
  //TODO Write a test where the result is cached in the database and then we assert that the client is not used
}
