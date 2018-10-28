package com.smartgenlabs.princequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class EndGameActivity extends AppCompatActivity {
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        getSupportActionBar().hide();
        SharedPreference SP=new SharedPreference(this);
        SP.set(C.USER_LEVEL,0);
        SP.clearSP();


    }
}
