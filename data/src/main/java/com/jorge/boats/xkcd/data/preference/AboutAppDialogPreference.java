package com.jorge.boats.xkcd.data.preference;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.jorge.boats.xkcd.data.R;

public class AboutAppDialogPreference extends CustomDialogPreference {

  public AboutAppDialogPreference(final @NonNull Context context) {
    super(context);
  }

  public AboutAppDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AboutAppDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public AboutAppDialogPreference(final @NonNull Context context,
      final @Nullable AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public Dialog buildDialog(final @NonNull AlertDialog.Builder builder, final boolean isDark) {
    final Context context = getContext();
    PackageInfo packageInfo = null;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    } catch (final PackageManager.NameNotFoundException ignored) {
    }

    builder.setTitle(R.string.pref_title_about_app);

    @SuppressLint("InflateParams") final View view =
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
            R.layout.preference_about_app, null, false);
    final TextView body = (TextView) view.findViewById(R.id.body), version =
        (TextView) view.findViewById(R.id.version);

    if (packageInfo != null) {
      if (isDark) {
        body.setTextColor(Color.WHITE);
        version.setTextColor(Color.WHITE);
      } else {
        body.setTextColor(Color.BLACK);
        version.setTextColor(Color.BLACK);
      }
      body.setMovementMethod(LinkMovementMethod.getInstance());
      body.setLinkTextColor(super.getLinkColor());
      body.setText(Html.fromHtml(context.getString(R.string.pref_message_about_app)));

      version.setText(context.getString(R.string.version_tag, packageInfo.versionName,
          packageInfo.versionCode));
    }

    final ViewTreeObserver observer = body.getViewTreeObserver();

    observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

      private boolean isExecuted = false;

      @Override
      public void onGlobalLayout() {
        if (!isExecuted) {
          isExecuted = true;
          execute();
          if (observer.isAlive()) {
            observer.removeOnGlobalLayoutListener(this);
          } else {
            body.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        }
      }

      private void execute() {
        final View icon;
        final ViewGroup.LayoutParams layoutParams =
            (icon = view.findViewById(R.id.icon)).getLayoutParams();

        layoutParams.height = body.getHeight();

        icon.setLayoutParams(layoutParams);
      }
    });

    builder.setView(view)
        .setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(R.string.licenses, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(final @NonNull DialogInterface dialog, final int which) {
            final String url = context.getString(R.string.licenses_url);
            final Intent i = new Intent(Intent.ACTION_VIEW);

            i.setData(Uri.parse(url));
            context.startActivity(i);
          }
        });

    return builder.create();
  }
}
