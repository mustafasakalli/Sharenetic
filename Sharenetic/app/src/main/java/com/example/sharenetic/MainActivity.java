package com.example.sharenetic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton sharePostBtn;
    private ImageButton commentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharePostBtn = (ImageButton) findViewById(R.id.addPostBtn);
        sharePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharePostIntent = new Intent(MainActivity.this,sharePostActivity.class);
                startActivity(sharePostIntent);
                finish();
            }
        });

        commentsBtn = (ImageButton) findViewById(R.id.commentsBtn);
        commentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showCommentsIntent = new Intent(MainActivity.this,postActivity.class);
                startActivity(showCommentsIntent);
                finish();
            }
        });
    }
}
