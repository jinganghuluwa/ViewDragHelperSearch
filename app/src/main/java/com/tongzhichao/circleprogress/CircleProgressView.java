package com.tongzhichao.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by tzc on 2017/3/20.
 */

public class CircleProgressView extends View {
    private Paint mPaint;
    private int width, height;
    RectF rectF;
    private int lineWidth=80;
    private int angle=0;

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getWidth();
        this.height = getHeight();
        rectF = new RectF(lineWidth/2, lineWidth/2, width-lineWidth/2, height-lineWidth/2);

    }
    private void initAnimator(int start,int end) {
        ValueAnimator anim = ValueAnimator.ofInt(start, end);
        anim.setDuration(Math.abs(end-start)*3);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle =  (int)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, 0, angle, false, mPaint);
    }

    public void setProgress(int progress){

        int toprogress = (int) (progress*3.6);
        initAnimator(angle,toprogress);
    }

}
