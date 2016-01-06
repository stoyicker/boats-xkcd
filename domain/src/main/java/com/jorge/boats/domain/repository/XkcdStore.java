package com.jorge.boats.domain.repository;

import com.jorge.boats.domain.entity.DomainStripe;
import rx.Observable;

public interface XkcdStore {

  Observable<DomainStripe> currentStripe();

  Observable<DomainStripe> stripeWithNum(final long stripeNum);
}
