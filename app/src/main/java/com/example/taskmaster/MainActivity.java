package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        TextView user= findViewById(R.id.showuser);
        user.setText(preferences.getString("username","please add user name from setting page"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);
        ImageView profile = (ImageView) findViewById(R.id.profile);
        Button setting = (Button) findViewById(R.id.setting);
        Button detail = (Button) findViewById(R.id.detail);



        profile.setBackgroundResource(R.drawable.profile);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (MainActivity.this,AddTask.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllTasks.class);
                startActivity(intent);

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TaskDetail.class);
                startActivity(intent);

            }
        });
    }

//    public void toAddTask( View view){
//        Intent intent = new Intent (this,AddTask.class);
//        startActivity(intent);
//
//    }
//     public void toAllTasks( View view){
//            Intent intent = new Intent (this,AllTasks.class);
//            startActivity(intent);
//
//        }


}