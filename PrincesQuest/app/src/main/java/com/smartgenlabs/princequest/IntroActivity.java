package com.smartgenlabs.princequest;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        viewPager=findViewById(R.id.viewPager);
        screenSlidePagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(screenSlidePagerAdapter);


    }
}
