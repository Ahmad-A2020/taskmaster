package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = "verify" ;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        EditText code= findViewById(R.id.code);
        Button verify = findViewById(R.id.verify);

        Intent intent= getIntent();
        username= intent.getExtras().getString("userName","");
        password= intent.getExtras().getString("password","");

        verify.setOnClickListener(v -> verfication(username,code.getText().toString()));


    }

    void verfication (String username,String code){
        Amplify.Auth.confirmSignUp(
                username,
                code,
                success ->{
                    Log.i(TAG, "verified success");

                    Intent goToSignIn = new Intent(VerificationActivity.this, SignInActivity.class);
                    goToSignIn.putExtra("username",username);
                    goToSignIn.putExtra("password",password);
                    startActivity(goToSignIn);

                },
                error -> Log.e(TAG,"verify Failed:"+error)
        );
    }
}