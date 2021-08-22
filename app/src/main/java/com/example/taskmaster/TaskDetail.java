package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        String TextTitle= getIntent().getStringExtra("taskTitle");
        TextView titleholder = findViewById(R.id.textView5);
        titleholder.setText(TextTitle);


        String TextDesci= getIntent().getStringExtra("taskDescription");
        TextView descriptionholder = findViewById(R.id.textView10);
        descriptionholder.setText(TextDesci);

        String TextState= getIntent().getStringExtra("taskstate");
        TextView stateholder = findViewById(R.id.textView9);
        stateholder.setText(TextState);



    }
}