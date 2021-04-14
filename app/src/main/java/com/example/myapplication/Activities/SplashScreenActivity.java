package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                finish();
            }
        },4000l);
//        Thread thread = new Thread(){
//            public void run()
//            {
//                try{
//                    sleep(4000l);
//                }
//                catch (Exception e)
//                {
//                    Log.d("Aryan","There is error in playing animation in splashScreen");
//                }
//                finally {
//                    finish();
//                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
//                }
//            }
//        };
//        thread.start();
    }
}