package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    private List<Task> taskList;
     private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        RecyclerView recyclerView = findViewById(R.id.list);
        taskList= new ArrayList<>();
        taskList.add( new Task("work","meet with the team","new"));
        taskList.add( new Task("house","remove garbage","assigned"));
        taskList.add( new Task("work","create weeklyplan ","in progress"));
        taskList.add( new Task("house","wash desh","complete"));

        adapter= new TaskAdapter(taskList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

}