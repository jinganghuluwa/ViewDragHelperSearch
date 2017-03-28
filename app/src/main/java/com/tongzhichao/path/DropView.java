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
 * Created by tongzhichao on 17-3-27.
 */

public class DropView extends View {
    private static final String TAG = "DropView";
    private Paint mPaint;
    private int viewWidth, viewHeight;

    private Point a, b, one, two;

    private Path mPath;

    private int ar, br;

    public DropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        a = new Point();
        a.set(300, 300);
        b = new Point(400, 400);
        mPath = new Path();
        ar = 20;
        br = 20;
        setPoint();
        setPath();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getWidth();
        viewHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0, viewWidth, 200, mPaint);
        canvas.drawCircle(a.x, a.y, ar, mPaint);
        canvas.drawCircle(b.x, b.y, br, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawPoint(aa.x, aa.y, mPaint);
        canvas.drawPoint(ba.x, bb.y, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(a.x, a.y, mPaint);
        canvas.drawPoint(b.x, b.y, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawPoint(one.x, one.y, mPaint);
        canvas.drawPoint(two.x, two.y, mPaint);
        canvas.drawPath(mPath, mPaint);
    }


    private void setPoint() {
        one = new Point((a.x - b.x) / 3 + b.x, (a.y - b.y) / 3 + b.y);
        two = new Point(2 * (a.x - b.x) / 3 + b.x, 2 * (a.y - b.y) / 3 + b.y);
    }

    Point aa = new Point();
    Point ab = new Point();
    Point ba = new Point();
    Point bb = new Point();

    private void setPath() {
        aa = new Point();
        ab = new Point();
        mPath.reset();
        Point xPoint = new Point();
        for (int i = a.x - ar; i < a.x + ar; i++) {
            for (int j = a.x - ar; j < a.x + ar; j++) {
                xPoint.set(i, j);

                if (getTwoPointsLength(a, xPoint) == ar) {
//                    Log.d(TAG, "a:" + Math.pow(getTwoPointsLength(a, xPoint), 2) + ",b:" + Math.pow(getTwoPointsLength(xPoint, one), 2) +
//                            ",c:" + Math.pow(getTwoPointsLength(a, one), 2));
//                    if (Math.sqrt(Math.pow(getTwoPointsLength(a, xPoint), 2)) + Math.sqrt(Math.pow(getTwoPointsLength(xPoint, one), 2)) ==
//                            Math.sqrt(Math.pow(getTwoPointsLength(a, one), 2))) {
//                        Log.d(TAG, "getPoint:" + xPoint.x + "  " + xPoint.y);
//                        if (aa.x == 0) {
//                            aa.set(xPoint.x, xPoint.y);
//                        } else {
//                            ab.set(xPoint.x, xPoint.y);
//                        }
////                        Log.d(TAG, "getPoint:" + Math.pow(getTwoPointsLength(xPoint, one), 2));
//                    }
                    if (Math.pow(getTwoPointsLength(xPoint, one), 2) > 8400 && Math.pow(getTwoPointsLength(xPoint, one), 2) < 8500) {
//                        Log.d(TAG, "getPoint:" + xPoint.x + "  " + xPoint.y);
//                        Log.d(TAG, "getPoint:" + Math.pow(getTwoPointsLength(xPoint, one), 2));
                        if (aa.x == 0) {
                            aa.set(xPoint.x, xPoint.y);
                        } else {
                            ab.set(xPoint.x, xPoint.y);
                        }
                    }

                }
            }
        }
        mPath.moveTo(aa.x, aa.y);
        for (int i = b.x - br; i < b.x + br; i += 2) {
            for (int j = b.x - br; j < b.x + br; j += 2) {
                xPoint.set(i, j);

                if (getTwoPointsLength(b, xPoint) == br) {
//                    Log.d(TAG, "a:" + Math.pow(getTwoPointsLength(a, xPoint), 2) + ",b:" + Math.pow(getTwoPointsLength(xPoint, one), 2) +
//                            ",c:" + Math.pow(getTwoPointsLength(a, one), 2));
                    if (Math.pow(getTwoPointsLength(xPoint, two), 2) > 8400 && Math.pow(getTwoPointsLength(xPoint, two), 2) < 8500) {
                        Log.d(TAG, "getPoint:" + xPoint.x + "  " + xPoint.y);
                        Log.d(TAG, "getPoint:" + Math.pow(getTwoPointsLength(xPoint, two), 2));
                        if (ba.x == 0) {
                            ba.set(xPoint.x, xPoint.y);
                        } else {
                            bb.set(xPoint.x, xPoint.y);
                        }
                    }
//                    if (Math.pow(ar, 2) + Math.pow(getTwoPointsLength(xPoint, one), 2) == Math.pow(getTwoPointsLength(a, one), 2)) {
//                        if (aa.x != 0) {
//                            aa.set(xPoint.x, xPoint.y);
//                        } else {
//                            ab.set(xPoint.x, xPoint.y);
//                        }
//                    }

                }
            }
        }
        mPath.cubicTo(one.x, one.y, two.x, two.y, ba.x, ba.y);
    }

    private int getTwoPointsLength(Point a, Point b) {
        return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

}
