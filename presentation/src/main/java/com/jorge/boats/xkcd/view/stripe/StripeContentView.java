package com.jorge.boats.xkcd.view.stripe;

import android.support.annotation.NonNull;

import com.jorge.boats.xkcd.entity.PresentationStripe;
import com.jorge.boats.xkcd.view.ContentView;

public interface StripeContentView extends ContentView {

  long getStripeNum();

  void renderStripe(final @NonNull PresentationStripe model);

  void setStripeNum(final long num);

  void share();
}
