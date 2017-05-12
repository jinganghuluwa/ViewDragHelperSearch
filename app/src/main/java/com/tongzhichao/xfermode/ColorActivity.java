package com.tongzhichao.xfermode;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.tongzhichao.example.R;

public class ColorActivity extends AppCompatActivity implements View.OnClickListener {
    ColorView colorView;
    View black,gray,red,green,blue,yellow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        colorView = (ColorView) findViewById(R.id.testView);
        black=findViewById(R.id.black);
        gray=findViewById(R.id.gray);
        red=findViewById(R.id.red);
        green=findViewById(R.id.green);
        blue=findViewById(R.id.blue);
        yellow=findViewById(R.id.yellow);
        black.setOnClickListener(this);
        gray.setOnClickListener(this);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);

        black.setBackgroundColor(Color.BLACK);
        gray.setBackgroundColor(Color.GRAY);
        red.setBackgroundColor(Color.RED);
        green.setBackgroundColor(Color.GREEN);
        blue.setBackgroundColor(Color.BLUE);
        yellow.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.black:
                colorView.setColor(Color.BLACK);
                break;
            case R.id.gray:
                colorView.setColor(Color.GRAY);
                break;
            case R.id.red:
                colorView.setColor(Color.RED);
                break;
            case R.id.green:
                colorView.setColor(Color.GREEN);
                break;
            case R.id.blue:
                colorView.setColor(Color.BLUE);
                break;
            case R.id.yellow:
                colorView.setColor(Color.YELLOW);
                break;


        }
    }
}
