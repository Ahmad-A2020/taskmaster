package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    private List<Todo> taskList= TasksDatamanager.getInstance().getTasks();
     private TaskAdapter adapter;
    private  AppDatabase db;
    private TaskDao taskDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        getDataFromApi();

        // data base config

//      db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,AddTask.TASK_HOLDER)
//              .allowMainThreadQueries().build();

      // pull data from the local database ----Room---
//        taskDao= db.taskDao();
//        taskList= taskDao.findAll();

        // git the data from the dynamoDB



//        Log.i("itemlist","value of zero  index"+taskList.get(0).getTitle());


        RecyclerView recyclerView = findViewById(R.id.list);
//         List<Task> taskList2= new ArrayList<>();
//        taskList2.add( new Task("work","meet with the team","new"));
//        taskList2.add( new Task("house","remove garbage","assigned"));
//        taskList2.add( new Task("work","create weekly plan ","in progress"));
//        taskList2.add( new Task("house","wash desh","complete"));

        adapter= new TaskAdapter(taskList, new TaskAdapter.onTaskClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent detailsIntent = new Intent(AllTasks.this,TaskDetail.class);
                detailsIntent.putExtra("taskTitle",taskList.get(position).getTitle());
                detailsIntent.putExtra("taskDescription",taskList.get(position).getTitle());
                detailsIntent.putExtra("taskstate",taskList.get(position).getTitle());
                startActivity(detailsIntent);

            }

            @Override
            public void onDeleteItem(int position) {
                taskList.remove( position);
                adapter.notifyDataSetChanged();
            }
        });

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
    private void getDataFromApi(){
        List<Todo> data= new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Todo.class),
                response -> {
                    int i=0;

                    for (Todo item: response.getData()){
                        taskList.add(item);
                        Log.i("bring","item:"+item.getTitle());
                        Log.i("bring","itemlist:"+taskList.get(i).getTitle());
                        i++;
                    }



//                    taskListSetChanged();
                },
                error -> Log.e("bring", "onCreate: Failed to get expenses => " + error.toString())


        );

    }

    private void taskListSetChanged(){
        adapter.notifyDataSetChanged();
    }

}