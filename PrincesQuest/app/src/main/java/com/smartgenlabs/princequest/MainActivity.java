package com.smartgenlabs.princequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        SharedPreference SP=new SharedPreference(this);
        level=SP.getInt(C.USER_LEVEL);

        if(level==0) {

            //if Game is Just beginning : level 0
            //show story
            Intent i = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(i);
            finish();
        }
        else if(level==1||level==2) {
            Intent i = new Intent(MainActivity.this, GameActivity.class);
            startActivity(i);
            finish();

        }
        else
        {
            Intent i = new Intent(MainActivity.this, EndGameActivity.class);
            startActivity(i);
            finish();
        }
    }

    
}
