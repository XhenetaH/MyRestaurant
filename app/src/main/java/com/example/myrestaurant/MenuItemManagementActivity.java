package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DB.CategoriesDB;
import Models.MenuItem;

public class MenuItemManagementActivity extends AppCompatActivity {
    private FloatingActionButton addMenuItemBTN;

    private RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //Varaibles
    private ArrayList<MenuItem> menuItemsList;
    private MenuItemAdapter menuItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_management);
        addMenuItemBTN = findViewById(R.id.menuitemFloatingBTN);
        recyclerView = findViewById(R.id.menuItemRecycleView);

        addMenuItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuItemManagementActivity.this,AddMenuItemActivity.class));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference();
        menuItemsList = new ArrayList<>();
        //Clear ArrayList
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

    }
    private void GetDataFromFirebase(){
        Query query = myRef.child("MenuItems");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    MenuItem menu = new MenuItem();
                    menu.setImageUrl(snapsh.child("imageUrl").getValue().toString());
                    menu.setName(snapsh.child("name").getValue().toString());
                    menu.setDescription(snapsh.child("description").getValue().toString());
                    menu.setId(snapsh.child("id").getValue().toString());
                    menu.setCategory(snapsh.child("category").getValue().toString());
                    menu.setPrice(Double.parseDouble(snapsh.child("price").getValue().toString()));

                    menuItemsList.add(menu);
                }

                menuItemAdapter = new MenuItemAdapter(MenuItemManagementActivity.this,menuItemsList);
                recyclerView.setAdapter(menuItemAdapter);
                menuItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll(){
        if(menuItemsList!=null){
            menuItemsList.clear();

            if(menuItemAdapter!=null){
                menuItemAdapter.notifyDataSetChanged();
            }
        }

        menuItemsList = new ArrayList<>();
    }
}