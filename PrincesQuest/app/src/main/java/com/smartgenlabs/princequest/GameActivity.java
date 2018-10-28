package com.smartgenlabs.princequest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Window;

public class GameActivity extends AppCompatActivity {

    private int level;
    private LinearLayout linearLayout;
    private EditText ans;
    private TextView ques;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        SharedPreference SP=new SharedPreference(this);
        level=SP.getInt(C.USER_LEVEL);

        linearLayout=findViewById(R.id.linearLayout);
        ans=findViewById(R.id.ans);
        submit=findViewById(R.id.submit);
        ques=findViewById(R.id.question);

        if(level==1)
        {
            inflateLayout(2,1);
        }

    }

    private void inflateLayout(int people, final int level) {
        String name[];
        if(level==1)
        {
            name=getResources().getStringArray(R.array.name1);
        }
        else
        {
            name=new String[2];
        }
        for(int i=0;i<people;i++)
        {
            View view= LayoutInflater.from(this).inflate(R.layout.knight,null);
            TextView tv=view.findViewById(R.id.text);
            tv.setText(name[i]);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ar[];
                    if(level==1)
                    {
                       ar=getResources().getStringArray(R.array.level1);
                    }
                    else
                    {
                        ar= new String[3];
                    }
                    AlertDialog dialog=new AlertDialog.Builder(GameActivity.this).create();
                    View v=LayoutInflater.from(GameActivity.this).inflate(R.layout.dialog,null);
                    dialog.setView(v);
                    TextView message=v.findViewById(R.id.knightText);
                    message.setText(ar[finalI]);

                }
            });
        }
    }

}
