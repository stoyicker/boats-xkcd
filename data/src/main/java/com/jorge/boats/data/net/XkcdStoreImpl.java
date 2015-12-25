package com.jorge.boats.data.net;

import android.support.annotation.NonNull;
import com.jorge.boats.data.entity.DomainEntityMapper;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.data.net.client.XkcdClient;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdStore;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Func1;

@Singleton public class XkcdStoreImpl implements XkcdStore {

  private final XkcdClient mClient;
  private final DomainEntityMapper mEntityMapper;

  @Inject
  public XkcdStoreImpl(final XkcdClient client, final @NonNull DomainEntityMapper entityMapper) {
    mClient = client;
    mEntityMapper = entityMapper;
  }

  /**
   * Unfortunately there is no reliable way to know the number of the current stripe without
   * actually parsing it.
   *
   * @return {@link Observable<DomainStripe>}
   */
  @Override public Observable<DomainStripe> currentStripe() {
    return mClient.getCurrentStripe().map(new Func1<DataStripe, DomainStripe>() {
      @Override public DomainStripe call(final DataStripe dataStripe) {
        return XkcdStoreImpl.this.mEntityMapper.transform(dataStripe);
      }
    });
  }

  @Override public Observable<DomainStripe> stripeWithNum(final long stripeNum) {
    return mClient.getStripeWithId(stripeNum).map(new Func1<DataStripe, DomainStripe>() {
      @Override public DomainStripe call(final DataStripe dataStripe) {
        return XkcdStoreImpl.this.mEntityMapper.transform(dataStripe);
      }
    });
  }
}
