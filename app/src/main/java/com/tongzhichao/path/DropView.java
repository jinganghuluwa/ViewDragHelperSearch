package com.tongzhichao.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.logging.LogRecord;

/**
 * Created by tongzhichao on 17-3-27.
 */

public class DropView extends View {
    private static final String TAG = "DropView";
    private Paint mPaint;
    private int viewWidth, viewHeight;

    private Point mCircleOneCenter, mCircleTwoCenter, mPointHalf, mCircleOneTangentPointOne,
            mCircleOneTangentPointTwo, mCircleTwoTangentPointOne, mCircleTwoTangentPointTwo;

    private boolean mIsTouching = false;

    private boolean mIsBacking = false;

    private Path mPath;

    private int mCircleOneRadius, mCircleTwoRadius;

    public DropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getWidth();
        viewHeight = getHeight();
        mCircleOneCenter = new Point();
        mCircleOneCenter.set(viewWidth / 2, viewHeight / 2);
        mCircleTwoCenter = new Point(mCircleOneCenter.x, mCircleOneCenter.y);
        mPath = new Path();
        mCircleOneRadius = 30;
        mCircleTwoRadius = 50;
        setPoint();
        setPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPoint();
        setPath();
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCircleOneCenter.x, mCircleOneCenter.y, mCircleOneRadius, mPaint);
        if (mIsTouching || mIsBacking) {
            canvas.drawCircle(mCircleTwoCenter.x, mCircleTwoCenter.y, mCircleTwoRadius, mPaint);
            canvas.drawPath(mPath, mPaint);
        }

    }

    private Point mTangent = new Point();


    private void setPoint() {
        mPointHalf = new Point((mCircleOneCenter.x - mCircleTwoCenter.x) / 2 + mCircleTwoCenter.x, (mCircleOneCenter.y - mCircleTwoCenter.y) / 2 + mCircleTwoCenter.y);

        mCircleOneTangentPointOne = new Point();
        mCircleOneTangentPointTwo = new Point();
        mCircleTwoTangentPointOne = new Point();
        mCircleTwoTangentPointTwo = new Point();

        //两个圆心距离
        int length = getTwoPointsLength(mCircleOneCenter, mCircleTwoCenter);
        if (length == 0) {
            return;
        }
        //两个圆半径之差
        int difference = Math.abs(mCircleTwoRadius - mCircleOneRadius);

        //两圆心连线与切线形成的角度
        double angleA = Math.asin((double) difference / (double) length);
        //两圆心连线的角度
        double angleB;
        if (mCircleOneCenter.y - mCircleTwoCenter.y == 0 && mCircleTwoCenter.x > mCircleOneCenter.x) {
            angleB = 0;
        } else if (mCircleOneCenter.y - mCircleTwoCenter.y == 0 && mCircleTwoCenter.x < mCircleOneCenter.x) {
            angleB = 0;
        } else {
            angleB = Math.atan((double) (mCircleTwoCenter.y - mCircleOneCenter.y) / (double) (mCircleOneCenter.x - mCircleTwoCenter.x));
            angleB = angleB * 180 / Math.PI;
        }
        if (angleB == 90) {
            angleB = -89;
        }
        if (angleB == -90) {
            angleB = 90;
        }
        angleA = angleA * 180 / Math.PI;
        //一条切线的角度
        double angleC = angleB - angleA;
        //另一条切线的角度
        double angleD = angleB + angleA;
        //切线斜率
        double slope = Math.tan(angleC * Math.PI / 180);

        slope = 1 / slope;

        //切线长度
        int tangentLength = (int) (Math.cos(angleA * Math.PI / 180) * length);

        int y = (int) (Math.sin(angleC * Math.PI / 180) * tangentLength);
        int x = (int) (Math.cos(angleC * Math.PI / 180) * tangentLength);
        if (mCircleTwoCenter.x < mCircleOneCenter.x || (angleC < -90 && slope < 0)) {
            x = -x;
            y = -y;
        }

        mTangent.set(mCircleOneCenter.x + x, mCircleOneCenter.y - y);


        mCircleTwoTangentPointOne.set((int) (mCircleTwoCenter.x + ((double) mCircleTwoRadius / (double) difference) * (mTangent.x - mCircleTwoCenter.x)),
                (int) (mCircleTwoCenter.y + ((double) mCircleTwoRadius / (double) difference) * (mTangent.y - mCircleTwoCenter.y)));
        mCircleOneTangentPointOne.set(mCircleTwoTangentPointOne.x - mTangent.x + mCircleOneCenter.x,
                mCircleTwoTangentPointOne.y - mTangent.y + mCircleOneCenter.y);


        slope = Math.tan(angleD * Math.PI / 180);


        y = (int) (Math.sin(angleD * Math.PI / 180) * tangentLength);
        x = (int) (Math.cos(angleD * Math.PI / 180) * tangentLength);
        if (mCircleTwoCenter.x < mCircleOneCenter.x || (angleD < -90 && slope > 0)) {
            x = -x;
            y = -y;
        }
        mTangent.set(mCircleOneCenter.x + x, mCircleOneCenter.y - y);
        mCircleTwoTangentPointTwo.set((int) (mCircleTwoCenter.x + ((double) mCircleTwoRadius / (double) difference) * (mTangent.x - mCircleTwoCenter.x)),
                (int) (mCircleTwoCenter.y + ((double) mCircleTwoRadius / (double) difference) * (mTangent.y - mCircleTwoCenter.y)));
        mCircleOneTangentPointTwo.set(mCircleTwoTangentPointTwo.x - mTangent.x + mCircleOneCenter.x,
                mCircleTwoTangentPointTwo.y - mTangent.y + mCircleOneCenter.y);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsBacking) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getTwoPointsLength(mCircleOneCenter, new Point((int) event.getX(), (int) event.getY())) < mCircleOneRadius) {
                    mIsTouching = true;
                } else {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mCircleTwoCenter.set((int) event.getX(), (int) event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsTouching && canBack()) {
                    Log.d(TAG, "up canback");
                    mIsBacking = true;
                    new Thread(mBackRunable).start();
                }
                mIsTouching = false;
                invalidate();
                break;

        }
        return true;
    }

    private void setPath() {

        mPath.reset();
        mPath.moveTo(mCircleOneTangentPointOne.x, mCircleOneTangentPointOne.y);
        mPath.quadTo(mPointHalf.x, mPointHalf.y, mCircleTwoTangentPointOne.x, mCircleTwoTangentPointOne.y);
        mPath.lineTo(mCircleTwoTangentPointTwo.x, mCircleTwoTangentPointTwo.y);
        mPath.quadTo(mPointHalf.x, mPointHalf.y, mCircleOneTangentPointTwo.x, mCircleOneTangentPointTwo.y);
    }

    private Handler mHanlder = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                invalidate();
            }
        }
    };

    private Runnable mBackRunable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "mBackRunable run");
            while (mIsBacking) {
                Log.d(TAG, "mIsBacking");
                if (canBack()) {
                    Log.d(TAG, "canBack");
                    Point back = getBackPoint();
                    Log.d(TAG, "back x " + back.x + " back y " + back.y);
                    int x = mCircleTwoCenter.x - back.x;
                    int y = mCircleTwoCenter.y - back.y;
                    x /= 20;
                    y /= 20;
                    for (int i = 0; i < 20; i++) {
                        if (i == 19) {
                            mCircleTwoCenter.set(back.x, back.y);
                        } else {
                            mCircleTwoCenter.set(mCircleTwoCenter.x - x, mCircleTwoCenter.y - y);
                        }
//                        Log.d(TAG, "mCircleTwoCenter " + mCircleTwoCenter.x + "  " + mCircleTwoCenter.y);
                        mHanlder.sendEmptyMessage(0);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.d(TAG, "InterruptedException");
                        }
                    }
                } else {
                    Log.d(TAG, "can not Back");
                    int x = mCircleTwoCenter.x - mCircleOneCenter.x;
                    int y = mCircleTwoCenter.y - mCircleOneCenter.y;
                    x /= 20;
                    y /= 20;
                    for (int i = 0; i < 20; i++) {
                        if (i == 19) {
                            mCircleTwoCenter.set(mCircleOneCenter.x, mCircleOneCenter.y);
                            mIsBacking = false;
                        } else {
                            mCircleTwoCenter.set(mCircleTwoCenter.x - x, mCircleTwoCenter.y - y);
                        }
                        Log.d(TAG, "mCircleTwoCenter " + mCircleTwoCenter.x + "  " + mCircleTwoCenter.y);
                        mHanlder.sendEmptyMessage(0);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.d(TAG, "InterruptedException");
                        }
                    }
                }
            }
        }
    };

    private Point getBackPoint() {
        if (canBack()) {
            return new Point(mCircleOneCenter.x - ((mCircleTwoCenter.x - mCircleOneCenter.x) / 3),
                    mCircleOneCenter.y - ((mCircleTwoCenter.y - mCircleOneCenter.y) / 3));
        }
        return mCircleOneCenter;
    }

    private boolean canBack() {
        Log.d(TAG, "length = " + getTwoPointsLength(mCircleTwoCenter, mCircleOneCenter) + " , mCircleOneRadius = " + mCircleOneRadius);
        return getTwoPointsLength(mCircleTwoCenter, mCircleOneCenter) > mCircleOneRadius * 3;
    }

    private int getTwoPointsLength(Point a, Point b) {
        return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

}
