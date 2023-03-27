package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore firestore;

    TextView displayName, displayEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        displayName = (TextView) findViewById(R.id.showname);
        displayEmail = (TextView) findViewById(R.id.displayemail);

        firestore = FirebaseFirestore.getInstance();

        String userid = getIntent().getStringExtra("user_id");

        DocumentReference docRef = firestore.collection("user").document(userid);
                docRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    displayName.setText(String.valueOf(documentSnapshot.get("name")));
                    displayEmail.setText(String.valueOf(documentSnapshot.get("email")));

                }
            }
        })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "Not able to retrieve information", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}