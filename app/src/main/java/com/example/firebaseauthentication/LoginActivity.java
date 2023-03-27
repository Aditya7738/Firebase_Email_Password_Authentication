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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    Button loginBtn;
    FirebaseAuth firebaseAuth;

    TextView forgotTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = (EditText) findViewById(R.id.logemail);
        passwordEt = (EditText) findViewById(R.id.logpassword);
        loginBtn = (Button) findViewById(R.id.login);
        forgotTv = (TextView) findViewById(R.id.forgotpass);

        firebaseAuth = FirebaseAuth.getInstance();
        
        forgotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lemail = emailEt.getText().toString();
                if(TextUtils.isEmpty(lemail)){
                    Toast.makeText(LoginActivity.this, "Email is not entered", Toast.LENGTH_LONG).show();
                }
                String lpass = passwordEt.getText().toString();
                if(TextUtils.isEmpty(lpass)){
                    Toast.makeText(LoginActivity.this, "Password is not entered", Toast.LENGTH_LONG).show();
                }

                if(!TextUtils.isEmpty(lemail) && !TextUtils.isEmpty(lpass)) {
                    boolean fpchanged = getIntent().getBooleanExtra("pchange", false);
                    if (fpchanged) {
                        firebaseAuth.signInWithEmailAndPassword(lemail, lpass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();

                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Login is not successful", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } else {


                        Intent logIntent = getIntent();
                        String uemail = logIntent.getStringExtra("email");
                        String upass = logIntent.getStringExtra("pass");

                        //Log.d("Login", uemail);
                        //Log.d("Login", upass);

                        Log.d("Login", lemail);
                        Log.d("Login", lpass);

                        if (lemail.equals(uemail) && lpass.equals(upass)) {
                            firebaseAuth.signInWithEmailAndPassword(lemail, lpass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                                        .putExtra("user_id",  getIntent().getStringExtra("userid")));
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Login is not successful", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                }
            }
        });

    }
}