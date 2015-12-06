package com.jorge.boats.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jorge.boats.R;

public final class CustomTitleToolbar extends Toolbar {

  @Bind(R.id.toolbar_title) TextView mTitleView;

  public CustomTitleToolbar(final @NonNull Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    initButterKnife();

    super.onFinishInflate();
  }

  public void setTitleTypeface(final @NonNull Typeface typeface) {
    mTitleView.setTypeface(typeface);
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }

  @Override public void setTitle(final CharSequence title) {
    mTitleView.setText(title);
  }
}
