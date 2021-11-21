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

import Models.MenuItemDetails;
import Models.Orders;
import Models.Table;

public class Reservation_Activity extends AppCompatActivity {
    private FloatingActionButton tableFB;
    private DatabaseReference myRef;
    private ArrayList<Orders> orderList;
    private ReservationAdapter reservationAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        tableFB = findViewById(R.id.reservationFAB);
        myRef = FirebaseDatabase.getInstance().getReference();
        orderList = new ArrayList<>();
        recyclerView = findViewById(R.id.reservationRecycleView);
        tableFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reservation_Activity.this,ManagementActivity.class));
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ClearAll();
        GetDataFromFirebase();
    }

    private void GetDataFromFirebase(){
        Query query = myRef.child("Orders");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    ArrayList<MenuItemDetails> menuItemDetailsList = new ArrayList<>();
                    Orders orders = new Orders();

                    orders.setTotal(Double.parseDouble(snapsh.child("total").getValue().toString()));
                    orders.setTableID(snapsh.child("tableID").getValue().toString());
                    orders.setID(snapsh.child("id").getValue().toString());
                    orders.setStatus(snapsh.child("status").getValue().toString());
                    if(orders.getStatus().toString().equals("ordered"))
                        orderList.add(orders);

                }

                reservationAdapter = new ReservationAdapter(getApplicationContext(),orderList);
                recyclerView.setAdapter(reservationAdapter);
                reservationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(orderList!=null){
            orderList.clear();

            if(reservationAdapter!=null){
                reservationAdapter.notifyDataSetChanged();
            }
        }

        orderList = new ArrayList<>();
    }

}