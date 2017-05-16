package com.tongzhichao.hvac.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-5-16.
 */

public class TemperatureControlView extends RelativeLayout {
    private TemperatureBackgroundView mBackground;
    private Button mUp, mDown;
    private TextView mValueText;
    private boolean isLeft;
    private float mTemperatureValue = 23;
    private OnControlChangeListener mOnControlChangeListener;

    public TemperatureControlView(Context context) {
        this(context, null);
    }

    public TemperatureControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().
                obtainStyledAttributes(attrs, R.styleable.TemperatureControlView, 0, 0);
        isLeft = typedArray.getBoolean(R.styleable.TemperatureControlView_control_left, true);
        if (isLeft) {
            LayoutInflater.from(context).inflate(R.layout.left_temperature_control_layout, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.right_temperature_control_layout, this);
        }


    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.temperature_control_up:
                    up();
                    break;
                default:
                    down();
                    break;
            }
        }
    };

    private TemperatureBackgroundView.OnSlideListener mSlideListener = new TemperatureBackgroundView.OnSlideListener() {
        @Override
        public void onSlideUp() {
            up();
        }

        @Override
        public void onSlideDown() {
            down();
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackground = (TemperatureBackgroundView) findViewById(R.id.temperature_background);
        mUp = (Button) findViewById(R.id.temperature_control_up);
        mDown = (Button) findViewById(R.id.temperature_control_down);
        mValueText = (TextView) findViewById(R.id.temperature_value);
        mUp.setOnClickListener(mClickListener);
        mDown.setOnClickListener(mClickListener);
        mBackground.setOnSlideListener(mSlideListener);
        setTemerratureValue();
    }

    public interface OnControlChangeListener {
        void onUp(TemperatureControlView temperatureControlView);

        void onDown(TemperatureControlView temperatureControlView);
    }

    private void up() {
        mTemperatureValue++;
        setTemerratureValue();
        if (mOnControlChangeListener != null) {
            mOnControlChangeListener.onUp(this);
        }
    }

    private void down() {
        mTemperatureValue--;
        setTemerratureValue();
        if (mOnControlChangeListener != null) {
            mOnControlChangeListener.onDown(this);
        }
    }

    public void setOnControlChangeListener(OnControlChangeListener onControlChangeListener) {
        mOnControlChangeListener = onControlChangeListener;
    }

    public void setTemerratureValue(float value) {
        mTemperatureValue = value;
        mValueText.setText(String.valueOf(mTemperatureValue));
    }

    public void setTemerratureValue() {
        mValueText.setText(String.valueOf(mTemperatureValue));
    }
}
