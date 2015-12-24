package com.jorge.boats.data.net;

import com.jorge.boats.domain.entity.DomainStripe;
import com.jorge.boats.domain.repository.XkcdRepository;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class XkcdRepositoryImpl implements XkcdRepository {

  @Override public Observable<DomainStripe> currentStripe() {
    //TODO currentStripe
    return null;
  }

  @Override public Observable<DomainStripe> stripeWithId(long id) {
    //TODO stripeWithId
    return null;
  }
}
