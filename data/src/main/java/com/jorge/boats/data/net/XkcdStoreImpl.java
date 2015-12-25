package com.jorge.boats.data.net;

import android.support.annotation.NonNull;
import com.jorge.boats.data.db.DatabaseHandler;
import com.jorge.boats.data.db.DatabaseStripe;
import com.jorge.boats.data.entity.DatabaseEntityMapper;
import com.jorge.boats.data.entity.DomainEntityMapper;
import com.jorge.boats.data.net.client.XkcdClient;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdStore;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Subscriber;

@Singleton public class XkcdStoreImpl implements XkcdStore {

  private final XkcdClient mClient;
  private final DatabaseEntityMapper mDatabaseEntityMapper;
  private final DomainEntityMapper mDomainEntityMapper;
  private final DatabaseHandler mDatabaseHandler;

  @Inject public XkcdStoreImpl(final @NonNull XkcdClient client,
      final @NonNull DatabaseEntityMapper databaseEntityMapper,
      final @NonNull DomainEntityMapper entityMapper,
      final @NonNull DatabaseHandler databaseHandler) {
    mClient = client;
    mDatabaseEntityMapper = databaseEntityMapper;
    mDomainEntityMapper = entityMapper;
    mDatabaseHandler = databaseHandler;
  }

  /**
   * Unfortunately there is no reliable way to know the number of the current stripe without
   * actually parsing it.
   *
   * @return {@link Observable<DomainStripe>}
   */
  @Override public Observable<DomainStripe> currentStripe() {
    return mClient.getCurrentStripe().map(XkcdStoreImpl.this.mDomainEntityMapper::transform);
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

        subscriber.onNext(XkcdStoreImpl.this.mDatabaseHandler.queryForStripeWithNum(mStripeNum));

        subscriber.onCompleted();
      }
    }.init(stripeNum))
        .map(XkcdStoreImpl.this.mDatabaseEntityMapper::transform)
        .switchIfEmpty(mClient.getStripeWithId(stripeNum))
        .map(dataStripe -> {
          XkcdStoreImpl.this.mDatabaseHandler.insertStripe(
              XkcdStoreImpl.this.mDatabaseEntityMapper.transform(dataStripe));
          return XkcdStoreImpl.this.mDomainEntityMapper.transform(dataStripe);
        });
  }
}