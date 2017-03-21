package com.tongzhichao.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tzc on 2017/3/20.
 */

public class Circle extends View {

    private Paint mPaint;
    private Path mPath;
    private int width;
    private int height;
    private int param=0;
    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0,height/2);
        mPath.cubicTo(0,param,width,param,width,height/2);
        mPath.moveTo(0,height/2);
        mPath.cubicTo(0,height-param,width,height-param,width,height/2);
        canvas.drawPath(mPath,mPaint);
    }

    public void setParam(int param){
        this.param = (int) (param*getResources().getDisplayMetrics().density);
        invalidate();
    }
}
