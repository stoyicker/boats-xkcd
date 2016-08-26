package com.jorge.boats.xkcd.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jorge.boats.xkcd.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomTitleToolbar extends Toolbar {

  @Bind(R.id.toolbar_title)
  TextView mTitleView;

  public CustomTitleToolbar(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    initButterKnife();

    super.onFinishInflate();
  }

  public TextView getTitleView() {
    return mTitleView;
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }

  @Override
  public void setTitle(final @Nullable CharSequence title) {
    ((FlickAndRevealTextView) mTitleView).playAndSetText(title);
  }
}
