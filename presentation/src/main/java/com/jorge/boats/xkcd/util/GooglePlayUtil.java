package com.jorge.boats.xkcd.util;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.jorge.boats.xkcd.log.ApplicationLogger;

public abstract class GooglePlayUtil {

  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

  private GooglePlayUtil() {
    throw new IllegalAccessError("No instances");
  }

  public static boolean isServicesAvailable(final @NonNull Activity activity,
      final boolean showErrorDialogIfAvailable) {
    final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    final int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);

    if (resultCode != ConnectionResult.SUCCESS) {
      if (apiAvailability.isUserResolvableError(resultCode) && showErrorDialogIfAvailable) {
        apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
            .show();
      } else {
        ApplicationLogger.i(GooglePlayUtil.class.getName(), "Google Play Services not available.");
      }
      return false;
    }
    return true;
  }
}
