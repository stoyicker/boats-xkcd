package com.jorge.boats.xkcd.data.preference;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

import com.jorge.boats.xkcd.data.P;
import com.jorge.boats.xkcd.data.R;

import java.util.Locale;

public abstract class CustomDialogPreference extends DialogPreference {

    public CustomDialogPreference(final @NonNull Context context) {
        super(context);
    }

    public CustomDialogPreference(final @NonNull Context context,
                                  final @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDialogPreference(final @NonNull Context context, final @Nullable AttributeSet attrs,
                                  final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomDialogPreference(final @NonNull Context context, final @Nullable AttributeSet attrs,
                                  final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public abstract Dialog buildDialog(final @NonNull AlertDialog.Builder builder,
                                       final boolean isDark);

    @ColorInt
    protected int getLinkColor() {
        final Context context = getContext();
        final @ColorInt int ret;

        final TypedArray a = context.getTheme().obtainStyledAttributes(context.getResources().getIdentifier(String.format(Locale.ENGLISH, "Settings%s", P.themeName.get()), "style", context.getPackageName()), new int[]{R.attr.colorAccent});

        ret = a.getColor(0, Color.GREEN);

        a.recycle();

        return ret;
    }
}
