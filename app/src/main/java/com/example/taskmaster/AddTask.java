package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Todo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTask extends AppCompatActivity {

    private static final int CODE_REQUEST =55 ;
    private static final String TAG = "upload";
    private static String log_tag="oh";

    private TaskDao taskDao;
    private AppDatabase db;
    private Team teamData= null ;
    private String key;
    private List<Team> teams= new ArrayList<>();

    public static final String TASK_HOLDER= "task_holder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // create teams
//        teams = new ArrayList<>();

        // amplify configuration -- addplugin

//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("plugin", "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e("plugin", "Could not initialize Amplify", e);
//        }
        // create spinner for state
        String [] stareList= {"new","assigned","in progress","complete"};
        Spinner spinner= (Spinner)  findViewById(R.id.Spinner01);
        ArrayAdapter<String > adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stareList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // create spinner for team
        String [] teamsList= {"TeamA","TeamB","TeamC"};
        Spinner spinnerTeam= (Spinner)  findViewById(R.id.spinnerSetting);
        ArrayAdapter<String > adapterTeams= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,teamsList);
        adapterTeams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(adapterTeams);


        // data base
//        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,TASK_HOLDER)
//                .allowMainThreadQueries().build();
//        taskDao= db.taskDao();

        // click Listener

        // find by Id

        Button submit = findViewById(R.id.button3);
        TextView label = findViewById(R.id.label);
        EditText inputtitle= findViewById(R.id.editText);
        EditText inputbody= findViewById(R.id.editText3);

        Button uploadButt = findViewById(R.id.uploade);

        // add listener for the upload file button
        uploadButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromDevice();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleContent = inputtitle.getText().toString();
                String bodyContent = inputbody.getText().toString();
                String stateContent = spinner.getSelectedItem().toString();
                String teamContent= spinnerTeam.getSelectedItem().toString();

                // save in the Database ---- Room-----
//                Task task = new Task(title,body,"completed");
//                taskDao.insertOne(task);
//
//                Toast.makeText(AddTask.this, "Task Added",Toast.LENGTH_SHORT).show();
//
//                label.setText("Submited!");
//                Log.i(log_tag,"summited Successfully");


                // --find the team from the dynamoDB--
                Log.i("teamspinner",teamContent);

                getTeamDetailFromAPIByName(teamContent);

                teamData = Team.builder().name(teamContent).build();
                Log.i("teamName",teamData.getName());
                Log.i("teamsData",String.valueOf(teams.size()));


                Todo item= Todo.builder()
                        .title(titleContent).body(bodyContent).state(stateContent).team(teamData).fileKey(key).build();
                // -- save in the dynamoDB

                Amplify.API.mutate(ModelMutation.create(item)
                        ,success -> Log.i("submit", "saved item sucessfully")
                        , error -> Log.e("submit", "error in the saving to server",error)
                );
                 Toast.makeText(AddTask.this, "Task Added",Toast.LENGTH_SHORT).show();



            }
        });
    }

    private  void  getTeamDetailFromAPIByName(String teamNameData) {
        Amplify.API.query(
                ModelQuery.list(Team.class, Team.NAME.contains(teamNameData)),
                response -> {
                    for (Team teamDetail : response.getData()) {
                        Log.i("teamDetail", teamDetail.toString());
                        teamData = teamDetail;
                    }
                },
                error -> Log.e("teamDetail", "Query failure", error)
        );
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CODE_REQUEST && resultCode== RESULT_OK ){
            File uploadedFile = new File(getApplicationContext().getFilesDir(),"file");
            Log.i(TAG,"create stream");
//            key = new Date().toString()+" File";
        try {
            InputStream stream = getContentResolver().openInputStream(data.getData());
            FileUtils.copy(stream, new FileOutputStream(uploadedFile));


        }catch (Exception e){
            Log.e(TAG,"error in upolad the File "+ e.toString());

        }
         Amplify.Storage.uploadFile(

                 key= new Date().toString()+".jpg",
                 uploadedFile,
                 sucess ->{
                     Log.i(TAG,"the file saved to s3 successfully");
                 },
                 error ->{
                     Log.e(TAG," error in store data at S3 "+ error);
                 }
         );

        }

    }

    private void getFileFromDevice(){

       Intent selectFile= new Intent(Intent.ACTION_GET_CONTENT);
       selectFile.setType("*/*");
       selectFile= Intent.createChooser(selectFile,"select File");

       startActivityForResult(selectFile,CODE_REQUEST);

   }


}