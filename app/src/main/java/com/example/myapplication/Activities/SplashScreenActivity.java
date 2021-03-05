package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            public void run()
            {
                try{
                    sleep(4000l);
                }
                catch (Exception e)
                {
                    Log.d("Aryan","There is error in playing animation in splashScreen");
                }
                finally {
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                }
            }
        };
        thread.start();
    }
}