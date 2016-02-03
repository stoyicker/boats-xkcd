package com.jorge.boats.xkcd.domain.repository;

import com.jorge.boats.xkcd.domain.entity.DomainStripe;
import rx.Observable;

public interface XkcdStore {

  Observable<DomainStripe> currentStripe();

  Observable<DomainStripe> stripeWithNum(final long stripeNum);
}
