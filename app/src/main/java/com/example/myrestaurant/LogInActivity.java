package com.example.myrestaurant;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText email,password;
    private Button loginBtn;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.L_emailTxt);
        password = findViewById(R.id.L_passwordTxt);
        loginBtn = findViewById(R.id.loginBTN);

        fAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email = email.getText().toString();
                String _password = password.getText().toString();
                if(TextUtils.isEmpty(_email) || TextUtils.isEmpty(_password)){
                    Toast.makeText(LogInActivity.this,"Please enter credentials",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    fAuth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogInActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LogInActivity.this,ManagementActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(LogInActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(LogInActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}