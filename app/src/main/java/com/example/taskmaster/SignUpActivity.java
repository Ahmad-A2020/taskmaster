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
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        configureAmplify();

        Button signup = findViewById(R.id.signup);
        Button SignIn = findViewById(R.id.SignIn);
        EditText email = findViewById(R.id.emailEdit);
        EditText password = findViewById(R.id.passwordEdit);
        EditText userName = findViewById(R.id.username);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG,userName.getText().toString());
                Log.i(TAG,email.getText().toString());
                Log.i(TAG,password.getText().toString());

                signUp(userName.getText().toString(),email.getText().toString(),password.getText().toString());
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInPage= new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(signInPage);
            }
        });

    }
    void signUp(String userName, String email, String password){

        Amplify.Auth.signUp(
                userName,
                password,
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email)
                        .build(),
                sucess ->{
                    Log.i(TAG,"signUp successfully ");
                    Intent goToVerification = new Intent(SignUpActivity.this,VerificationActivity.class);
                    goToVerification.putExtra("userName",userName);
                    goToVerification.putExtra("password",password);
                    startActivity(goToVerification);

                },
                error ->{
                    Log.e(TAG, "error in DignUp"+error.toString());
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