package com.jorge.boats.xkcd.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jorge.boats.xkcd.R;
import com.jorge.boats.xkcd.presenter.StripePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RetryLinearLayout extends LinearLayout {

    @Bind(android.R.id.text1)
    TextView mText;

    private StripePresenter mStripePresenter;
    private GestureDetector mIntermediateGestureDetector;

    public RetryLinearLayout(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(final @NonNull Context context) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.widget_retry, this);
        }

        setOrientation(VERTICAL);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final @NonNull View v) {
                RetryLinearLayout.this.onClick();
            }
        });

        ButterKnife.bind(this);
    }

    public void setStripePresenter(final @NonNull StripePresenter stripePresenter) {
        this.mStripePresenter = stripePresenter;
    }

    private void onClick() {
        mStripePresenter.requestRetry();
    }

    @Override
    public boolean onTouchEvent(final @NonNull MotionEvent event) {
        if (mIntermediateGestureDetector != null) {
            return mIntermediateGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }

    public void setTextTypeface(final @NonNull Typeface typeface) {
        mText.setTypeface(typeface);
    }

    public void setIntermediateGestureDetector(final @NonNull GestureDetector gestureDetector) {
        this.mIntermediateGestureDetector = gestureDetector;
    }
}
