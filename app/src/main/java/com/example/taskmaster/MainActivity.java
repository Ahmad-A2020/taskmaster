package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
//import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
//import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
//import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.firebase.analytics.FirebaseAnalytics;


//import java.util.ArrayList;
//import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        TextView user= findViewById(R.id.showuser);
        user.setText(preferences.getString("username","please add user name from setting page"));
        // to show user teamName
        TextView team = findViewById(R.id.showteam);
        team.setText(( preferences.getString("teamName","please add team name from setting page")));

        //  to show userName
        if (Amplify.Auth.getCurrentUser() != null){
            currentUserName();
            TextView userHolder = findViewById(R.id.userPlace);
            userHolder.setText(Amplify.Auth.getCurrentUser().getUsername() );
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // analytics firebase

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        configureAmplify();

        // amplify

//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("plugin", "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e("plugin", "Could not initialize Amplify", e);
//        }

        // create teams and save it at dynamoDB ---- generate one time

//        Team team = Team.builder().name("TeamA").build();
//        Team team = Team.builder().name("TeamB").build();
//        Team team = Team.builder().name("TeamC").build();
//
//        Amplify.API.mutate(ModelMutation.create(team),
//        success -> Log.i("teamSave", "Saved Team to api : " + success.getData()),
//        error -> Log.e("teamSave", "Could not save Team to API/dynamodb", error));



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= findViewById(R.id.button);
        b2= findViewById(R.id.button2);
        ImageView profile = findViewById(R.id.profile);
        Button setting = findViewById(R.id.setting);
//        Button detail = (Button) findViewById(R.id.detail);



        profile.setBackgroundResource(R.drawable.profile);

        b1.setOnClickListener(v -> {

            Intent intent = new Intent (MainActivity.this,AddTask.class);

            // REVIEW
            Bundle params = new Bundle();
            params.putString("Taskname", "add Task");
            params.putString("full_text", "text");
            mFirebaseAnalytics.logEvent("ad_click", params);

            startActivity(intent);
        });

        b2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AllTasks.class);
            startActivity(intent);

        });

        setting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
        });

        // set logout button

        Button signOutbutt= findViewById(R.id.signout);
        signOutbutt.setOnClickListener(v -> signout());



//        detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,TaskDetail.class);
//                startActivity(intent);
//
//            }
//        });

//        RecyclerView recyclerView = findViewById(R.id.list);
//        taskList= new ArrayList<>();
//        taskList.add( new Task("work","meet with the team","new"));
//        taskList.add( new Task("house","remove garbage","assigned"));
//        taskList.add( new Task("work","create weekly plan ","in progress"));
//        taskList.add( new Task("house","wash desh","complete"));
//
//        adapter= new TaskAdapter(taskList);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
//                getApplicationContext()
//
//        );
//                this,
//                LinearLayoutManager.VERTICAL,
//                false
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());


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
    void currentUserName(){
        //    private TaskAdapter adapter;
        //    private List<Task> taskList;
        String user = Amplify.Auth.getCurrentUser().getUsername();
        Log.i("userName","CurrentUser: "+ user);
    }
    private void signout(){
        Amplify.Auth.signOut(
                () ->{
                    Intent ToLogIn = new Intent(MainActivity.this,SignInActivity.class);
                    startActivity(ToLogIn);
                }
                ,
                error -> Log.e("signout", "error in logging out: "+ error)
        );
    }

    private void configureAmplify() {
        // configure Amplify plugins
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("TAG", "Successfully initialized Amplify plugins");
        } catch (AmplifyException exception) {
            Log.e("TAG", "Failed to initialize Amplify plugins => " + exception.toString());
        }
    }




}