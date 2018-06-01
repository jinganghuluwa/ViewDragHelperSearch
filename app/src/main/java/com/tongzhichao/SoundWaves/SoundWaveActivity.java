package com.tongzhichao.SoundWaves;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tongzhichao.example.R;

import java.io.File;

/**
 * Created by tongzhichao on 17-8-18.
 */

public class SoundWaveActivity extends AppCompatActivity {

    private int BASE = 600;
    private int SPACE = 20;// 间隔取样时间
    private MediaRecorder recorder;
    private String output_Path= Environment.getExternalStorageDirectory().getAbsolutePath()
            +File.separator+"luyin.3gp";
    //录音文件
    private File soundFile;

    private SoundWaveView soundWave;

    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            //根据mHandler发送what的大小决定话筒的图片是哪一张
            //说话声音越大,发送过来what值越大
            soundWave.setVoice(what);
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundwave);
        soundWave = findViewById(R.id.soundWave);
        int permissionCheck = checkSelfPermission(
                Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            initRecord();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001 && permissions[0].equals(Manifest.permission.RECORD_AUDIO) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initRecord();
        }
    }

    private void initRecord() {
        recorder = new MediaRecorder();
        soundFile=new File(output_Path);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//声音来源是话筒
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置解码方式
        recorder.setOutputFile(soundFile.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        recorder.start();
        updateMicStatus();
    }

    private int mDb = 0;
    private int mRdb = 0;

    private void updateMicStatus() {
        if (recorder != null) {
            int ratio = recorder.getMaxAmplitude() / BASE;
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (20 * Math.log10(ratio));
            if(db>30){
                db=30;
            }
            mRdb = db;
            //我对着手机说话声音最大的时候，db达到了35左右，
            voice();
            //所以除了2，为的就是对应14张图片
        }
    }

    private void voice(){
        if(mDb<mRdb){
            mDb++;
            mHandler.postDelayed(voiceRunnable, SPACE);
        }else if(mDb>mRdb){
            mDb--;
            mHandler.postDelayed(voiceRunnable, SPACE);
        }else {
            updateMicStatus();
        }
    }

    private Runnable voiceRunnable = new Runnable() {
        public void run() {
            mHandler.sendEmptyMessage(mDb);
            voice();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(voiceRunnable);
        if(recorder!=null){
            recorder.stop();
            recorder.release();
        }
    }
}
