package com.tongzhichao.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tongzhichao on 17-3-28.
 */

public class PointView extends View {

    private Paint mPaint;
    private Path mPath;
    private int width, height, r, paintWidth = 3;
    private Point mCenter, mTop, mBottom, mLeft, mRight, mTopCopy, mBottomCopy, mLeftCopy, mRightCopy;
    private float mControlLength;

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
        initPoints();
    }

    private void initPoints() {
        mCenter = new Point(width / 2, height / 2);
        r = width >= height ? height : width;
        r /= 6;
        r -= paintWidth;
        mTop = new Point(mCenter.x, mCenter.y - r);
        mBottom = new Point(mCenter.x, mCenter.y + r);
        mLeft = new Point(mCenter.x - r, mCenter.y);
        mRight = new Point(mCenter.x + r, mCenter.y);
        mTopCopy = new Point(mTop.x, mTop.y);
        mBottomCopy = new Point(mBottom.x, mBottom.y);
        mLeftCopy = new Point(mLeft.x, mLeft.y);
        mRightCopy = new Point(mRight.x, mRight.y);
        mControlLength = (float) (r * 4 * Math.tan(Math.PI / 8) / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        drawCircle(canvas);
        drawPoints(canvas);
    }

    private void drawCoordinate(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(paintWidth);
        canvas.drawLine(0, height / 2, width, height / 2, mPaint);
        canvas.drawLine(width / 2, 0, width / 2, height, mPaint);
    }

    private void drawPoints(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(paintWidth * 2);
        canvas.drawPoint(mTop.x, mTop.y, mPaint);
        canvas.drawPoint(mBottom.x, mBottom.y, mPaint);
        canvas.drawPoint(mLeft.x, mLeft.y, mPaint);
        canvas.drawPoint(mRight.x, mRight.y, mPaint);

        canvas.drawPoint(mTopCopy.x, mTopCopy.y, mPaint);
        canvas.drawPoint(mBottomCopy.x, mBottomCopy.y, mPaint);
        canvas.drawPoint(mLeftCopy.x, mLeftCopy.y, mPaint);
        canvas.drawPoint(mRightCopy.x, mRightCopy.y, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(paintWidth);
        initPath();
        canvas.drawPath(mPath, mPaint);
    }

    private void initPath() {
        mPath.reset();
        mPath.moveTo(mTopCopy.x, mTopCopy.y);
        mPath.cubicTo(mTopCopy.x + mControlLength, mTopCopy.y, mRightCopy.x, mRightCopy.y - mControlLength, mRightCopy.x, mRightCopy.y);
        mPath.cubicTo(mRightCopy.x, mRightCopy.y + mControlLength, mBottomCopy.x + mControlLength, mBottomCopy.y, mBottomCopy.x, mBottomCopy.y);
        mPath.cubicTo(mBottomCopy.x - mControlLength, mBottomCopy.y, mLeftCopy.x, mLeftCopy.y + mControlLength, mLeftCopy.x, mLeftCopy.y);
        mPath.cubicTo(mLeftCopy.x, mLeftCopy.y - mControlLength, mTopCopy.x - mControlLength, mTopCopy.y, mTopCopy.x, mTopCopy.y);
    }

    public void setTopPoint(int progress) {
        int size = r * progress / 50;
        mTopCopy.set(mTop.x, mTop.y - size);
        invalidate();
    }

    public void setBottomPoint(int progress) {
        int size = r * progress / 50;
        mBottomCopy.set(mBottom.x, mBottom.y + size);
        invalidate();
    }

    public void setLeftPoint(int progress) {
        int size = r * progress / 50;
        mLeftCopy.set(mLeft.x - size, mLeft.y);
        invalidate();
    }

    public void setRightPoint(int progress) {
        int size = r * progress / 50;
        mRightCopy.set(mRight.x + size, mLeft.y);
        invalidate();
    }
}
