package com.jorge.boats.xkcd.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class ApplicationLogger {

    private ApplicationLogger() {
        throw new IllegalAccessError("No instances.");
    }

    public static void d(final String message, final Object... args) {
        //No-op
    }

    public static void w(final String message, final Object... args) {
        //No-op
    }

    public static void e(final @NonNull Throwable throwable, final String message, final Object... args) {
        //No-op
    }

    public static void i(final @NonNull String message, final @Nullable Object... args) {
        //No-op
    }
}
