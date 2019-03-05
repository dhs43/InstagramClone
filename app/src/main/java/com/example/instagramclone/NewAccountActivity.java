package com.example.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class NewAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateAccountActivity";

    private static Context context;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        //declare context
        NewAccountActivity.context = getApplicationContext();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        etEmail = findViewById(R.id.etEmail);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                CreateAccount(username, email, password);
            }
        });
    }

    private void CreateAccount(String username, String email, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Navigating to main activity");
                    Intent i = new Intent(context, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(context, "Error logging in.", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Issue with login");
                    e.printStackTrace();
                }
            }
        });
    }
}
