package com.example.taskmaster;

//import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
//import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
//import android.widget.TextView;
import android.widget.Toast;

//import com.amplifyframework.AmplifyException;
//import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.analytics.FirebaseAnalytics;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import java.util.List;
import android.Manifest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;


public class AddTask extends AppCompatActivity {

    private static final int CODE_REQUEST =55 ;
    private static final String TAG = "upload";
    private static final String TAG2 = "location" ;

    //    private TaskDao taskDao;
//    private AppDatabase db;
//    private List <Team> teamData= new ArrayList<>() ;
    private Team teamData ;
    private String key;
//    private List<Team> teams= new ArrayList<>();

//    public static final String TASK_HOLDER= "task_holder";

    private Handler handler;
    private FirebaseAnalytics mFirebaseAnalytics;
//    public static String TAG = "hogwarts";

    FusedLocationProviderClient locationProviderClient;

    Location currentLocation;
    String addressString;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this); // analytics review

        // location

        askForPermissionToUseLocation();
        configureLocationServices();
        askForLocation();


        // create handler

//        handler= new Handler(Looper.getMainLooper(), msg -> {
//            Log.i("database","the name: "+teamData.getName());
//            return true;
//        });
        handler= new Handler(Looper.getMainLooper(),

                msg -> {
                    Log.i("database","the name: "+teamData.getName());
                    return false;
                }
        );


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
        Spinner spinner= findViewById(R.id.Spinner01);
        ArrayAdapter<String > adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stareList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // create spinner for team
        String [] teamsList= {"TeamA","TeamB","TeamC"};
        Spinner spinnerTeam= findViewById(R.id.spinnerSetting);
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
//        TextView label = findViewById(R.id.label);
        EditText inputtitle= findViewById(R.id.editText);
        EditText inputbody= findViewById(R.id.editText3);

        Button uploadButt = findViewById(R.id.uploade);

        // add listener for the upload file button
        uploadButt.setOnClickListener(v -> getFileFromDevice());

        submit.setOnClickListener(v -> {

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


            Todo item= Todo.builder()
                    .title(titleContent).body(bodyContent).state(stateContent).team(teamData).fileKey(key).address(addressString).build();
            // -- save in the dynamoDB

            Amplify.API.mutate(ModelMutation.create(item)
                    ,success -> Log.i("submit", "saved item sucessfully")
                    , error -> Log.e("submit", "error in the saving to server",error)
            );
             Toast.makeText(AddTask.this, "Task Added",Toast.LENGTH_SHORT).show();

            // Add Analytics --Review- -
            Bundle addNewTaskEvent = new Bundle();
            addNewTaskEvent.putString("Tasktitle",titleContent );
            mFirebaseAnalytics.logEvent("add_task",addNewTaskEvent);

        });
    }

    private  void  getTeamDetailFromAPIByName(String teamNameData) {
        Amplify.API.query(
                ModelQuery.list(Team.class, Team.NAME.contains(teamNameData)),
                response -> {
                    for (Team teamDetail : response.getData()) {
//                        Log.i("teamDetail", teamDetail.toString());
                        teamData = teamDetail;
//                        Log.i("teamDetail", teamData.getName());
                        handler.sendEmptyMessage(1);
                    }
                        handler.sendEmptyMessage(1);
                    // analytics
                    // Add Analytics --Review- -
                    Bundle fetchTeam = new Bundle();
                    fetchTeam.putString("teamName",teamNameData );
                    mFirebaseAnalytics.logEvent("fetch_Team_Name",fetchTeam);
                },
                error -> Log.e("teamDetail", "Query failure", error)
        );
    }

//    private  void  getTeamDetailFromAPIByName(String teamNameData) {
//        Amplify.API.query(
//                ModelQuery.list(Team.class),
//                response -> {
//                    for (Team teamDetail : response.getData()) {
//                        Log.i("teamDetail", teamDetail.toString());
//                        teamData.add(teamDetail);
//                        handler.sendEmptyMessage(1);
//                    }
//                },
//                error -> Log.e("teamDetail", "Query failure", error)
//        );
//    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CODE_REQUEST && resultCode== RESULT_OK ){
            File uploadedFile = new File(getApplicationContext().getFilesDir(),"file");
            Log.i(TAG,"create stream");
//            key = new Date().toString()+" File";
        try {
            assert data != null;
            InputStream stream = getContentResolver().openInputStream(data.getData());
            FileUtils.copy(stream, new FileOutputStream(uploadedFile));


        }catch (Exception e){
            Log.e(TAG,"error in upolad the File "+ e.toString());

        }
         Amplify.Storage.uploadFile(

                 key= new Date().toString()+".jpg",
                 uploadedFile,
                 sucess -> Log.i(TAG,"the file saved to s3 successfully"),
                 error -> Log.e(TAG," error in store data at S3 "+ error)
         );

        }

    }

    private void getFileFromDevice(){

        // Analytics --- Review--
        Bundle uploadFile= new Bundle();
        uploadFile.putString("fileType","photo");
        mFirebaseAnalytics.logEvent("upload_File",uploadFile);

        // Intent
       Intent selectFile= new Intent(Intent.ACTION_GET_CONTENT);
       selectFile.setType("*/*");
       selectFile= Intent.createChooser(selectFile,"select File");

       startActivityForResult(selectFile,CODE_REQUEST);

       // activity
        Bundle uploadFileActivity = new Bundle();
        uploadFileActivity.putString("fileType","photo" );
        mFirebaseAnalytics.logEvent("upload_Photo_from_Devices",uploadFileActivity);


   }

   // location lab
   @RequiresApi(api = Build.VERSION_CODES.M)
   public void askForPermissionToUseLocation() {
       requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }


   public void askForLocation() {
       // TODO: geocoder
       LocationRequest locationRequest;
       LocationCallback locationCallback;

       locationRequest = LocationRequest.create();
       locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       locationRequest.setInterval(10000);


       locationCallback = new LocationCallback() {
           @Override
           public void onLocationResult(LocationResult locationResult) {
               if (locationResult == null) {
                   Log.i(TAG2, "result is null");

                   return;
               }
               currentLocation = locationResult.getLastLocation();
               Log.i(TAG2, currentLocation.toString());

               // TODO: GeoCoding the coordinates
               Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
               try {
                   List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 10);
                   Log.i(TAG2, addresses.get(0).toString());
                   addressString = addresses.get(0).getAddressLine(0);
                   Log.i(TAG2, addressString);


               } catch (IOException e) {
                   e.printStackTrace();
               }
           }


       };
       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           // TODO: Consider calling
           //    ActivityCompat#requestPermissions
           Toast t = new Toast(this);
           t.setText("You need to accept the permissions");
           t.setDuration(Toast.LENGTH_LONG);
           t.show();
           return;
       }
       locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
   }

   public void configureLocationServices(){
       locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
       // fuses the multiple location requests into one big one, gives you the most accurate that comes back
   }



}