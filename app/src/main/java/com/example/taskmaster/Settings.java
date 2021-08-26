package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
//import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // spiner definition

        // create spinner for team
        String [] teamsList= {"TeamA","TeamB","TeamC"};
        Spinner spinnerTeam= findViewById(R.id.spinnerSetting);
        ArrayAdapter<String > adapterTeams= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,teamsList);
        adapterTeams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(adapterTeams);
        // share preferance
        SharedPreferences preferances= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferanceEditor= preferances.edit();

        Button save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            EditText userName= findViewById(R.id.username);
            preferanceEditor.putString("username", userName.getText().toString());
            // for spinner
            String teamContent= spinnerTeam.getSelectedItem().toString();
            preferanceEditor.putString("teamName",teamContent);

            preferanceEditor.apply();


            Toast.makeText(Settings.this, "You saved your user name", Toast.LENGTH_LONG).show();

        });
    }
}