package com.tongzhichao.matrix;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;


import java.io.IOException;
import java.util.jar.Attributes;

public class ScaleImageView extends ImageView {

    private static final String TAG = "ScaleImageView";

    private static final int ANGLE0 = 0;//水平状态
    private static final int ANGLE90 = 1;//逆时针90度
    private static final int ANGLE180 = 2;//翻转180
    private static final int ANGLE270 = 3;//逆时针270度

    private static final int NO_BLANK = 0;//没空白
    private static final int H_BLANK = 1;//水平有空白
    private static final int V_BLANK = 2;//竖直有空白
    private static final int D_BLANK = 3;//都有空白

    private float x_down = 0;
    private float y_down = 0;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float oldRotation = 0;
    private Matrix matrix = new Matrix();
    private Matrix matrixTemp = new Matrix();
    private Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    private float viewWidth, viewHeight, bmWidth, bmHeight;

    private Bitmap mBitmap;
    private Drawable mDrawable;

    private float minScale;
    private float maxScale;

    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmap = bm;
        bmWidth = bm.getWidth();
        bmHeight = bm.getHeight();
    }


    public void drawableToBitmap(Drawable drawable) {
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                            : Config.RGB_565);
        }
        Canvas canvas = new Canvas(mBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
    }

    public ScaleImageView(Context context) {
        this(context, null);

    }

    public ScaleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure viewWidth : " + viewWidth + " , viewHeight : " + viewHeight);
        sizeAdjust();
        moveToCenter();
    }

    private void sizeAdjust() {
        float scaleX = 1;
        float scaleY = 1;
        switch (getAngle()) {
            case ANGLE0:
            case ANGLE180:
                scaleX = viewWidth / bmWidth;
                scaleY = viewHeight / bmHeight;
                break;
            case ANGLE90:
            case ANGLE270:
                scaleX = viewWidth / bmHeight;
                scaleY = viewHeight / bmWidth;
                break;
        }
        minScale = Math.min(scaleX, scaleY);
        if (minScale > 1) {
            minScale = 1;
        }
        matrixTemp.postScale(minScale / getScale(), minScale / getScale());
        matrix.set(matrixTemp);
        invalidate();
        maxScale = 3 * minScale;

    }

    /**
     * 移动到屏幕中间，如果图片比屏幕大不动，如果宽度小于屏幕宽度，水平方向移到中间
     */
    private void moveToCenter() {
        float redundantXSpace = viewWidth - getCurrentWidth();
        float redundantYSpace = viewHeight - getCurrentHeight();
        redundantXSpace /= (float) 2;
        redundantYSpace /= (float) 2;
        float scale = getScale();
        float x = getRect().left;
        float y = getRect().top;
        switch (hasBlank()) {
            case D_BLANK:
                matrixTemp.postTranslate(redundantXSpace - x, redundantYSpace - y);
                break;
            case H_BLANK:
                if (y > 0) {
                    matrixTemp.postTranslate(redundantXSpace - x, -y);
                } else if (getRect().bottom < viewHeight) {
                    matrixTemp.postTranslate(redundantXSpace - x, viewHeight - getRect().bottom);
                } else {
                    matrixTemp.postTranslate(redundantXSpace - x, 0);
                }
                break;
            case V_BLANK:
                if (x > 0) {
                    matrixTemp.postTranslate(-x, redundantYSpace - y);
                } else if (getRect().right < viewWidth) {
                    matrixTemp.postTranslate(viewWidth - getRect().right, redundantYSpace - y);
                } else {
                    matrixTemp.postTranslate(0, redundantYSpace - y);
                }
                break;
            case NO_BLANK:
                if (x > 0) {
                    matrixTemp.postTranslate(-x, 0);
                } else if (getRect().right < viewWidth) {
                    matrixTemp.postTranslate(viewWidth - getRect().right, 0);
                }
                if (y > 0) {
                    matrixTemp.postTranslate(0, -y);
                } else if (getRect().bottom < viewHeight) {
                    matrixTemp.postTranslate(0, viewHeight - getRect().bottom);
                }

                break;
        }
        matrix.set(matrixTemp);
        invalidate();
    }

    /**
     * 是否有空白，即宽度或者高度是否小于控件宽度或者高度
     *
     * @return D_BLANK：宽度高度皆小于控件宽高  H_BLANK：宽度小于控件宽度
     * V_BLANK：高度小于控件高度  NO_BLANK：宽高大于控件宽高
     */
    private int hasBlank() {
        float rWidth = getCurrentWidth();
        float rHeight = getCurrentHeight();

        if (rWidth < viewWidth && rHeight < viewHeight) {
            return D_BLANK;
        } else if (rWidth >= viewWidth && rHeight >= viewHeight) {
            return NO_BLANK;
        } else if (rWidth < viewWidth) {
            return H_BLANK;
        } else {
            return V_BLANK;
        }
    }

    /**
     * 获得当前宽度
     *
     * @return 缩放后宽度
     */
    private float getCurrentWidth() {
        RectF rectF = getRect();
        return rectF.right - rectF.left;
    }

    /**
     * 获得当前高度
     *
     * @return 缩放后高度
     */
    private float getCurrentHeight() {
        RectF rectF = getRect();
        return rectF.bottom - rectF.top;
    }

    /**
     * 旋转，逆时针旋转90度
     */
    public void rotate() {
        matrixTemp.postRotate(-90, getX() + viewWidth / 2, getY() + viewHeight / 2);
        matrix.set(matrixTemp);
        sizeAdjust();
        Log.d(TAG, "rotate");
        moveToCenter();
    }

    /**
     * 父view viewpager是否可滑动
     *
     * @return 可滑动放回true
     */
    public boolean pagerCanScroll() {
        return !horizontalDragCheck();
    }

    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (mDrawable != null) {
            drawableToBitmap(mDrawable);
        }
        canvas.drawBitmap(mBitmap, matrix, null);
        canvas.restore();
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                x_down = event.getX();
                y_down = event.getY();
                savedMatrix.set(matrix);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                oldDist = spacing(event);
                oldRotation = rotation(event);
                savedMatrix.set(matrix);
                midPoint(mid, event);
                break;
            case MotionEvent.ACTION_MOVE:
                matrixTemp.set(savedMatrix);
                if (mode == ZOOM) {
//                    Log.d(TAG, "ZOOM  "+getTestScale());
                    float rotation = rotation(event) - oldRotation;
                    float newDist = spacing(event);
                    float scale = newDist / oldDist;

                    matrixTemp.postRotate(rotation, mid.x, mid.y);// 旋轉
                    matrixTemp.postScale(scale, scale, mid.x, mid.y);// 縮放

                    matrix.set(matrixTemp);
                    invalidate();
                } else if (mode == DRAG) {
//                    Log.d(TAG, "DRAG  "+getTestScale());
                    matrixTemp.postTranslate(event.getX() - x_down, event.getY()
                            - y_down);// 平移
                    if (dragCheck()) {
//                        Log.d(TAG, "CAN DRAG  "+getTestScale());
                        matrix.set(matrixTemp);
                        invalidate();
                    } else {
//                        Log.d(TAG, "CAN NOT DRAG  "+getTestScale());
                        matrixTemp.set(matrix);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "ACTION_UP  " + getCurrentRotation() + " ,oldRotation : " + oldRotation);
//                matrixTemp.postRotate(getCurrentRotation() - oldRotation, mid.x, mid.y);
//                float tempscale = scaleCheck();
//                Log.d(TAG, "ACTION_UP  " + tempscale);
//                matrixTemp.postScale(tempscale, tempscale, mid.x, mid.y);
//                matrix.set(matrixTemp);
//                moveToCenter();
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
//                Log.d(TAG, "ACTION_POINTER_UP  "+getTestScale());
                if (mode == ZOOM) {
                    savedMatrix.set(matrix);
                    int index = event.getActionIndex();
                    if (index == 1) {
                        x_down = event.getX();
                        y_down = event.getY();
                    } else {
                        x_down = event.getX(1);
                        y_down = event.getY(1);
                    }
                    mode = DRAG;
//                    Log.d(TAG, "ACTION_POINTER_UP  DRAG  "+getTestScale());
                }
                break;
        }
        return true;
    }

    /**
     * 缩放检查，ontouch结束检查，如果缩放范围不合法会回弹
     *
     * @return 返回当前应缩放值
     */
    private float scaleCheck() {
        float scale = getScale();
        if (scale >= minScale && scale <= maxScale) {
            return 1;
        } else if (scale < minScale) {
            return minScale / scale;
        } else {
            return maxScale / scale;
        }
    }

    /**
     * 拖拽检查
     *
     * @return 可拖拽返回true，反之返回false
     */
    private boolean dragCheck() {
        RectF rectF = getRect();
        float rWidth = rectF.right - rectF.left;
        float rHeight = rectF.bottom - rectF.top;
        if (rWidth <= viewWidth && rHeight <= viewHeight) {
//            Log.d(TAG, "dragCheck  :  <<");
            return false;
        } else if (rWidth > viewWidth) {
            boolean b = rectF.left < 0 && rectF.right > viewWidth;
//            Log.d(TAG, "dragCheck  :  rWidth > viewWidth");
            return true;
        } else if (rHeight > viewHeight) {
            boolean b = rectF.top < 0 && rectF.bottom > viewHeight;
//            Log.d(TAG, "dragCheck  :  rHeight > viewHeight");
            return true;
        }
        return false;
    }

    private boolean horizontalDragCheck() {
        RectF rectF = getRect();
        float rWidth = rectF.right - rectF.left;
        float rHeight = rectF.bottom - rectF.top;
        if (rWidth <= viewWidth && rHeight <= viewHeight) {
            return false;
        } else {
            boolean b = rectF.left < 0 && rectF.right > viewWidth;
            return b;
        }
    }

    /**
     * 获取当前缩放比例
     *
     * @return 当前图像缩放比例
     */
    private float getScale() {
        float[] f = new float[9];
        matrix.getValues(f);
        switch (getAngle()) {
            case ANGLE0:
                return f[0];
            case ANGLE90:
                return f[1];
            case ANGLE180:
                return -f[0];
            case ANGLE270:
                return -f[1];
            default:
                return f[0];
        }
    }

    private float getTestScale() {
        float[] f = new float[9];
        matrixTemp.getValues(f);
        switch (getAngle()) {
            case ANGLE0:
                return f[0];
            case ANGLE90:
                return f[1];
            case ANGLE180:
                return -f[0];
            case ANGLE270:
                return -f[1];
            default:
                return f[0];
        }
    }

    private RectF getRect() {
        float[] f = new float[9];
        matrixTemp.getValues(f);
        RectF rectF = new RectF();
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
//        float x2 = f[0] * gintama.getWidth() + f[1] * 0 + f[2];
//        float y2 = f[3] * gintama.getWidth() + f[4] * 0 + f[5];
//        float x3 = f[0] * 0 + f[1] * gintama.getHeight() + f[2];
//        float y3 = f[3] * 0 + f[4] * gintama.getHeight() + f[5];
        float x4 = f[0] * bmWidth + f[1] * bmHeight + f[2];
        float y4 = f[3] * bmWidth + f[4] * bmHeight + f[5];
        switch (getAngle()) {
            case ANGLE0:
                rectF.left = x1;
                rectF.right = x4;
                rectF.top = y1;
                rectF.bottom = y4;
                break;
            case ANGLE90:
                rectF.left = x1;
                rectF.right = x4;
                rectF.top = y4;
                rectF.bottom = y1;
                break;
            case ANGLE180:
                rectF.left = x4;
                rectF.right = x1;
                rectF.top = y4;
                rectF.bottom = y1;
                break;
            case ANGLE270:
                rectF.left = x4;
                rectF.right = x1;
                rectF.top = y1;
                rectF.bottom = y4;
                break;
        }
        return rectF;
    }


    private int getAngle() {
        float[] f = new float[9];
        matrixTemp.getValues(f);
        RectF rectF = new RectF();
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x4 = f[0] * bmWidth + f[1] * bmHeight + f[2];
        float y4 = f[3] * bmWidth + f[4] * bmHeight + f[5];
        if ((x4 > x1) && (y4 > y1)) {
            return ANGLE0;
        } else if ((x4 > x1) && (y1 > y4)) {
            return ANGLE90;
        } else if ((x1 > x4) && (y1 > y4)) {
            return ANGLE180;
        } else if ((x1 > x4) && (y4 > y1)) {
            return ANGLE270;
        }
        return ANGLE0;
    }


    // 触碰两点间距离
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取手势中心点
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    // 取旋转角度
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float getCurrentRotation() {
        float[] f = new float[9];
        matrixTemp.getValues(f);
        double nowRotation = Math.asin(f[0]);
        while (nowRotation < 0) {
            nowRotation += 360;
        }
        while (nowRotation > 360) {
            nowRotation -= 360;
        }
        if (nowRotation >= 0 && nowRotation < 45) {
            return 0;
        } else if (nowRotation >= 45 && nowRotation < 90) {
            return 90;
        } else if (nowRotation >= 90 && nowRotation < 135) {
            return 90;
        } else if (nowRotation >= 135 && nowRotation < 180) {
            return 180;
        } else if (nowRotation >= 180 && nowRotation < 225) {
            return 180;
        } else if (nowRotation >= 225 && nowRotation < 270) {
            return 270;
        } else if (nowRotation >= 275 && nowRotation < 325) {
            return 270;
        } else if (nowRotation >= 325 && nowRotation < 360) {
            return 360;
        }
        return 0;
    }


}