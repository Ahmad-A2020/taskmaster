package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {

    private static String log_tag="oh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Button submit = findViewById(R.id.button3);
        TextView label = findViewById(R.id.label);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label.setText("Submited!");
                Log.i(log_tag,"summited Successfully");


            }
        });
    }
}