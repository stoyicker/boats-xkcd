/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.jorge.boats.view.stripe;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import com.jorge.boats.domain.entity.StripeEntity;
import com.jorge.boats.view.LoadStripeView;

public interface StripeView extends LoadStripeView {

  void setTitleTypeface(@NonNull Typeface titleTypeface);

  void renderStripe(final @NonNull StripeEntity model);
}
