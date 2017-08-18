package com.tongzhichao.SoundWaves;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tongzhichao.path.CurveView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by tongzhichao on 17-8-18.
 */

public class SoundWaveView extends View {

    private static final String TAG = "SoundWaveView";

    private Paint mPaint;
    private Path mPath;

    private int width;
    private int height;

    private int wide = 2;
    private float density;

    private int count;

    private int variable = 0;

    private Handler handler = new Handler(Looper.myLooper());

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            variable--;
            if (variable <= -getPointCount()) {
                variable = 0;
            }
            invalidate();
            animator();
        }
    };

    private void animator() {
        handler.postDelayed(runnable, 16);
    }

    Point beforeh = new Point();
    Point oneh = new Point();
    Point onea = new Point();
    Point twoh = new Point();
    Point twoa = new Point();
    Point oneq = new Point();
    Point twoq = new Point();

    public SoundWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        density = getResources().getDisplayMetrics().density;
        wide *= density;
        mPath = new Path();
        animator();
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
            width = (int) (density * 200);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (density * 100);
        }
        count = getPointCount();
        setMeasuredDimension(width, height);
    }

    private int getPointCount() {
//        Log.d(TAG, "width " + width + " wide " + wide);
        return width / wide + 1;
    }

    private float getSinY(int x) {
//        Log.d(TAG, "x " + x + " y " + Math.sin(((float) (x * wide) / (width / 18))));

        return (float) (Math.sin(((float) (getSinX(x) * wide) / (width / (Math.PI * 6)))) * 100);
    }

    private float getSinX(int x) {
        return x + variable;
    }

    private float getAttenuation(int x) {
        return (float) (Math.sin(((float) (x * wide) / (width / Math.PI)))) * getSinY(x);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawLine(0, height / 2, width, height / 2, mPaint);
        mPath.reset();
        setLine(1);
        mPath.moveTo(getX(0), getY(getAttenuation(0)));
        mPaint.setColor(0xFF00FF00);
        setPath();
        canvas.drawPath(mPath, mPaint);
        setLine(2);
        mPath.moveTo(getX(0), getY(getAttenuation(0)));
        mPaint.setColor(0x8800FF00);
        setPath();
        canvas.drawPath(mPath, mPaint);

        setLine(3);
        mPath.moveTo(getX(0), getY(getAttenuation(0)));
        mPaint.setColor(0x6600FF00);
        setPath();
        canvas.drawPath(mPath, mPaint);

        setLine(4);
        mPath.moveTo(getX(0), getY(getAttenuation(0)));
        mPaint.setColor(0x5500FF00);
        setPath();
        canvas.drawPath(mPath, mPaint);

        setLine(5);
        mPath.moveTo(getX(0), getY(getAttenuation(0)));
        mPaint.setColor(0x4400FF00);
        setPath();
        canvas.drawPath(mPath, mPaint);
//        for (int i = 0; i < count; i++) {
//            canvas.drawPoint(getX(i), getY(getAttenuation(i)), mPaint);
////            Log.d(TAG, "getX " + i + " getY " + getSinY(i));
//            if (i == count - 1) {
////                Log.d(TAG, "i " + i + " getPointCount " + getPointCount());
//            }
//        }
    }

    private int line = 1;

    private void setLine(int line) {
        this.line = line;
    }

    private void setPath() {
        for (int i = 0; i < count - 1; i++) {
            Point one = new Point(i);
            Point two = new Point(i + 1);

            if (i == 0) {
                Point three = new Point(i + 2);
                oneh.setX((one.getX() + two.getX()) / 2);
                oneh.setY((one.getY() + two.getY()) / 2);
                twoh.setX((two.getX() + three.getX()) / 2);
                twoh.setY((two.getY() + three.getY()) / 2);
                twoa.setX((oneh.getX() + twoh.getX()) / 2);
                twoa.setY((oneh.getY() + twoh.getY()) / 2);
                oneq.setX(oneh.getX() + two.getX() - twoa.getX());
                oneq.setY(oneh.getY() + two.getY() - twoa.getY());
                mPath.quadTo(getX(oneq.getX()), getY(oneq.getY()),
                        getX(two.getX()), getY(two.getY()));
            } else if (i == count - 2) {
                Point before = new Point(i - 1);
                oneh.setX((one.getX() + two.getX()) / 2);
                oneh.setY((one.getY() + two.getY()) / 2);
                beforeh.setX((one.getX() + before.getX()) / 2);
                beforeh.setY((one.getY() + before.getY()) / 2);
                onea.setX((oneh.getX() + beforeh.getX()) / 2);
                onea.setY((oneh.getY() + beforeh.getY()) / 2);
                oneq.setX(oneh.getX() + one.getX() - onea.getX());
                oneq.setY(oneh.getY() + one.getY() - onea.getY());
                mPath.quadTo(getX(oneq.getX()), getY(oneq.getY()),
                        getX(two.getX()), getY(two.getY()));
            } else if (i < count - 2) {
                Point three = new Point(i + 2);
                mPath.moveTo(getX(i), getY(getAttenuation(i)));
                Point before = new Point(i - 1);
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
                mPath.cubicTo(getX(oneq.getX()), getY(oneq.getY()),
                        getX(twoq.getX()), getY(twoq.getY()),
                        getX(two.getX()), getY(two.getY()));
            }
        }
    }

    private float getX(float x) {
        return x * wide;
    }

    private float getY(float y) {
        if (line == 1) {
            return height / 2 - y;
        } else if (line == 2) {
            return height / 2 - 4 * (y / 5);
        } else if (line == 3) {
            return height / 2 - 3 * (y / 5);
        } else if (line == 4) {
            return height / 2 - 2 * (y / 5);
        }
        return height / 2 - (y / 5);
    }

    public class Point {
        private float x = 0;
        private float y = 0;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Point(float x) {
            this.x = x;
            this.y = getAttenuation((int) x);
        }

        public Point() {

        }
    }
}
