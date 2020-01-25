package com.example.a32intacthealthcare.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.a32intacthealthcare.Firebase.GmailIntegration;
import com.example.a32intacthealthcare.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(){
            public void run(){
                super.run();
                try {
                    Thread.sleep(2000);
                    Intent intent=new Intent(Splash.this, GmailIntegration.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    Splash.super.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
