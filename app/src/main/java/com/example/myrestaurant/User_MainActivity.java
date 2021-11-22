package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Models.MenuItem;

public class User_MainActivity extends AppCompatActivity {
    private RecyclerView category_recyclerView,menu_recyclerView;
    private DatabaseReference myRef;
    private ArrayList<Category> categoriesList;
    private ArrayList<MenuItem> menuitemsList;
    private FloatingActionButton bagImageView;
    private User_CategoryList_Adpater category_recyclerAdapter;
    private OurMenu_Adapter menu_Adapter;
    private String categroy = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        bagImageView = findViewById(R.id.categoryFloatingBTN);
        category_recyclerView = findViewById(R.id.categoryList_recycler);
        menu_recyclerView = findViewById(R.id.allMenuListRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        category_recyclerView.setLayoutManager(linearLayoutManager);
        category_recyclerView.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        menu_recyclerView.setLayoutManager(linearLayoutManager1);
        menu_recyclerView.setHasFixedSize(true);

        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        //ArrayList
        categoriesList = new ArrayList<>();
        searchMenuItem();

        ClearAll();
        GetCategoryFromFirebase();
        GetDataFromFirebase();

        bagImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_MainActivity.this,OrdersManagementActivity.class));
            }
        });


    }

    private void searchMenuItem()
    {
        SearchView searchView =(SearchView)findViewById(R.id.catsearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<MenuItem> filteredList = new ArrayList<>();
                for(MenuItem menuItem : menuitemsList)
                {
                    if(menuItem.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        filteredList.add(menuItem);
                    }
                }
                OurMenu_Adapter adapter = new OurMenu_Adapter(getApplicationContext(),filteredList);
                menu_recyclerView.setAdapter(adapter);
                return false;
            }
        });
    }

    //Kishe me filtru
    public void FilterMenuitems(){
        ArrayList<MenuItem> filteredList = new ArrayList<>();
        if(categroy!="")
        {
            for(MenuItem menuItem : menuitemsList)
            {
                if(menuItem.getCategory().toLowerCase().contains(categroy.toLowerCase()))
                {
                    filteredList.add(menuItem);
                }
            }
            OurMenu_Adapter adapter = new OurMenu_Adapter(getApplicationContext(),filteredList);
            menu_recyclerView.setAdapter(adapter);
        }
    }


    private void GetDataFromFirebase(){
        Query query = myRef.child("MenuItems");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAllMenu();
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    MenuItem menu = new MenuItem();
                    menu.setImageUrl(snapsh.child("imageUrl").getValue().toString());
                    menu.setName(snapsh.child("name").getValue().toString());
                    menu.setDescription(snapsh.child("description").getValue().toString());
                    menu.setId(snapsh.child("id").getValue().toString());
                    menu.setCategory(snapsh.child("category").getValue().toString());
                    menu.setPrice(Double.parseDouble(snapsh.child("price").getValue().toString()));

                    menuitemsList.add(menu);
                }

                menu_Adapter = new OurMenu_Adapter(User_MainActivity.this,menuitemsList);
                menu_recyclerView.setAdapter(menu_Adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetCategoryFromFirebase(){
        Query query = myRef.child("Categories");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    Category categories = new Category();
                    categories.setImageUrl(snapsh.child("imageUrl").getValue().toString());
                    categories.setName(snapsh.child("name").getValue().toString());
                    categories.setID(snapsh.child("id").getValue().toString());
                    categoriesList.add(categories);
                }
                category_recyclerAdapter= new User_CategoryList_Adpater(getApplicationContext(),categoriesList);
                category_recyclerView.setAdapter(category_recyclerAdapter);
                category_recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void ClearAllMenu(){
        if(menuitemsList!=null){
            menuitemsList.clear();

            if(menu_Adapter!=null){
                menu_Adapter.notifyDataSetChanged();
            }
        }

        menuitemsList = new ArrayList<>();
    }

    private void ClearAll(){
        if(categoriesList!=null){
            categoriesList.clear();

            if(category_recyclerAdapter!=null){
                category_recyclerAdapter.notifyDataSetChanged();
            }
        }

        categoriesList = new ArrayList<>();
    }
}