package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText nameEt, emailEt, passwordEt;
    Button registerBtn, loginBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEt = (EditText) findViewById(R.id.regname);
        emailEt = (EditText) findViewById(R.id.regemail);
        passwordEt = (EditText) findViewById(R.id.regpassword);
        registerBtn = (Button) findViewById(R.id.register);
        loginBtn = (Button) findViewById(R.id.login);

        firebaseAuth = FirebaseAuth.getInstance();



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = nameEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString().trim();

                Log.d("MainActivity", email);
                Log.d("MainActivity", password);

                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(MainActivity.this, "Name is not entered", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this, "Email is not entered", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password))
                    Toast.makeText(MainActivity.this, "Password is not entered", Toast.LENGTH_SHORT).show();

                if(password.length() < 6)
                    Toast.makeText(MainActivity.this, "Password must contain atleast 6 characters", Toast.LENGTH_SHORT).show();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && password.length() >= 6) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                        //check user get verification email or not
                                        if (firebaseUser != null) {
                                            firebaseUser.sendEmailVerification()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                        }

                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("pass", password);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(MainActivity.this, "Registration is not completed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }
}