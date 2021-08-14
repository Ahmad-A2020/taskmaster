package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    private List<Task> taskList;
     private TaskAdapter adapter;
    private  AppDatabase db;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        // data base config

      db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,AddTask.TASK_HOLDER)
              .allowMainThreadQueries().build();

      // pull data from the data base
        taskDao= db.taskDao();
        taskList= taskDao.findAll();


        RecyclerView recyclerView = findViewById(R.id.list);
//        taskList= new ArrayList<>();
//        taskList.add( new Task("work","meet with the team","new"));
//        taskList.add( new Task("house","remove garbage","assigned"));
//        taskList.add( new Task("work","create weekly plan ","in progress"));
//        taskList.add( new Task("house","wash desh","complete"));

        adapter= new TaskAdapter(taskList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getApplicationContext()

        );
//                this,
//                LinearLayoutManager.VERTICAL,
//                false
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

}