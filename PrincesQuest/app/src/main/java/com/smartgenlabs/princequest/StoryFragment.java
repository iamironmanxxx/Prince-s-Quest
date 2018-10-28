package com.smartgenlabs.princequest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoryFragment extends Fragment {

    private TextView tv;
    private View v;
    private int level;

    public void setText(String text) {
        this.text = text;
    }

    private String text;
    public StoryFragment() {
        // Required empty public constructor
    }

    public TextView getText() {
        return tv;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_story, container, false);
        tv=v.findViewById(R.id.story);
        if(level==2)
        {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreference SP=new SharedPreference(getActivity());
                    SP.set(C.USER_LEVEL,1);
                    Intent i=new Intent(getActivity(),GameActivity.class);
                    getActivity().startActivity(i);
                    AppCompatActivity activity=(AppCompatActivity)getActivity();
                    activity.finish();
                }
            });
        }
        tv.setText(text);
        return v;
    }
    public void setPosition(int l)
    {
        level=l;
    }

}
