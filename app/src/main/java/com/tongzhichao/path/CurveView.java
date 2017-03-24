package com.tongzhichao.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongzhichao on 17-3-22.
 */

public class CurveView extends View {
    private List<Point> mData = new ArrayList<>();

    private Paint mPaint;
    private Path mPath;
    private int width;
    private int height;

    private int wide = 30;
    private float density;

    Point beforeh = new Point();
    Point oneh = new Point();
    Point onea = new Point();
    Point twoh = new Point();
    Point twoa = new Point();
    Point oneq = new Point();
    Point twoq = new Point();

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        density = getResources().getDisplayMetrics().density;
        wide *= density;
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        EXACTLY  确定值或者matchparent
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
//            int desired = getPaddingLeft() + getBitmapWidth() + getPaddingRight();
//            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
//            int desired = getPaddingTop() + getBitmapHeight() + getPaddingBottom();
//            height = desired;
        }
        width = widthSize;
        height = heightSize;
        setMeasuredDimension(widthSize, heightSize);
    }

    public void setData(List<Point> data) {
        mData.clear();
        mData.addAll(data);
        requestLayout();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.GREEN);
        for (int i = 0; i < 30; i++) {
            canvas.drawLine(0, height - (i * wide), wide * 19, height - (i * wide), mPaint);
            if (i < 20) {
                canvas.drawLine(i * wide, height - (wide * 29), i * wide, height, mPaint);
            }

        }

        mPath.moveTo(getX(0), getY((float) mData.get(0).getY()));
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        for (int i = 0; i < mData.size() - 1; i++) {
            mPaint.setColor(Color.RED);
            Point one = mData.get(i);
            Point two = mData.get(i + 1);
            Point three = mData.get(i + 2);
            if (i == 0 ) {
                oneh.setX((one.getX() + two.getX()) / 2);
                oneh.setY((one.getY() + two.getY()) / 2);
                twoh.setX((two.getX() + three.getX()) / 2);
                twoh.setY((two.getY() + three.getY()) / 2);
                twoa.setX((oneh.getX() + twoh.getX()) / 2);
                twoa.setY((oneh.getY() + twoh.getY()) / 2);
                oneq.setX(oneh.getX() + two.getX() - twoa.getX());
                oneq.setY(oneh.getY() + two.getY() - twoa.getY());
                mPath.quadTo(getX((float) oneq.getX()), getY((float) oneq.getY()),
                        getX((float) two.getX()), getY((float) two.getY()));
            }else if( i == mData.size() - 2){
                Point before = mData.get(i - 1);
                oneh.setX((one.getX() + two.getX()) / 2);
                oneh.setY((one.getY() + two.getY()) / 2);
                beforeh.setX((one.getX() + before.getX()) / 2);
                beforeh.setY((one.getY() + before.getY()) / 2);
                onea.setX((oneh.getX() + beforeh.getX()) / 2);
                onea.setY((oneh.getY() + beforeh.getY()) / 2);
                oneq.setX(oneh.getX() + one.getX() - onea.getX());
                oneq.setY(oneh.getY() + one.getY() - onea.getY());
                mPath.quadTo(getX((float) oneq.getX()), getY((float) oneq.getY()),
                        getX((float) two.getX()), getY((float) two.getY()));
            }else if( i == mData.size() - 2){
                mPath.moveTo(getX((float) mData.get(i).getX()), getY((float) mData.get(i).getY()));
                Point before = mData.get(i - 1);
                beforeh.setX((one.getX() + before.getX()) / 2);
                beforeh.setY((one.getY() + before.getY()) / 2);
                oneh.setX((one.getX() + two.getX()) / 2);
                oneh.setY((one.getY() + two.getY()) / 2);
                twoh.setX((two.getX() + three.getX()) / 2);
                twoh.setY((two.getY() + three.getY()) / 2);
                onea.setX((beforeh.getX() + oneh.getX()) / 2);
                onea.setY((beforeh.getY() + oneh.getY()) / 2);
                twoa.setX((oneh.getX() + twoh.getX()) / 2);
                twoa.setY((oneh.getY() + twoh.getY()) / 2);
                oneq.setX(oneh.getX() + one.getX() - onea.getX());
                oneq.setY(oneh.getY() + one.getY() - onea.getY());
                twoq.setX(oneh.getX() + two.getX() - twoa.getX());
                twoq.setY(oneh.getY() + two.getY() - twoa.getY());
                mPath.cubicTo(getX((float) oneq.getX()), getY((float) oneq.getY()),
                        getX((float) twoq.getX()), getY((float) twoq.getY()),
                        getX((float) two.getX()), getY((float) two.getY()));
                mPaint.setColor(Color.BLUE);
                canvas.drawPoint(getX((float) oneh.getX()), getY((float) oneh.getY()), mPaint);
                canvas.drawPoint(getX((float) twoh.getX()), getY((float) twoh.getY()), mPaint);
                mPaint.setColor(Color.YELLOW);
                canvas.drawPoint(getX((float) onea.getX()), getY((float) onea.getY()), mPaint);
                canvas.drawPoint(getX((float) twoa.getX()), getY((float) twoa.getY()), mPaint);
                mPaint.setColor(Color.GRAY);
                canvas.drawPoint(getX((float) oneq.getX()), getY((float) oneq.getY()), mPaint);
                canvas.drawPoint(getX((float) twoq.getX()), getY((float) twoq.getY()), mPaint);
            }
        }
        canvas.drawPath(mPath, mPaint);
    }

    private float getX(float x) {
        return x * wide;
    }

    private float getY(float y) {
        return height - y * wide;
    }

    public static class Point {
        private double x = 0;
        private double y = 0;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;

        }

        public Point() {

        }
    }
}
