package com.jorge.boats.data.net;

import com.jorge.boats.domain.entity.StripeEntity;
import com.jorge.boats.domain.repository.XkcdRepository;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class XkcdRepositoryImpl implements XkcdRepository {

  @Override public Observable<StripeEntity> stripe(int id) {
    return null;
  }
}
