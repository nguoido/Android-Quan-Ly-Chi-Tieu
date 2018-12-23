package com.vivhp.qlct.splash_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.vivhp.qlct.MainActivity;
import com.vivhp.qlct.R;

public class SplashScreen extends Activity {

    private ProgressBar proBar;
    private int SECOND_TIME_OUT = 3000;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        proBar = (ProgressBar) findViewById(R.id.proBar);

        //Set min / max ProgressBar
        proBar.setProgress(0);
        proBar.setMax(100);


        handler = new Handler();
        TimeOut(SECOND_TIME_OUT);
    }

    private int TimeOut(int time){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        }, time);
        return time;
    }
}
