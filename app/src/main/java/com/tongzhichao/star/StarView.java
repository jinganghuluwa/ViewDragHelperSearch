package com.tongzhichao.star;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StarView extends View {
    private double allPer = 1.0;

    private static final int RANDOM_LEVEL1_SPACE = 30;
    private static final int RANDOM_LEVEL1_RANDOM = 45;
    private static final int RANDOM_LEVEL3_SPACE = 15;
    //点点水平间隔
    private static final int POINT1_HORIZONTAL_SPACE = 8;
    private static final int POINT2_HORIZONTAL_SPACE = 10;
    private static final int POINT3_HORIZONTAL_SPACE = 5;
    private static final int POINT5_HORIZONTAL_SPACE = 2;
    //点点大小
    private static final int POINT_LENGTH = 2;
    //1组数量
    private static final int GROUP1_COUNT = 6;
    //1组步长
    private static final double GROUP1_STEP = 0.2;
    //2组数量
    private static final int GROUP2_COUNT = 20;
    private static final int GROUP3_COUNT = 14;
    private static final int GROUP5_COUNT = 30;
    private static final double GROUP2_STEP = 0.1;
    private static final double GROUP3_STEP = 0.15;

    private int width;
    private int height;

    private float density;

    private Paint paint1;

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;
        paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setStrokeWidth(POINT_LENGTH);
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
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawRandom(canvas);
        drawLineGroup1(canvas);
        drawLineGroup2(canvas);
        drawLineGroup3(canvas);
        drawLineGroup4(canvas);
        drawLineGroup5(canvas);

    }

    private void drawRandom(Canvas canvas) {
        paint1.setColor(Color.WHITE);
        for (int i = 0; i < width; i += RANDOM_LEVEL1_SPACE) {
            for (int j = 0; j < height; j += RANDOM_LEVEL1_SPACE) {
                canvas.drawPoint(random(i, RANDOM_LEVEL1_RANDOM), random(j, RANDOM_LEVEL1_RANDOM), paint1);
            }
        }
        //int x = (int) (Math.random() * width);
        //int y = (int) (Math.random() * height);
        //for (int i = 0; i < Math.min(2 * x, width); i += RANDOM_LEVEL2_SPACE) {
        //    for (int j = 0; j < Math.min(2 * y, width); j += RANDOM_LEVEL2_SPACE) {
        //        canvas.drawPoint(random(i, RANDOM_LEVEL2_SPACE), random(j, RANDOM_LEVEL2_SPACE), paint1);
        //    }
        //}
        //x = (int) (Math.random() * width);
        //y = (int) (Math.random() * height);
        //for (int i = x; i < Math.min(2 * x, width); i += RANDOM_LEVEL2_SPACE) {
        //    for (int j = y; j < Math.min(2 * y, width); j += RANDOM_LEVEL2_SPACE) {
        //        canvas.drawPoint(random(i, RANDOM_LEVEL2_SPACE), random(j, RANDOM_LEVEL2_SPACE), paint1);
        //    }
        //}
        //x = (int) (Math.random() * width);
        //y = (int) (Math.random() * height);
        //for (int i = x; i < Math.min(2 * x, width); i += RANDOM_LEVEL3_SPACE) {
        //    for (int j = y; j < Math.min(2 * y, width); j += RANDOM_LEVEL3_SPACE) {
        //        canvas.drawPoint(random(i, RANDOM_LEVEL3_SPACE), random(j, RANDOM_LEVEL3_SPACE), paint1);
        //    }
        //}
    }

    private void drawLineGroup1(Canvas canvas) {
        float color;
        for (int i = 0; i < GROUP1_COUNT; i++) {
            if (i < 3) {
                color = (float) (0.3 * (i + 1));
                paint1.setColor(getColor(color));
            } else {
                color = (float) (1.8 - 0.3 * i);
                paint1.setColor(getColor(color));
            }
            for (int j = width / 5; j < width - 10; j += POINT1_HORIZONTAL_SPACE + POINT_LENGTH) {
                //if (j < (width / 3) + 100) {
                //    float a = (float) (j - width / 3) / 100f;
                //    paint1.setColor(getColor(color * a));
                //} else if (j > width - 110) {
                //    float a = (float) (width - 10 - j) / 100f;
                //    paint1.setColor(getColor(color * a));
                //}
                canvas.drawPoint(random(j), random(translateY(((line1(j) * (1 + GROUP1_STEP * i)) * height / 2)) + 100), paint1);
            }
        }

    }

    private void drawLineGroup2(Canvas canvas) {
        float color = 1;
        paint1.setColor(getColor(color));
        for (int i = 0; i < GROUP2_COUNT; i++) {
            //if (i <= 10) {
            //    color = (float) (0.1 * i);
            //    paint1.setColor(getColor(color));
            //} else {
            //    color = (float) (2 - 0.1 * i);
            //    paint1.setColor(getColor(color));
            //}
            //Log.d("tzc","i=:"+i+" color:"+color);
            for (int j = 0; j < width; j += POINT2_HORIZONTAL_SPACE + POINT_LENGTH) {
                //if (j < 300) {
                //    //Log.d("tzc","j:"+j+" value:"+((float) j)  / 300f);
                //    paint1.setColor(getColor(color * ((float) j) / 300f));
                //} else if (j > width - 300) {
                //    //Log.d("tzc","j:"+j+" value:"+(float) (width-j) / 300f);
                //    paint1.setColor(getColor(color * (float) (width - j) / 300f));
                //}
                canvas.drawPoint(random((float) j,15), random(  (translateY((line2(j) * (1 - GROUP2_STEP * i) * height / 3))),10), paint1);
            }
        }
    }

    private void drawLineGroup3(Canvas canvas) {
        float color = 1;
        for (int i = 0; i < GROUP3_COUNT; i++) {
            color = (float) (0.05 * i );
            //Log.d("tzc","i=:"+i+" color:"+color);
            paint1.setColor(getColor(1 - color));
            for (int j = 0; j <width; j += POINT3_HORIZONTAL_SPACE + POINT_LENGTH) {
                //if (j < 100) {
                //    //Log.d("tzc","j:"+j+" value:"+((float) j)  / 300f);
                //    paint1.setColor(getColor(color * ((float) j) / 100f));
                //} else if (j > width / 2 - 100) {
                //    //Log.d("tzc","j:"+j+" value:"+(float) (width-j) / 300f);
                //    paint1.setColor(getColor(color * (float) (width / 2 - j) / 100f));
                //}
                canvas.drawPoint(random((float) ((float) j-100*allPer)), random(translateY(((line3(j) + GROUP3_STEP * i) * height / 3)) - 150), paint1);
            }
        }
    }

    private void drawLineGroup4(Canvas canvas) {
        float color = 1;
        for (int i = 0; i < GROUP3_COUNT; i++) {
            color = (float) (0.05 * i );
            //Log.d("tzc","i=:"+i+" color:"+color);
            paint1.setColor(getColor(1 - color));
            for (int j = 0; j <  width ; j += POINT3_HORIZONTAL_SPACE + POINT_LENGTH) {
                //if (j < width / 6 + 100) {
                //    //Log.d("tzc","j:"+j+" value:"+((float) j)  / 300f);
                //    paint1.setColor(getColor(color * ((float) j-((float) width / 6)) / 100f));
                //} else if (j > 3 * width / 5 - 100) {
                //    //Log.d("tzc","j:"+j+" value:"+(float) (width-j) / 300f);
                //    paint1.setColor(getColor(color * (float) (3 * width / 5 - j) / 100f));
                //}
                canvas.drawPoint(random((float) ((float) j+100*allPer)), random(translateY(((line4(j) + GROUP3_STEP * i) * height / 3)) - 120), paint1);
            }
        }
    }

    private void drawLineGroup5(Canvas canvas) {
        float color = 1;
        for (int i = 0; i < GROUP5_COUNT; i++) {
            color = (float) (0.1 * i + 0.2);
            Log.d("tzc","i=:"+i+" color:"+color);
            paint1.setColor(getColor(1 ));
            for (int j = 0 ; j <  width ; j +=  1) {
                j+=0.05*j;
                //if (j < width / 6 + 100) {
                //    //Log.d("tzc","j:"+j+" value:"+((float) j)  / 300f);
                //    paint1.setColor(getColor(color * ((float) j-((float) width / 6)) / 100f));
                //} else if (j > 3 * width / 5 - 100) {
                //    //Log.d("tzc","j:"+j+" value:"+(float) (width-j) / 300f);
                //    paint1.setColor(getColor(color * (float) (3 * width / 5 - j) / 100f));
                //}
                canvas.drawPoint(random((float) j+i*10), random(translateY(-line5(j)*Math.sin((double) i*Math.PI*3 /GROUP5_COUNT) ) -i+100,2), paint1);
            }
        }
    }

    private double line1(double x) {
        x = getsinX(x);
        return -Math.sin((x + 0.8) * 3) * Math.sin(0.1 * (x + 0.8)) - (0.8 + x) / 5;
    }

    private float random(float value) {
        return (float) (value + Math.random() * 2);
    }

    private float random(float value, int max) {
        return (float) (value + Math.random() * max);
    }

    private double line2(double x) {
        x = getsinX(x);
        return -Math.sin(x * 0.8 + 0.3) * 1.8;
    }

    private double line3(double x) {
        x = getsinX(x);
        return -Math.sin(2 * x + 0.8) + 0.2 * x;
    }

    private double line4(double x) {
        x = getsinX(x);
        return -Math.sin(2 * x + 3.5) + 0.2 * x;
    }

    private double line5(double x) {
        return width*30/(x);
    }


    private double getsinX(double x) {
        return Math.PI * x / width;
    }

    private float translateY(double y) {
        return (float) (y * allPer + (double) height / 2);
    }

    private int getColor(float per) {
        return ((int) (per * 255.0f + 0.5f) << 24) | 16777215;
    }

    public void setPer(double per) {
        allPer = per;
        setAlpha((float) (1 - (1 - per) * 2));
        postInvalidate();
    }
}
