package com.loan.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/*
 * 进度条学习连接:https://www.python100.com/html/95TO5M4X3WD8.html
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyProgressBar progressBar = findViewById(R.id.my_progress_bar);
//        progressBar.setProgress(30);
        progressBar.setOnProgressChangeListener(new MyProgressBar.OnProgressChangeListener() {
            @Override
            public void onProgressChange(int progress) {
                Toast.makeText(getApplicationContext(), "进度" + progress, Toast.LENGTH_SHORT).show();
            }
        });
    }
}