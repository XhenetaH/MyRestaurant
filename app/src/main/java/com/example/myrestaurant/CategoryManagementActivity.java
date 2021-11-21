package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryManagementActivity extends AppCompatActivity {
    private FloatingActionButton addcategoryBtn;

    private RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //Varaibles
    private ArrayList<Category> categoriesList;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);
        addcategoryBtn = findViewById(R.id.categoryFloatingBTN);
        recyclerView = findViewById(R.id.categoryRecycleView);

        addcategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryManagementActivity.this,AddCategoryActivity.class));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference();
        categoriesList = new ArrayList<>();
        //Clear ArrayList
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();
    }
    private void GetDataFromFirebase(){
        Query query = myRef.child("Categories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    Category categories = new Category();
                    categories.setImageUrl(snapsh.child("imageUrl").getValue().toString());
                    categories.setName(snapsh.child("name").getValue().toString());
                    categories.setDescription(snapsh.child("description").getValue().toString());
                    categories.setID(snapsh.child("id").getValue().toString());

                    categoriesList.add(categories);
                }
                categoryAdapter = new CategoryAdapter(getApplicationContext(),categoriesList);
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(categoriesList!=null){
            categoriesList.clear();

            if(categoryAdapter!=null){
                categoryAdapter.notifyDataSetChanged();
            }
        }

        categoriesList = new ArrayList<>();
    }
}