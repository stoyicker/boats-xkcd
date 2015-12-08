package com.jorge.boats.di.component;

import com.jorge.boats.di.module.StripeModule;
import dagger.Component;

@Component(modules = { StripeModule.class }) public interface StripeComponent
    extends AbstractComponent {
}
