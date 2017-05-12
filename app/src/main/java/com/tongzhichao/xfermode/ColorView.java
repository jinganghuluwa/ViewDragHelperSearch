package com.tongzhichao.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-5-12.
 */

public class ColorView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private int color = Color.GRAY;

    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = createBitmap();
        mPaint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }

    private Bitmap createBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getBitmapWidth(), getBitmapHeight(),
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(color);
        Canvas canvas = new Canvas(bitmap);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        return bitmap;
    }

    public void setColor(int color){
        this.color = color;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = getPaddingLeft() + getBitmapWidth() + getPaddingRight();
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = getPaddingTop() + getBitmapHeight() + getPaddingBottom();
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    private int getBitmapWidth() {
        return mBitmap.getWidth();
    }

    private int getBitmapHeight() {
        return mBitmap.getHeight();
    }
}
