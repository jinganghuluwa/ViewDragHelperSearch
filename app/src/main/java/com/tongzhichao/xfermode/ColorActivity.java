package com.tongzhichao.xfermode;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

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
        if (v.getId()==R.id.black){
            colorView.setColor(Color.BLACK);
        }else if (v.getId()==R.id.gray){
            colorView.setColor(Color.GRAY);
        }else if (v.getId()==R.id.red){
            colorView.setColor(Color.RED);
        }else if (v.getId()==R.id.green){
            colorView.setColor(Color.GREEN);
        }else if (v.getId()==R.id.blue){
            colorView.setColor(Color.BLUE);
        }else if (v.getId()==R.id.yellow){
            colorView.setColor(Color.YELLOW);
        }
    }
}
