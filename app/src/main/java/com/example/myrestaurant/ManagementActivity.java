package com.example.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class ManagementActivity extends AppCompatActivity {
    private Button categoryBtn,menuBtn,tableBtn,reservationBtn,userBtn;
    private ImageView logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        categoryBtn = findViewById(R.id.categoryManagementBTN);
        menuBtn = findViewById(R.id.menuItemManagementBTN);
        tableBtn = findViewById(R.id.tablesManagementBTN);
        reservationBtn = findViewById(R.id.reservationManagementBTN);
        userBtn = findViewById(R.id.userRegisterManagementBTN);
        logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ManagementActivity.this,MainActivity.class));
            }
        });

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this,Register_Activity.class));
            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this,CategoryManagementActivity.class));
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this,MenuItemManagementActivity.class));
            }
        });

        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this,TableManagementActivity.class));
            }
        });

        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementActivity.this,Reservation_Activity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }
}