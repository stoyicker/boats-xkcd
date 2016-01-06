package com.jorge.boats.data.net;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import com.jorge.boats.data.R;
import com.jorge.boats.data.model.DataStripe;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;

@Singleton public class XkcdClient {

  private final XkcdService mService;

  @Inject public XkcdClient(final @NonNull Context context) {
    mService = createRetrofit(context).create(XkcdService.class);
  }

  public Observable<DataStripe> getCurrentStripe() {
    return mService.getCurrentStripe();
  }

  public Observable<DataStripe> getStripeWithId(final long stripeNum) {
    return mService.getStripeWithNum(stripeNum);
  }

  private Retrofit createRetrofit(final @NonNull Context context) {
    final Retrofit.Builder builder = new Retrofit.Builder();
    final Resources resources = context.getResources();
    final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(
        resources.getInteger(R.integer.client_timeout_connection_milliseconds),
        TimeUnit.MILLISECONDS)
        .readTimeout(resources.getInteger(R.integer.client_timeout_read_milliseconds),
            TimeUnit.MILLISECONDS)
        .writeTimeout(resources.getInteger(R.integer.client_timeout_write_milliseconds),
            TimeUnit.MILLISECONDS)
        .build();

    builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    builder.addConverterFactory(GsonConverterFactory.create());
    builder.baseUrl(resources.getString(R.string.xkcd_base_url));
    builder.client(client);

    return builder.build();
  }
}
