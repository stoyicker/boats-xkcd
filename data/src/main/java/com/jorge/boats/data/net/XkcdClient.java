package com.jorge.boats.data.net;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import com.jorge.boats.data.R;
import com.jorge.boats.data.model.DataStripe;
import com.squareup.okhttp.OkHttpClient;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import retrofit.Retrofit;
import rx.Observable;

@Singleton public class XkcdClient {

  private final XkcdService mService;

  public XkcdClient(final @NonNull Context context) {
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
    final OkHttpClient client = new OkHttpClient();
    final Resources resources = context.getResources();

    client.setConnectTimeout(resources.getInteger(R.integer.client_timeout_connection_milliseconds),
        TimeUnit.MILLISECONDS);
    client.setReadTimeout(resources.getInteger(R.integer.client_timeout_read_milliseconds),
        TimeUnit.MILLISECONDS);
    client.setWriteTimeout(resources.getInteger(R.integer.client_timeout_write_milliseconds),
        TimeUnit.MILLISECONDS);

    builder.baseUrl(resources.getString(R.string.xkcd_base_url));
    builder.client(client);

    return builder.build();
  }
}
