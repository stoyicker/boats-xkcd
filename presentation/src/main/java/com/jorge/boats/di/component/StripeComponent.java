package com.jorge.boats.di.component;

import android.support.annotation.NonNull;
import com.jorge.boats.di.PerActivity;
import com.jorge.boats.di.module.StripeModule;
import com.jorge.boats.view.stripe.StripeActivity;
import dagger.Component;

@PerActivity @Component(modules = { StripeModule.class }) public interface StripeComponent
    extends AbstractComponent {

  void inject(final @NonNull StripeActivity stripeActivity);
}
