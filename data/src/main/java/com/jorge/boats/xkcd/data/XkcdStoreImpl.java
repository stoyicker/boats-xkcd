package com.jorge.boats.xkcd.data;

import android.support.annotation.NonNull;

import com.jorge.boats.xkcd.data.db.DatabaseStripe;
import com.jorge.boats.xkcd.data.db.XkcdDatabaseHandler;
import com.jorge.boats.xkcd.data.mapper.DatabaseEntityMapper;
import com.jorge.boats.xkcd.data.mapper.DomainEntityMapper;
import com.jorge.boats.xkcd.data.model.DataStripe;
import com.jorge.boats.xkcd.data.net.XkcdClient;
import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import com.jorge.boats.xkcd.domain.repository.XkcdStore;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class XkcdStoreImpl implements XkcdStore {

  private final XkcdClient mClient;
  private final DatabaseEntityMapper mDatabaseEntityMapper;
  private final DomainEntityMapper mDomainEntityMapper;
  private final XkcdDatabaseHandler mXkcdDatabaseHandler;

  @Inject
  public XkcdStoreImpl(final @NonNull XkcdClient client,
      final @NonNull DatabaseEntityMapper databaseEntityMapper,
      final @NonNull DomainEntityMapper entityMapper,
      final @NonNull XkcdDatabaseHandler xkcdDatabaseHandler) {
    mClient = client;
    mDatabaseEntityMapper = databaseEntityMapper;
    mDomainEntityMapper = entityMapper;
    mXkcdDatabaseHandler = xkcdDatabaseHandler;
  }

  @Override
  public Observable<DomainStripe> currentStripe() {
    final long lastShownStripeNum = P.lastShownStripeNum.get();
    Observable<DataStripe> base = Observable.create(new Observable.OnSubscribe<DataStripe>() {
      @Override
      public void call(final @NonNull Subscriber<? super DataStripe> subscriber) {
        subscriber.onStart();
        final DataStripe currentRetrieved = mClient.getCurrentStripe().toBlocking().single();

        if (currentRetrieved != null) {
          subscriber.onNext(currentRetrieved);

          XkcdStoreImpl.this.mXkcdDatabaseHandler.insertStripe(
              XkcdStoreImpl.this.mDatabaseEntityMapper.transform(currentRetrieved));

          subscriber.onCompleted();
        }
      }
    });

    if (lastShownStripeNum != -1) {
      base = base.onErrorResumeNext(dataStripeWithNum(lastShownStripeNum));
    }

    return mapToDomain(base).cache();
  }

  @Override
  public Observable<DomainStripe> stripeWithNum(final long stripeNum) {
    return mapToDomain(dataStripeWithNum(stripeNum)).cache();
  }

  private Observable<DataStripe> dataStripeWithNum(final long stripeNum) {
    final Observable<DataStripe> fromDatabase =
        Observable.create(new Observable.OnSubscribe<DatabaseStripe>() {

          private long mStripeNum;

          private Observable.OnSubscribe<DatabaseStripe> init(final long _stripeNum) {
            mStripeNum = _stripeNum;
            return this;
          }

          @Override
          public void call(final @NonNull Subscriber<? super DatabaseStripe> subscriber) {
            subscriber.onStart();

            final DatabaseStripe retrievedFromDatabase =
                XkcdStoreImpl.this.mXkcdDatabaseHandler.queryForStripeWithNum(mStripeNum);

            if (retrievedFromDatabase != null) {
              subscriber.onNext(retrievedFromDatabase);
            }

            subscriber.onCompleted();
          }
        }.init(stripeNum)).map(new Func1<DatabaseStripe, DataStripe>() {
          @Override
          public DataStripe call(final DatabaseStripe databaseStripe) {
            return XkcdStoreImpl.this.mDatabaseEntityMapper.transform(databaseStripe);
          }
        });

    final Observable<DataStripe> fromInternet =
        Observable.create(new Observable.OnSubscribe<DataStripe>() {

          private long mStripeNum;

          private Observable.OnSubscribe<DataStripe> init(final long _stripeNum) {
            mStripeNum = _stripeNum;
            return this;
          }

          @Override
          public void call(final @NonNull Subscriber<? super DataStripe> subscriber) {
            subscriber.onStart();
            final DataStripe retrievedFromInternet = mClient.getStripeWithId(mStripeNum)
                .onErrorReturn(new Func1<Throwable, DataStripe>() {

                  private Subscriber<? super DataStripe> mSubscriber;

                  private Func1<Throwable, DataStripe> init(
                      final @NonNull Subscriber<? super DataStripe> subscriber) {
                    mSubscriber = subscriber;
                    return this;
                  }

                  @Override
                  public DataStripe call(final Throwable throwable) {
                    mSubscriber.onError(throwable);
                    return null;
                  }
                }.init(subscriber))
                .toBlocking()
                .single();

            if (retrievedFromInternet != null) {
              subscriber.onNext(retrievedFromInternet);

              XkcdStoreImpl.this.mXkcdDatabaseHandler.insertStripe(
                  XkcdStoreImpl.this.mDatabaseEntityMapper.transform(retrievedFromInternet));

              subscriber.onCompleted();
            }
          }
        }.init(stripeNum));

    return fromDatabase.switchIfEmpty(fromInternet);
  }

  private Observable<DomainStripe> mapToDomain(final @NonNull Observable<DataStripe> observable) {
    return observable.map(new Func1<DataStripe, DomainStripe>() {
      @Override
      public DomainStripe call(final DataStripe dataStripe) {
        return XkcdStoreImpl.this.mDomainEntityMapper.transform(dataStripe);
      }
    });
  }
}
