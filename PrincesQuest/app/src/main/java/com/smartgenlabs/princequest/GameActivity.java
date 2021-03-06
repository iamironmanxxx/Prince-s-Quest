package com.smartgenlabs.princequest;

import android.content.Intent;
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
import android.widget.Toast;

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

        final SharedPreference SP=new SharedPreference(this);
        level=SP.getInt(C.USER_LEVEL);

        linearLayout=findViewById(R.id.linearLayout);
        ans=findViewById(R.id.ans);
        submit=findViewById(R.id.submit);
        ques=findViewById(R.id.question);
        if(level==3)
        {
            Intent i=new Intent (GameActivity.this,EndGameActivity.class);
            startActivity(i);
            finish();
        }

        if(level==1)
        {
            inflateLayout(2,level);
            ques.setText(getString(R.string.q1));
        }
        else if(level==2)
        {
            inflateLayout(2,level);
            ques.setText(getString(R.string.q2));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ansStr=ans.getText().toString().trim();
                String correctstr;
                if(level==1) {
                    correctstr = getString(R.string.ans1);
                }
                else
                {
                    correctstr=getString(R.string.ans2);
                }
                if(correctstr.equalsIgnoreCase(ansStr))
                {
                    SP.set(C.USER_LEVEL,++level);
                    Toast.makeText(GameActivity.this, "Right Answer!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(GameActivity.this,GameActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    SP.set(C.USER_LEVEL,0);
                    Toast.makeText(GameActivity.this, "You Lost!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(GameActivity.this,StartupActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        });

    }

    private void inflateLayout(int people, final int level) {
        linearLayout.removeAllViews();
        String name[];
        if(level==1)
        {
            name=getResources().getStringArray(R.array.name1);
        }
        else
        {
            name=getResources().getStringArray(R.array.name2);
        }
        for(int i=0;i<people;i++)
        {
            final View view= LayoutInflater.from(this).inflate(R.layout.knight,null);
            TextView tv=view.findViewById(R.id.text);
            tv.setText(name[i]);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View btn) {
                    String ar[];
                    if(level==1)
                    {
                       ar=getResources().getStringArray(R.array.level1);
                    }
                    else
                    {
                        ar= getResources().getStringArray(R.array.level2);
                    }
                    AlertDialog dialog=new AlertDialog.Builder(GameActivity.this).create();
                    View v=LayoutInflater.from(GameActivity.this).inflate(R.layout.dialog,null);
                    dialog.setView(v);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.show();
                    TextView message=v.findViewById(R.id.knightText);
                    message.setText(ar[finalI]);
                }
            });
            linearLayout.addView(view);
        }
    }

}
