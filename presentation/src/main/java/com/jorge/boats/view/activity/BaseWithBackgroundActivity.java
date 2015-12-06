package com.jorge.boats.view.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import com.jorge.boats.R;

class BaseWithBackgroundActivity extends BaseActivity {

  @Override public void setContentView(final int layoutResID) {
    final Resources res;
    super.setContentView(layoutResID);

    findViewById(android.R.id.content).setBackground(
        new BackgroundBitmapDrawable(res = getResources(),
            BitmapFactory.decodeResource(res, R.drawable.app_background)));
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
