package com.example.taskmaster;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.os.Message;
import android.util.Log;

//import com.amplifyframework.AmplifyException;
//import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.firebase.analytics.FirebaseAnalytics;

//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    private final List<Todo> taskList= TasksDatamanager.getInstance().getTasks();
     private TaskAdapter adapter;
//    private  AppDatabase db;
//    private TaskDao taskDao;
    private Handler handler;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this); // analytics review


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

        handler= new Handler(Looper.getMainLooper(),

                msg -> {
                    taskListSetChanged();
                    return false;
                }
        );

        adapter= new TaskAdapter(taskList, new TaskAdapter.onTaskClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent detailsIntent = new Intent(AllTasks.this,TaskDetail.class);
                detailsIntent.putExtra("taskTitle",taskList.get(position).getTitle());
                detailsIntent.putExtra("taskDescription",taskList.get(position).getBody());
                detailsIntent.putExtra("taskstate",taskList.get(position).getState());
                detailsIntent.putExtra("linkFile",taskList.get(position).getFileKey());
                detailsIntent.putExtra("address",taskList.get(position).getAddress());
                startActivity(detailsIntent);

                // Add analytics -- Review--
                Bundle detail= new Bundle();
                detail.putString("detailOf",taskList.get(position).getTitle() );
                mFirebaseAnalytics.logEvent("click_detail",detail);

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
        Amplify.API.query(ModelQuery.list(Todo.class),
                response -> {
                    int i=0;

                    for (Todo item: response.getData()){
                        taskList.add(item);
                        Log.i("bring","item:"+item.getTitle());
                        Log.i("bring","itemlist:"+taskList.get(i).getTitle());
                        i++;
                    }
                    handler.sendEmptyMessage(1);



//                    taskListSetChanged();
                },
                error -> Log.e("bring", "onCreate: Failed to get expenses => " + error.toString())


        );

    }
//    private void getfileUrl(String key){
//        Amplify.Storage.getUrl(
//                key,
//                result ->{
//
//
//                Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
//                fileUrl= result.getUrl();
//                } ,
//                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
//        );
//
//    }

//    private void downloadFile(String Key){
//        Amplify.Storage.downloadFile(
//                Key,
//                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
//                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
//                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
//        );
//
//    }
    private void taskListSetChanged(){
        adapter.notifyDataSetChanged();
    }

}