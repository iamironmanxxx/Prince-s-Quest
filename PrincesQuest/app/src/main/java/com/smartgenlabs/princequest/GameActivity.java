package com.smartgenlabs.princequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class GameActivity extends AppCompatActivity {

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        SharedPreference SP=new SharedPreference(this);
        level=SP.getInt(C.USER_LEVEL);

    }
}
