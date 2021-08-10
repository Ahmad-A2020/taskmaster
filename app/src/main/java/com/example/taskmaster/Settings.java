package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferances= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferanceEditor= preferances.edit();

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName= findViewById(R.id.username);
                preferanceEditor.putString("username", userName.getText().toString());
                preferanceEditor.apply();

                Toast toast = Toast.makeText(Settings.this, "You saved your user name", Toast.LENGTH_LONG);

            }
        });
    }
}