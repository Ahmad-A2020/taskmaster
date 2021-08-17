package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    private static String log_tag="oh";

    private TaskDao taskDao;
    private AppDatabase db;

    public static final String TASK_HOLDER= "task_holder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        // create spinner
        String [] stareList= {"new","assigned","in progress","complete"};
        Spinner spinner= (Spinner)  findViewById(R.id.Spinner01);
        ArrayAdapter<String > adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stareList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // data base
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,TASK_HOLDER)
                .allowMainThreadQueries().build();
        taskDao= db.taskDao();

        // click Listener

        // find by Id

        Button submit = findViewById(R.id.button3);
        TextView label = findViewById(R.id.label);
        EditText inputtitle= findViewById(R.id.editText);
        EditText inputbody= findViewById(R.id.editText3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String title = inputtitle.getText().toString();
                String body = inputbody.getText().toString();
                String state = spinner.getSelectedItem().toString();

                // save in the Database
                Task task = new Task(title,body,"completed");
                taskDao.insertOne(task);

                Toast.makeText(AddTask.this, "Task Added",Toast.LENGTH_SHORT).show();

                label.setText("Submited!");
                Log.i(log_tag,"summited Successfully");


            }
        });
    }
}