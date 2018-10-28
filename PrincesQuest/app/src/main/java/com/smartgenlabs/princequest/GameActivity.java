package com.smartgenlabs.princequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreference SP=new SharedPreference(this);
        level=SP.getInt(C.USER_LEVEL);

    }
}
