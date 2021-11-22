package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.MenuItemDetails;
import Models.Orders;

public class ManageReservation_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference myRef;
    private ArrayList<Orders> orderList;
    private ArrayList<MenuItemDetails> menuList;
    private ManageReservationAdapter manageReservationAdapter;
    private TextView price, total, startBtn, cancelBtn;
    String OrderId,TableId,status;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_order_list);
        floatingActionButton = findViewById(R.id.reservationFloating);
        recyclerView = findViewById(R.id.dispOrderRecyclerView);
        OrderId = getIntent().getStringExtra("OrderId");
        status = getIntent().getStringExtra("status");
        price = findViewById(R.id.subTotTxt);
        total = findViewById(R.id.totTXT);
        startBtn = findViewById(R.id.startBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference("Orders");
        menuList = new ArrayList<>();
        orderList = new ArrayList<>();

        ClearAll();
        GetDataFromFirebase();

       if(status.equals("prepare"))
            startBtn.setVisibility(View.GONE);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateStatus(OrderId,"prepare");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateStatus(OrderId,"canceled");
                finish();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageReservation_Activity.this,ManagementActivity.class));
            }
        });

    }
    private void GetDataFromFirebase(){
        Query query = myRef;
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

                    for(DataSnapshot snapshot1 : snapsh.child("MenuItems").getChildren())
                    {
                        MenuItemDetails menuItemDetails = new MenuItemDetails();

                        menuItemDetails.setName(snapshot1.child("name").getValue().toString());
                        menuItemDetails.setID(snapshot1.child("id").getValue().toString());
                        menuItemDetails.setImageUrl(snapshot1.child("imageUrl").getValue().toString());
                        menuItemDetails.setPrice(Double.parseDouble(snapshot1.child("price").getValue().toString()));
                        menuItemDetails.setTotalPrice(Double.parseDouble(snapshot1.child("totalPrice").getValue().toString()));
                        menuItemDetails.setQuantity(Integer.parseInt(snapshot1.child("quantity").getValue().toString()));

                        menuItemDetailsList.add(menuItemDetails);
                    }

                    orders.setMenuItemList(menuItemDetailsList);

                    orderList.add(orders);
                }
                for(int i=0; i<orderList.size();i++)
                {
                    if(orderList.get(i).getID().toString().equals(OrderId))
                    {
                        menuList = orderList.get(i).getMenuItemList();
                    }
                }
                price.setText(String.valueOf(calcPrice(menuList)));
                total.setText(String.valueOf(calctotal(menuList)));
                manageReservationAdapter = new ManageReservationAdapter(getApplicationContext(),menuList);
                recyclerView.setAdapter(manageReservationAdapter);
                manageReservationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(orderList!=null){
            orderList.clear();

            if(manageReservationAdapter!=null){
                manageReservationAdapter.notifyDataSetChanged();
            }
        }

        orderList = new ArrayList<>();
    }

    private double calctotal(ArrayList<MenuItemDetails> menuItemDetailsList)
    {
        return Math.round(calcPrice(menuItemDetailsList) + 0.07*calcPrice(menuItemDetailsList));
    }
    private double calcPrice(ArrayList<MenuItemDetails> menuItemDetailsList)
    {
        double sum=0;
        for(int i=0; i<menuItemDetailsList.size();i++)
        {
            sum+=menuItemDetailsList.get(i).getTotalPrice();
        }
        return sum;
    }

    private void UpdateStatus(String orderId,String status){
        Map<String,Object> mapOrder = new HashMap<>();
        mapOrder.put("status", status);
        myRef.child(orderId).updateChildren(mapOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ManageReservation_Activity.this,"success",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ManageReservation_Activity.this,"failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}