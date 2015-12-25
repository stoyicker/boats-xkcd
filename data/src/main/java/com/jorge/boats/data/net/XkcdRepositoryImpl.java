package com.jorge.boats.data.net;

import android.support.annotation.NonNull;
import com.jorge.boats.data.entity.DomainEntityMapper;
import com.jorge.boats.data.model.DataStripe;
import com.jorge.boats.data.net.client.XkcdClient;
import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdRepository;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Func1;

@Singleton public class XkcdRepositoryImpl implements XkcdRepository {

  private final XkcdClient mClient;
  private final DomainEntityMapper mEntityMapper;

  @Inject public XkcdRepositoryImpl(final XkcdClient client, final @NonNull
  DomainEntityMapper entityMapper) {
    mClient = client;
    mEntityMapper = entityMapper;
  }

  @Override public Observable<DomainStripe> currentStripe() {
    return mClient.getCurrentStripe().map(new Func1<DataStripe, DomainStripe>() {
      @Override public DomainStripe call(final DataStripe dataStripe) {
        return XkcdRepositoryImpl.this.mEntityMapper.transform(dataStripe);
      }
    });
  }

  @Override public Observable<DomainStripe> stripeWithId(final long id) {
    return mClient.getStripeWithId(id).map(new Func1<DataStripe, DomainStripe>() {
      @Override public DomainStripe call(final DataStripe dataStripe) {
        return XkcdRepositoryImpl.this.mEntityMapper.transform(dataStripe);
      }
    });
  }
}
