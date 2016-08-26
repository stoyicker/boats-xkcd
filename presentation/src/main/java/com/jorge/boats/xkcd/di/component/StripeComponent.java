package com.jorge.boats.xkcd.di.component;

import com.jorge.boats.xkcd.di.PerActivity;
import com.jorge.boats.xkcd.di.module.StripeModule;
import com.jorge.boats.xkcd.view.stripe.StripeActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = { ApplicationComponent.class }, modules = { StripeModule.class })
public interface StripeComponent extends ApplicationComponent {

  void inject(final StripeActivity stripeActivity);
}
