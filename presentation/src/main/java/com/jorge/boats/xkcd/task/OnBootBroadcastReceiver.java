package com.jorge.boats.xkcd.task;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class OnBootBroadcastReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver") // Necessary to invoke this manually
    @Override
    public void onReceive(final Context context, final Intent intent) {
        BackgroundTaskManager.initialize(context);
    }

    public static Intent callingIntent(final Context context) {
        return new Intent(context, OnBootBroadcastReceiver.class);
    }
}
