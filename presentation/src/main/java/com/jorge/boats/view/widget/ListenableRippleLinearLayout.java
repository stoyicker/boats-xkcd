package com.jorge.boats.view.widget;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Robin Chutaux
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import com.jorge.boats.R;
import com.jorge.boats.util.ResourceUtil;

/**
 * Adapted from <a href="https://raw.githubusercontent.com/rafaeltoledo/RippleEffect/27a50e283e2c25aa629e4d40e79b3acd3386ff28/library/src/main/java/com/andexert/library/RippleView.java">https://raw.githubusercontent.com/rafaeltoledo/RippleEffect/27a50e283e2c25aa629e4d40e79b3acd3386ff28/library/src/main/java/com/andexert/library/RippleView.java</a>
 * <p/>
 * Author :    Chutaux Robin
 * Date :      10/8/2014
 */
public class ListenableRippleLinearLayout extends LinearLayout {

  private Integer WIDTH;
  private Integer HEIGHT;
  private Integer FRAME_RATE = 10;
  private Integer DURATION = 100;
  private Integer PAINT_ALPHA = 90;
  private Handler canvasHandler;
  private Float radiusMax = 0F;
  private boolean animationRunning = Boolean.FALSE;
  private Integer timer = 0;
  private Integer timerEmpty = 0;
  private Integer durationEmpty = -1;
  private Float x = -1F;
  private Float y = -1F;
  private Integer zoomDuration;
  private Float zoomScale;
  private ScaleAnimation scaleAnimation;
  private Boolean hasToZoom;
  private Boolean isCentered;
  private Integer rippleType;
  private Paint mPaint;
  private Bitmap originBitmap;
  private Integer rippleColor;
  private Integer ripplePadding;
  private GestureDetector gestureDetector;
  private final Runnable runnable = new Runnable() {
    @Override public void run() {
      invalidate();
    }
  };

  private IRippleCompleteListener mOnCompletionListener;
  private int mCanvasSaveCount;

  /**
   * Standard constructor.
   *
   * @param context {@link Context} Context
   * @param attrs {@link AttributeSet} Attributes specified through XML
   */
  public ListenableRippleLinearLayout(final @NonNull Context context,
      final @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  /**
   * Initializes the instance fields with the values read on the XML.
   *
   * @param context {@link Context} Context
   * @param attrs {@link AttributeSet} Attributes specified through XML
   */
  private void init(final @NonNull Context context, final @Nullable AttributeSet attrs) {
    if (isInEditMode()) return;

    if (attrs != null) {
      final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
      rippleColor = typedArray.getColor(R.styleable.RippleView_rv_color,
          ResourceUtil.getColor(getResources(), R.color.primary, context.getTheme()));
      rippleType = typedArray.getInt(R.styleable.RippleView_rv_type, 0);
      hasToZoom = typedArray.getBoolean(R.styleable.RippleView_rv_zoom, Boolean.FALSE);
      isCentered = typedArray.getBoolean(R.styleable.RippleView_rv_centered, Boolean.FALSE);
      DURATION = typedArray.getInteger(R.styleable.RippleView_rv_rippleDuration, DURATION);
      FRAME_RATE = typedArray.getInteger(R.styleable.RippleView_rv_framerate, FRAME_RATE);
      PAINT_ALPHA = typedArray.getInteger(R.styleable.RippleView_rv_alpha, PAINT_ALPHA);
      ripplePadding = typedArray.getDimensionPixelSize(R.styleable.RippleView_rv_ripplePadding, 0);
      canvasHandler = new Handler();
      zoomScale = typedArray.getFloat(R.styleable.RippleView_rv_zoomScale, 1.03f);
      zoomDuration = typedArray.getInt(R.styleable.RippleView_rv_zoomDuration, 200);
      typedArray.recycle();
    }
    mPaint = new Paint();
    mPaint.setAntiAlias(Boolean.TRUE);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setColor(rippleColor);
    mPaint.setAlpha(PAINT_ALPHA);
    this.setWillNotDraw(Boolean.FALSE);

    gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

      /**
       * {@inheritDoc}
       */
      @Override public void onLongPress(MotionEvent event) {
        super.onLongPress(event);
        animateRipple(event);
      }

      /**
       * {@inheritDoc}
       */
      @Override public boolean onSingleTapConfirmed(MotionEvent e) {
        return Boolean.TRUE;
      }

      /**
       * {@inheritDoc}
       */
      @Override public boolean onSingleTapUp(MotionEvent e) {
        return Boolean.TRUE;
      }
    });

    this.setDrawingCacheEnabled(Boolean.TRUE);
    this.setClickable(Boolean.TRUE);
  }

  /**
   * {@inheritDoc}
   */
  @Override public void draw(@NonNull Canvas canvas) {
    super.draw(canvas);
    if (animationRunning) {
      if (DURATION <= timer * FRAME_RATE) {
        animationRunning = Boolean.FALSE;
        timer = 0;
        durationEmpty = -1;
        timerEmpty = 0;
        if (mCanvasSaveCount != -1) {
          canvas.restoreToCount(mCanvasSaveCount);
          mCanvasSaveCount = -1;
        }
        invalidate();
        if (mOnCompletionListener != null) mOnCompletionListener.onRippleComplete(this);
        return;
      } else {
        canvasHandler.postDelayed(runnable, FRAME_RATE);
      }

      if (timer == 0) mCanvasSaveCount = canvas.save();

      canvas.drawCircle(x, y, (radiusMax * (((float) timer * FRAME_RATE) / DURATION)), mPaint);

      mPaint.setColor(Color.parseColor("#ffff4444"));

      if (rippleType == 1
          && originBitmap != null
          && (((float) timer * FRAME_RATE) / DURATION) > 0.4f) {
        if (durationEmpty == -1) durationEmpty = DURATION - timer * FRAME_RATE;

        timerEmpty++;
        final Bitmap tmpBitmap = getCircleBitmap(
            (int) ((radiusMax) * (((float) timerEmpty * FRAME_RATE) / (durationEmpty))));
        canvas.drawBitmap(tmpBitmap, 0, 0, mPaint);
        tmpBitmap.recycle();
      }

      mPaint.setColor(rippleColor);

      if (rippleType == 1) {
        if ((((float) timer * FRAME_RATE) / DURATION) > 0.6f) {
          mPaint.setAlpha((int) (PAINT_ALPHA - ((PAINT_ALPHA) * (((float) timerEmpty * FRAME_RATE)
              / (durationEmpty)))));
        } else {
          mPaint.setAlpha(PAINT_ALPHA);
        }
      } else {
        mPaint.setAlpha(
            (int) (PAINT_ALPHA - ((PAINT_ALPHA) * (((float) timer * FRAME_RATE) / DURATION))));
      }

      timer++;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    WIDTH = w;
    HEIGHT = h;

    scaleAnimation = new ScaleAnimation(1.0f, zoomScale, 1.0f, zoomScale, w / 2, h / 2);
    scaleAnimation.setDuration(zoomDuration);
    scaleAnimation.setRepeatMode(Animation.REVERSE);
    scaleAnimation.setRepeatCount(1);
  }

  /**
   * Wrapper to start the ripple animation after a motion event
   *
   * @param event {@link MotionEvent} The event that triggers the animation
   * @see ListenableRippleLinearLayout#createAnimation(Float, Float)
   */
  private void animateRipple(final MotionEvent event) {
    createAnimation(event.getX(), event.getY());
  }

  /**
   * Creates the ripple animation
   *
   * @param x {@link Float} The X coordinate of the center of the animation, if not centered on the
   * view
   * @param y @link Float} The Y coordinate of the center of the animation, if not centered on the
   * view
   */
  private void createAnimation(final Float x, final Float y) {
    if (!animationRunning) {
      if (hasToZoom) this.startAnimation(scaleAnimation);

      radiusMax = (float) Math.max(WIDTH, HEIGHT);

      if (rippleType != 2) radiusMax /= 2;

      radiusMax -= ripplePadding;

      if (isCentered || rippleType == 1) {
        this.x = (float) getMeasuredWidth() / 2;
        this.y = (float) getMeasuredHeight() / 2;
      } else {
        this.x = x;
        this.y = y;
      }

      animationRunning = Boolean.TRUE;

      if (rippleType == 1 && originBitmap == null) originBitmap = getDrawingCache(Boolean.TRUE);

      invalidate();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override public boolean onTouchEvent(@NonNull final MotionEvent event) {
    if (gestureDetector.onTouchEvent(event)) {
      animateRipple(event);
    }
    return super.onTouchEvent(event);
  }

  /**
   * {@inheritDoc}
   */
  @Override public boolean onInterceptTouchEvent(final MotionEvent event) {
    this.onTouchEvent(event);
    return super.onInterceptTouchEvent(event);
  }

  /**
   * {@inheritDoc}
   */
  private Bitmap getCircleBitmap(final Integer radius) {
    final Bitmap output = Bitmap.createBitmap(originBitmap.getWidth(), originBitmap.getHeight(),
        Bitmap.Config.ARGB_8888);
    final Canvas canvas = new Canvas(output);
    final Paint paint = new Paint();
    final Rect rect =
        new Rect((int) (x - radius), (int) (y - radius), (int) (x + radius), (int) (y + radius));

    paint.setAntiAlias(Boolean.TRUE);
    canvas.drawARGB(0, 0, 0, 0);
    canvas.drawCircle(x, y, radius, paint);

    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(originBitmap, rect, rect, paint);

    return output;
  }

  /**
   * Sets the listener for ripple completion events
   *
   * @param listener {@link IRippleCompleteListener} The listener
   */
  void setOnRippleCompleteListener(final IRippleCompleteListener listener) {
    this.mOnCompletionListener = listener;
  }

  /**
   * Defines the protocol for listening to ripple completion events
   */
  interface IRippleCompleteListener {

    /**
     * Notifies the completion of a ripple animation
     *
     * @param rippleView {@link ListenableRippleLinearLayout} The view on which the ripple has
     * completed
     */
    void onRippleComplete(final ListenableRippleLinearLayout rippleView);
  }
}
