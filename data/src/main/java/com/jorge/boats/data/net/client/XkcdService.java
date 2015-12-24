package com.jorge.boats.data.net.client;

import com.jorge.boats.data.model.DataStripe;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

interface XkcdService {

  String STRIPE_INFO_PATH = "info.0.json";

  @GET(STRIPE_INFO_PATH) Observable<DataStripe> getCurrentStripe();

  @GET("{stripeId}/" + STRIPE_INFO_PATH) Observable<DataStripe> getStripeWithId(
      final @Path("stripeId") long stripeId);
}
