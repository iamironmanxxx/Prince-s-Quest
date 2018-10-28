package com.smartgenlabs.princequest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public void setText(String text) {
        this.text = text;
    }

    private String text;
    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_story, container, false);
        tv=v.findViewById(R.id.story);
        tv.setText(text);
        return v;
    }

}
