package com.tongzhichao.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tongzhichao on 17-3-28.
 */

public class FourPathCircle extends View {

    private static final String TAG = "FourPathCircle";

    private Paint mPaint;
    private Path mPath;
    private int width, height, r, paintWidht = 3;
    private Point mCenter, mTop, mBottom, mLeft, mRight, mTopDraw;
    private float bei;
    private int sideSize, bottomSize;

    public FourPathCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(paintWidht);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
        initPoints();
        initPath();
        invalidate();
    }

    private void initPoints() {
        mCenter = new Point(width / 2, height / 2);
        r = width >= height ? height : width;
        r /= 2;
        r -= paintWidht;
        mTop = new Point(mCenter.x, mCenter.y - r);
        mBottom = new Point(mCenter.x, mCenter.y + r);
        mLeft = new Point(mCenter.x - r, mCenter.y);
        mRight = new Point(mCenter.x + r, mCenter.y);
        mTopDraw = new Point(mTop.x, mTop.y);
        bei = (float) (r * 4 * Math.tan(Math.PI / 8) / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    private void initPath() {
        mPath.reset();
        mPath.moveTo(mTopDraw.x, mTopDraw.y);
        mPath.cubicTo(mTop.x + bei, mTop.y, mRight.x, mRight.y - bei, mRight.x, mRight.y);
        mPath.cubicTo(mRight.x - sideSize, mRight.y + bei, mBottom.x + bei, mBottom.y - bottomSize, mBottom.x, mBottom.y);
        mPath.cubicTo(mBottom.x - bei, mBottom.y - bottomSize, mLeft.x + sideSize, mLeft.y + bei, mLeft.x, mLeft.y);
        mPath.cubicTo(mLeft.x, mLeft.y - bei, mTop.x - bei, mTop.y, mTopDraw.x, mTopDraw.y);

    }

    public void setTopPoint(int progress) {
        int count = progress * r / 100;
        mTopDraw.set(mTop.x, mTop.y + count);
        initPath();
        invalidate();
    }

    public void setSidePoint(int progress) {
        sideSize = progress * r / 100;
        initPath();
        invalidate();
    }

    public void setBottomPoint(int progress) {
        bottomSize = progress * r / 100;
        initPath();
        invalidate();
    }
}
