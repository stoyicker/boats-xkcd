/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.jorge.boats.view.stripe;

import android.support.annotation.NonNull;
import com.jorge.boats.model.StripeModel;
import com.jorge.boats.view.LoadStripeView;

interface StripeView extends LoadStripeView {

  void renderStripe(final @NonNull StripeModel user);
}
