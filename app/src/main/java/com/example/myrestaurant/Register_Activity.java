package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    private EditText fullnameTxt,emailTxt,passwordTxt,confirmPassTxt;
    private Button registerBtn;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullnameTxt = findViewById(R.id.fullnameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        confirmPassTxt = findViewById(R.id.confirmPassTxt);
        registerBtn = findViewById(R.id.registerBtn);

        fAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailTxt.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordTxt.setError("Password is Required.");
                    return;
                }
                if(password.length()<6){
                    passwordTxt.setError("Password must be longer than 6 char.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register_Activity.this,"User Created Successfully!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Register_Activity.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }
}