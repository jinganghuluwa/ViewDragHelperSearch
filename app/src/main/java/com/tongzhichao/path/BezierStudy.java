package com.tongzhichao.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BezierStudy extends View {

    private Paint mPaint;
    private Path mPath;
    private int width;
    private int height;

    public BezierStudy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
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
        one = new Point(100, height - 200);
        two = new Point(400, height - 1000);
        three = new Point(700, height - 600);
        four = new Point(1000, height - 1600);

        onehalf = new Point((one.x + two.x) / 2, (one.y + two.y) / 2);
        twohalf = new Point((two.x + three.x) / 2, (two.y + three.y) / 2);
        threehalf = new Point((three.x + four.x) / 2, (three.y + four.y) / 2);

        halflinehalfone = new Point((onehalf.x + twohalf.x) / 2, (onehalf.y + twohalf.y) / 2);
        halflinehalftwo = new Point((twohalf.x + threehalf.x) / 2, (twohalf.y + threehalf.y) / 2);
        yueshuone = new Point(onehalf.x + two.x - halflinehalfone.x, onehalf.y + two.y - halflinehalfone.y);

        twoyueshuone = new Point(two.x + twohalf.x - halflinehalfone.x, two.y + twohalf.y - halflinehalfone.y);
        twoyueshutwo = new Point(twohalf.x + three.x - halflinehalftwo.x, twohalf.y + three.y - halflinehalftwo.y);
    }


    Point one;

    Point two;

    Point three;

    Point four;

    Point onehalf;
    Point twohalf;
    Point threehalf;


    Point halflinehalfone;
    Point halflinehalftwo;

    Point yueshuone;
    Point twoyueshuone;
    Point twoyueshutwo;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        setPath();
//        canvas.drawPath(mPath, mPaint);


        mPaint.setStyle(Paint.Style.FILL);
        //baseline
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(one.x, one.y, two.x, two.y, mPaint);
        canvas.drawLine(two.x, two.y, three.x, three.y, mPaint);
        canvas.drawLine(three.x, three.y, four.x, four.y, mPaint);

        //basepoint
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(three.x, three.y, 10, mPaint);
        canvas.drawCircle(four.x, four.y, 10, mPaint);
        canvas.drawCircle(one.x, one.y, 10, mPaint);
        canvas.drawCircle(two.x, two.y, 10, mPaint);

        //中点连线
        mPaint.setColor(Color.RED);
        canvas.drawLine(onehalf.x, onehalf.y, twohalf.x, twohalf.y, mPaint);
        canvas.drawLine(twohalf.x, twohalf.y, threehalf.x, threehalf.y, mPaint);

        //baseline中点
        canvas.drawCircle(onehalf.x, onehalf.y, 6, mPaint);
        canvas.drawCircle(twohalf.x, twohalf.y, 6, mPaint);
        canvas.drawCircle(threehalf.x, threehalf.y, 6, mPaint);

        //中点连线中点
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(halflinehalfone.x, halflinehalfone.y, 4, mPaint);
        canvas.drawCircle(halflinehalftwo.x, halflinehalftwo.y, 4, mPaint);

        //约束点
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(yueshuone.x, yueshuone.y, 8, mPaint);

        canvas.drawCircle(twoyueshuone.x, twoyueshuone.y, 8, mPaint);
        canvas.drawCircle(twoyueshutwo.x, twoyueshutwo.y, 8, mPaint);
        //矩形
//        canvas.drawLine(onehalf.x, onehalf.y, yueshuone.x, yueshuone.y, mPaint);
//        canvas.drawLine(yueshuone.x, yueshuone.y, two.x, two.y, mPaint);
//        canvas.drawLine(two.x, two.y, halflinehalfone.x, halflinehalfone.y, mPaint);

//        canvas.drawLine(halflinehalftwo.x, halflinehalftwo.y, three.x, three.y, mPaint);
//        canvas.drawLine(three.x, three.y, twoyueshutwo.x, twoyueshutwo.y, mPaint);
//        canvas.drawLine(twoyueshutwo.x, twoyueshutwo.y, twohalf.x, twohalf.y, mPaint);


        //画曲线
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(one.x, one.y);
        path.quadTo(yueshuone.x, yueshuone.y, two.x, two.y);
        canvas.drawPath(path, mPaint);

        path.moveTo(two.x, two.y);
        path.cubicTo(twoyueshuone.x, twoyueshuone.y, twoyueshutwo.x, twoyueshutwo.y, three.x, three.y);
        canvas.drawPath(path, mPaint);
    }

    private void setPath() {
//        mPath.moveTo(start.x, start.y);
//        mPath.cubicTo(one.x, one.y, two.x,two.y,end.x, end.y);
    }
}
