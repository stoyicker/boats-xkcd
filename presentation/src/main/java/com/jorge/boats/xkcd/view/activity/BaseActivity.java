package com.jorge.boats.xkcd.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.jorge.boats.xkcd.CustomApplication;
import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.di.component.ApplicationComponent;
import com.jorge.boats.xkcd.util.ThemeUtil;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

  private Dialog mRateAppDialog;

  @Override protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  protected abstract void createComponentAndInjectSelf();

  protected final ApplicationComponent getApplicationComponent() {
    return ((CustomApplication) getApplication()).getApplicationComponent();
  }

  protected final void showRateAppDialog() {
    if (mRateAppDialog == null) {
      //noinspection deprecation -- Yes, deprecated, but the replacement is added in API 22
      mRateAppDialog = new AlertDialog.Builder(this,
          ThemeUtil.isSelectedThemeDark(this) ? android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK
              : android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).
          setTitle(getString(R.string.rate_popup_title, getString(R.string.app_name)))
          .setMessage(R.string.rate_popup_body)
          .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override public void onClick(final @NonNull DialogInterface dialog, final int which) {
              final String packageName;
              final Uri uri = Uri.parse(String.format(Locale.ENGLISH, "market://details?id=%s",
                  packageName = BaseActivity.this.getPackageName()));
              final Intent intent = new Intent(Intent.ACTION_VIEW, uri);

              intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //noinspection InlinedApi -- To not to pop lint
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
              } else {
                //noinspection deprecation -- The if statement covers any possible issues
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
              }

              try {
                startActivity(intent);
              } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                    String.format(Locale.ENGLISH, "http://play.google.com/store/apps/details?id=%s",
                        packageName))));
              }
            }
          })
          .setNegativeButton(android.R.string.cancel, null)
          .create();
    }
    mRateAppDialog.show();
  }
}
