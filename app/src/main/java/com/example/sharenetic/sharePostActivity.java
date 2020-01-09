package com.example.sharenetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sharePostActivity extends AppCompatActivity {

    private ImageView icon;
    private EditText postText;
    private Button submitBtn;
    private Button cancelBtn;
    private Spinner spinner;
    private ProgressBar bar;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();


        icon = (ImageView) findViewById(R.id.icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(sharePostActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(sharePostActivity.this, MainActivity.class);
                startActivity(cancelIntent);
                finish();
            }
        });

        spinner = (Spinner) findViewById(R.id.classes);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("GUI Programming");
        arrayList.add("Artificial Intelligence");
        arrayList.add("Computer Networks");
        arrayList.add("Design and Analysis of Algorithms");

        final String[] sClass = {null};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sClass[0] = arrayList.get(position);
                //Toast.makeText(sharePostActivity.this, "Selected class is "+ arrayList.get(position), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        postText = (EditText) findViewById(R.id.post);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post = postText.getText().toString();

                if (!post.isEmpty()){
                    cancelBtn.setVisibility(View.INVISIBLE);
                    submitBtn.setVisibility(View.INVISIBLE);
                    bar.setVisibility(View.VISIBLE);

                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("post", post);
                    postMap.put("class", sClass[0]);
                    postMap.put("user_id", current_user_id);
                    postMap.put("timestamp", FieldValue.serverTimestamp());

                    firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Intent homeIntent = new Intent(sharePostActivity.this, MainActivity.class);
                                startActivity(homeIntent);
                                finish();
                            }
                            else{
                                cancelBtn.setVisibility(View.VISIBLE);
                                submitBtn.setVisibility(View.VISIBLE);
                                bar.setVisibility(View.INVISIBLE);

                                Toast.makeText(getApplicationContext(), "Try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
