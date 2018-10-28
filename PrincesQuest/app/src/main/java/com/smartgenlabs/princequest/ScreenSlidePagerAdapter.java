package com.smartgenlabs.princequest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    public ScreenSlidePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        StoryFragment sf=new StoryFragment();
        switch (position)
        {
            case 0: sf.setText(context.getString(R.string.story1));
                    break;
            case 1: sf.setText(context.getString(R.string.story2));
                    break;
            case 2: sf.setText(context.getString(R.string.story3));
                    sf.getText().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreference SP=new SharedPreference(context);
                            SP.set(C.USER_LEVEL,1);
                            Intent i=new Intent(context,GameActivity.class);
                            context.startActivity(i);
                            AppCompatActivity activity=(AppCompatActivity)context;
                            activity.finish();
                        }
                    });
                    break;
        }

        return sf;
    }
}
