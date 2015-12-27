package com.jorge.boats.data;

import android.support.annotation.NonNull;
import com.jorge.boats.data.db.DatabaseStripe;
import com.jorge.boats.data.db.XkcdDatabaseHandler;
import com.jorge.boats.data.entity.DatabaseEntityMapper;
import com.jorge.boats.data.entity.DomainEntityMapper;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.data.net.XkcdClient;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdStore;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton public class XkcdStoreImpl implements XkcdStore {

  private final XkcdClient mClient;
  private final DatabaseEntityMapper mDatabaseEntityMapper;
  private final DomainEntityMapper mDomainEntityMapper;
  private final XkcdDatabaseHandler mXkcdDatabaseHandler;

  @Inject public XkcdStoreImpl(final @NonNull XkcdClient client,
      final @NonNull DatabaseEntityMapper databaseEntityMapper,
      final @NonNull DomainEntityMapper entityMapper,
      final @NonNull XkcdDatabaseHandler xkcdDatabaseHandler) {
    mClient = client;
    mDatabaseEntityMapper = databaseEntityMapper;
    mDomainEntityMapper = entityMapper;
    mXkcdDatabaseHandler = xkcdDatabaseHandler;
  }

  /**
   * Unfortunately there is no reliable way to know the number of the current stripe without
   * actually parsing it.
   *
   * @return {@link Observable<DomainStripe>}
   */
  @Override public Observable<DomainStripe> currentStripe() {
    return mClient.getCurrentStripe().map(new Func1<DataStripe, DomainStripe>() {
      @Override public DomainStripe call(DataStripe dataStripe) {
        return XkcdStoreImpl.this.mDomainEntityMapper.transform(dataStripe);
      }
    });
  }

  @Override public Observable<DomainStripe> stripeWithNum(final long stripeNum) {
    return Observable.create(new Observable.OnSubscribe<DatabaseStripe>() {

      private long mStripeNum;

      private Observable.OnSubscribe<DatabaseStripe> init(final long _stripeNum) {
        mStripeNum = _stripeNum;
        return this;
      }

      @Override public void call(final @NonNull Subscriber<? super DatabaseStripe> subscriber) {
        subscriber.onStart();

        subscriber.onNext(
            XkcdStoreImpl.this.mXkcdDatabaseHandler.queryForStripeWithNum(mStripeNum));

        subscriber.onCompleted();
      }
    }.init(stripeNum)).map(new Func1<DatabaseStripe, DataStripe>() {
      @Override public DataStripe call(final DatabaseStripe databaseStripe) {
        return XkcdStoreImpl.this.mDatabaseEntityMapper.transform(databaseStripe);
      }
    }).switchIfEmpty(mClient.getStripeWithId(stripeNum)).map(new Func1<DataStripe, DomainStripe>() {
      @Override public DomainStripe call(final DataStripe dataStripe) {
        XkcdStoreImpl.this.mXkcdDatabaseHandler.insertStripe(
            XkcdStoreImpl.this.mDatabaseEntityMapper.transform(dataStripe));
        return XkcdStoreImpl.this.mDomainEntityMapper.transform(dataStripe);
      }
    });
  }
}
