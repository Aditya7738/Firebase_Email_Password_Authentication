package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassActivity extends AppCompatActivity {

    EditText rpemailEt;
    Button resetBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        rpemailEt = (EditText) findViewById(R.id.rpemailEt);
        resetBtn = (Button) findViewById(R.id.resetBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTorp = rpemailEt.getText().toString();

                if (TextUtils.isEmpty(emailTorp)) {
                    Toast.makeText(ForgotPassActivity.this, "Email is not entered", Toast.LENGTH_LONG).show();
                } else {

                    firebaseAuth.sendPasswordResetEmail(emailTorp, null)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        Toast.makeText(ForgotPassActivity.this,
                                                "Email to reset password have send", Toast.LENGTH_LONG).show();

                                        boolean passchange = true;
                                        Intent fpintent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                                        fpintent.putExtra("pchange", passchange);
                                        startActivity(fpintent);
                                    }else{
                                        Toast.makeText(ForgotPassActivity.this,
                                                "Fail to send email", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                    }
                });
            }
}