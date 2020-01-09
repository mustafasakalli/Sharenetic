package com.example.sharenetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class signupActivity extends AppCompatActivity {

    private EditText signName;
    private EditText signEmailText;
    private EditText signPassText;
    private Button signupBtn;
    private Button loginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginBtn = (Button) findViewById(R.id.signup_loginbtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(signupActivity.this, loginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });


        /*
        mAuth = FirebaseAuth.getInstance();

        final String email = "beyzakurt1998@hotmail.com";
        final String pass = "012345";
        final String name = "Beyza Kurt";

        signupBtn = (Button) findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUserAccount(email, name, pass);

            }
        });
        */
    }

    private void CreateUserAccount(String email, final String name, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Context context = getApplicationContext();
                            Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();

                            updateUserInfo(name, mAuth.getCurrentUser());
                        }
                    }
                });
    }

    private void updateUserInfo(String name, FirebaseUser currentUser) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        currentUser.updateProfile(profileUpdate);
    }
}
