package com.jorge.boats.view.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jorge.boats.R;
import com.jorge.boats.view.widget.CustomTitleToolbar;

public abstract class BaseVisualActivity extends BaseActivity {

  @Bind(android.R.id.content) View mRoot;

  @Bind(R.id.toolbar) CustomTitleToolbar mToolbar;

  @Override public void setContentView(final int layoutResID) {
    super.setContentView(layoutResID);

    initButterKnife();

    setupToolbar(mToolbar);
    setupBackground(mRoot);
  }

  private void initButterKnife() {
    ButterKnife.bind(this);
  }

  private void setupToolbar(final @NonNull Toolbar toolbar) {
    setSupportActionBar(toolbar);
  }

  private void setupBackground(final @NonNull View root) {
    final Resources res;

    root.setBackground(new BackgroundBitmapDrawable(res = getResources(),
        BitmapFactory.decodeResource(res, R.drawable.app_background)));
  }

  @NonNull public TextView getTitleView() {
    return mToolbar.getTitleView();
  }

  private static class BackgroundBitmapDrawable extends BitmapDrawable {
    private final Matrix mMatrix = new Matrix();
    private int mOldHeight;
    private boolean isSimpleMapping = false;

    private BackgroundBitmapDrawable(Resources res, Bitmap bitmap) {
      super(res, bitmap);
    }

    @Override protected void onBoundsChange(Rect bounds) {
      if (bounds.height() > mOldHeight) {
        mOldHeight = bounds.height();
        Bitmap b = getBitmap();
        RectF src = new RectF(0, 0, b.getWidth(), b.getHeight());
        RectF dst;

        if (isSimpleMapping) {
          dst = new RectF(bounds);
          mMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        } else {
          // Full Screen Image -> Always scale and center-crop in order to fill the screen
          float dwidth = src.width();
          float dheight = src.height();

          float vwidth = bounds.width();
          float vheight = bounds.height();

          float scale;
          float dx = 0, dy = 0;

          if (dwidth * vheight > vwidth * dheight) {
            scale = vheight / dheight;
            dx = (vwidth - dwidth * scale) * 0.5f;
          } else {
            scale = vwidth / dwidth;
            dy = (vheight - dheight * scale) * 0.5f;
          }

          mMatrix.setScale(scale, scale);
          mMatrix.postTranslate(dx, dy);
        }
      }
    }

    @Override public void draw(Canvas canvas) {
      canvas.drawColor(0xaa00ff00);
      canvas.drawBitmap(getBitmap(), mMatrix, null);
    }
  }
}
