package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "signInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        configureAmplify();

        Button signIn = findViewById(R.id.Login);
        EditText password = findViewById(R.id.passwordEditLogin);
        EditText username = findViewById(R.id.userNameEditLogIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(username.getText().toString(),password.getText().toString());
            }
        });
    }

    void signIn( String username,String password){
        Amplify.Auth.signIn(
                username,
                password,
                success ->{
                    Log.i(TAG,"login Sucess");
                    Intent goToMain = new Intent (SignInActivity.this, MainActivity.class);
                    startActivity(goToMain);
                },
                error ->{
                    Log.e(TAG,"login Failed: "+error);
                }
        );
    }

    private void configureAmplify() {
        // configure Amplify plugins
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "Successfully initialized Amplify plugins");
        } catch (AmplifyException exception) {
            Log.e(TAG, "Failed to initialize Amplify plugins => " + exception.toString());
        }
    }
}