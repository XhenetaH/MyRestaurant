package com.example.myrestaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import Models.Orders;

public class MainActivity extends AppCompatActivity {

    private Button userLoginBtn;
    private ImageView staffBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staffBtn = findViewById(R.id.btnStaff);
        userLoginBtn = findViewById(R.id.userLoginBTN);

        staffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LogInActivity.class));
            }
        });

        userLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null){
            Intent i = new Intent(MainActivity.this,User_MainActivity.class);
            i.putExtra("TableID",intentResult.getContents());
            startActivity(i);
            Orders.Table = intentResult.getContents();
        }else{
            Toast.makeText(getApplicationContext(), "OOPS... You did not scan anything!", Toast.LENGTH_SHORT).show();
        }
    }
}