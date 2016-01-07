package com.jorge.boats.view.stripe;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.entity.PresentationStripe;
import com.jorge.boats.view.LoadStripeView;

public interface StripeView extends LoadStripeView {

  void setTitleTypeface(final @NonNull Typeface titleTypeface);

  void renderStripe(final @NonNull PresentationStripe model);

  void setStripeNum(final long num);
}
