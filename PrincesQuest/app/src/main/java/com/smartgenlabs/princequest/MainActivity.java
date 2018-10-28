package com.smartgenlabs.princequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreference SP=new SharedPreference(this);
        level=SP.getInt(C.USER_LEVEL);

        if(level==0) {

            //if Game is Just beginning : level 0
            //show story
            Intent i = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(i);
            finish();
        }
    }

    
}
