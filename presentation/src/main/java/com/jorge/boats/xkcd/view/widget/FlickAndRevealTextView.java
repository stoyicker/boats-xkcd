package com.jorge.boats.xkcd.view.widget;

import com.jorge.boats.xkcd.R;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Adapted from <a href="https://github.com/ANPez/RevealTextView">RevealTextView</a>
 */
public class FlickAndRevealTextView extends TextView
        implements Runnable, ValueAnimator.AnimatorUpdateListener {

    private int mInDuration = 0, mOutDuration = 0;
    private int mRed, mGreen, mBlue;
    private double[] mAlphas;

    public FlickAndRevealTextView(final @NonNull Context context) {
        super(context);
    }

    public FlickAndRevealTextView(final @NonNull Context context,
                                  final @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlickAndRevealTextView, 0, 0));
    }

    public FlickAndRevealTextView(final @NonNull Context context, final @Nullable AttributeSet attrs,
                                  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlickAndRevealTextView, 0, 0));
    }

    private void init(final @Nullable TypedArray attrs) {
        if (attrs != null) {
            try {
                mInDuration =
                        attrs.getInteger(R.styleable.FlickAndRevealTextView_in_duration_millis, mInDuration);
                mOutDuration =
                        attrs.getInteger(R.styleable.FlickAndRevealTextView_out_duration_millis, mOutDuration);
            } finally {
                attrs.recycle();
            }
        }
    }

    private void refreshAlphas() {
        final CharSequence text = super.getText();
        final int length;

        mAlphas = new double[length = text.length()];
        for (int i = 0; i < length; i++) {
            mAlphas[i] = Math.random() - 1.0f;
        }
    }

    public void playAndSetText(final @Nullable CharSequence text) {
        final Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setDuration(mOutDuration);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final @NonNull Animation animation) {

            }

            @Override
            public void onAnimationEnd(final @NonNull Animation animation) {
                FlickAndRevealTextView.super.setText(text);

                if (!TextUtils.isEmpty(text)) replay();
            }

            @Override
            public void onAnimationRepeat(final @NonNull Animation animation) {
            }
        });

        startAnimation(fadeOut);
    }

    private void replay() {
        refreshAlphas();

        if (!TextUtils.isEmpty(super.getText())) {
            post(this);
        }
    }

    @Override
    public void run() {
        final int color = getCurrentTextColor();

        mRed = Color.red(color);
        mGreen = Color.green(color);
        mBlue = Color.blue(color);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 2f);
        animator.setDuration(mInDuration);
        animator.addUpdateListener(this);
        animator.start();
    }

    private int clamp(final double value) {
        return (int) (255f * Math.min(Math.max(value, 0f), 1f));
    }

    @Override
    public void onAnimationUpdate(final @NonNull ValueAnimator valueAnimator) {
        final float value = (float) valueAnimator.getAnimatedValue();
        final CharSequence text = super.getText();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        setVisibility(VISIBLE);
        for (int i = 0; i < text.length(); i++) {
            builder.setSpan(
                    new ForegroundColorSpan(Color.argb(clamp(value + mAlphas[i]), mRed, mGreen, mBlue)), i,
                    i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(builder);
    }
}
