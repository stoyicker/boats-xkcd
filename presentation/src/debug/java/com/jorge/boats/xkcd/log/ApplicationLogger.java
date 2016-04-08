package com.jorge.boats.xkcd.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

public abstract class ApplicationLogger {

    private ApplicationLogger() {
        throw new IllegalAccessError("No instances.");
    }

    public static void d(final @NonNull String message, final @Nullable Object... args) {
        Timber.d(message, args);
    }

    public static void w(final @NonNull String message, final @Nullable Object... args) {
        Timber.w(message, args);
    }

    public static void e(final @NonNull Throwable throwable, final @NonNull String message,
                         final @Nullable Object... args) {
        Timber.e(throwable, message, args);
    }

    public static void i(final @NonNull String message, final @Nullable Object... args) {
        Timber.i(message, args);
    }
}
