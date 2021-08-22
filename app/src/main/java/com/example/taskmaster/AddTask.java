package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class AddTask extends AppCompatActivity {

    private static String log_tag="oh";

    private TaskDao taskDao;
    private AppDatabase db;
    private Team teamData= null ;

    public static final String TASK_HOLDER= "task_holder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
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

                getTeamDetailFromAPIByName("TeamA");

                Todo item= Todo.builder()
                        .title(titleContent).body(bodyContent).state(stateContent).team(teamData).build();
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

    private void configurateAmplify(){


    }


}