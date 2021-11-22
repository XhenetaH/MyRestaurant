package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import DB.CategoriesDB;
import DB.TablesDB;

public class UDTableActivity extends AppCompatActivity {
    private TablesDB db = new TablesDB();
    private EditText nameTxt;
    private ImageView updateBtn, deleteBtn,imageV;
    private Button busyBtn, freeBtn;
    private ProgressBar progressBar;
    private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udtable);
        progressBar = findViewById(R.id.udTablePrograssBar);
        nameTxt = findViewById(R.id.udTableName);
        imageV = findViewById(R.id.udtableImageView);
        updateBtn = findViewById(R.id.tableUpdateBtn);
        deleteBtn = findViewById(R.id.tableDeleteBtn);
        cardView = findViewById(R.id.cardView);
        freeBtn = findViewById(R.id.freeBtn);
        busyBtn = findViewById(R.id.busyBtn);

        String name = getIntent().getStringExtra("Name");
        String ID = getIntent().getStringExtra("id");
        String Status = getIntent().getStringExtra("status");
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("Image")).into(imageV);
        nameTxt.setText(name);

        if(Status.equals("busy"))
        {
            cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_category_background));
            busyBtn.setVisibility(View.GONE);
        }
        else if(Status.equals("waiting"))
        {
            cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.yellow_category_background));
        }
        else if(Status.equals("free"))
        {
            cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.green_category_background));
            busyBtn.setVisibility(View.GONE);
            freeBtn.setVisibility(View.GONE);
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTable(ID);
            }
        });

        busyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(ID,"busy");
                cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_category_background));
            }
        });
        freeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(ID,"free");
                cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.green_category_background));
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UDTableActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.root.child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UDTableActivity.this,"Deleted successfully!",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UDTableActivity.this,"Failed!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UDTableActivity.this,"Cancelled.",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

    }

    private void updateTable(String ID){
        Map<String,Object> map = new HashMap<>();
        map.put("name",nameTxt.getText().toString());

        db.root.child(ID).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStatus(String id, String status)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);

        db.root.child(id).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Status Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}