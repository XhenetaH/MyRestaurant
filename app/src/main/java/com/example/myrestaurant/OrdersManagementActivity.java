package com.example.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Models.ChangeNumberItemsListener;
import Models.ManagementCard;
import Models.MenuItemDetails;
import Models.MyBag;
import Models.Orders;

public class OrdersManagementActivity extends AppCompatActivity {
    private TextView subtotal, _total, checkoutBtn;
    private RecyclerView recyclerView;
    private ArrayList<MenuItemDetails> menuItemsList;
    private OrderAdapter orderAdapter = new OrderAdapter();
    private ScrollView scrollView;
    private Orders orders;
    double tax;
    private String TableID;
    public DatabaseReference root = FirebaseDatabase.getInstance().getReference("Orders");
    public DatabaseReference root2;

    private ManagementCard managementCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_management);
        recyclerView = findViewById(R.id.orderRecyclerView);
        subtotal = findViewById(R.id.subTotalTXT);
        _total = findViewById(R.id.totalTXT);
        managementCard = new ManagementCard(this);
        menuItemsList = new ArrayList<>();
        scrollView = findViewById(R.id.scrollView4);
        checkoutBtn = findViewById(R.id.checkoutBTN);


        InitList();
        calculateCard();
        bottomNavigation();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableID = Orders.Table;
                root2 = FirebaseDatabase.getInstance().getReference("Orders").child(TableID);
                uploadToFirebase();
            }
        });

    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.tableFloatingBTN);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrdersManagementActivity.this, OrdersManagementActivity.class));
            }
        });

    }
    private void calculateCard() {
        double percentTax = 0.02;
        tax = Math.round((managementCard.getTotalFee() * percentTax) * 100.0) / 100.0;
        double total = Math.round((managementCard.getTotalFee() + tax) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCard.getTotalFee() * 100.0) / 100.0;

        subtotal.setText(Double.toString(itemTotal));
        _total.setText(Double.toString(total));
    }

    private void InitList()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(this, MyBag.menuItemDetails, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });
        recyclerView.setAdapter(orderAdapter);
        if (MyBag.menuItemDetails.isEmpty()) {

            scrollView.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void uploadToFirebase() {
        double tot =Double.parseDouble(_total.getText().toString());
        String _id = root.push().getKey();

        orders = new Orders();
        orders.setID(_id);
        orders.setTableID(TableID);
        orders.setTotal(tot);
        root.child(_id).setValue(orders).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                for(int i=0; i<MyBag.menuItemDetails.size();i++) {
                    String menuId = root.push().getKey();
                    root.child(_id).child("MenuItems").child(menuId).setValue(MyBag.menuItemDetails.get(i));
                }
                Toast.makeText(OrdersManagementActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }


}