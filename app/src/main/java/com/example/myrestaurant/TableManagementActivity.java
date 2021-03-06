package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DB.TablesDB;
import Models.MenuItem;
import Models.Table;

public class TableManagementActivity extends AppCompatActivity {
    private FloatingActionButton tableFB;
    private DatabaseReference myRef;
    private ArrayList<Table> tableList;
    private TableAdapter tableAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_management);
        tableFB = findViewById(R.id.tableFloatingBTN);
        myRef = FirebaseDatabase.getInstance().getReference();
        tableList = new ArrayList<>();
        recyclerView = findViewById(R.id.tableRecycleView);

        tableFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TableManagementActivity.this,AddTableActivity.class));
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ClearAll();
        GetDataFromFirebase();
    }

    private void GetDataFromFirebase(){
        Query query = myRef.child("Tables");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    Table table = new Table();
                    table.setImageUrl(snapsh.child("imageUrl").getValue().toString());
                    table.setName(snapsh.child("name").getValue().toString());
                    table.setID(snapsh.child("id").getValue().toString());
                    table.setStatus(snapsh.child("status").getValue().toString());
                    tableList.add(table);
                }
                tableAdapter = new TableAdapter(TableManagementActivity.this,tableList);
                recyclerView.setAdapter(tableAdapter);
                tableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(tableList!=null){
            tableList.clear();

            if(tableAdapter!=null){
                tableAdapter.notifyDataSetChanged();
            }
        }

        tableList = new ArrayList<>();
    }
}