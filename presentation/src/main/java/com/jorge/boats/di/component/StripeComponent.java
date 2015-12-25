package com.jorge.boats.di.component;

import com.jorge.boats.di.PerActivity;
import com.jorge.boats.di.module.StripeModule;
import com.jorge.boats.view.stripe.StripeActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = { ApplicationComponent.class }, modules = { StripeModule.class })
public interface StripeComponent extends ApplicationComponent {

  void inject(final StripeActivity stripeActivity);
}
