package com.example.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import Models.MyBag;

public class MyBagActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyBagAdapter myBagAdapter;
    private Button orderNowBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bag);

        recyclerView = findViewById(R.id.myBagRecyclerView);
        orderNowBtn = findViewById(R.id.bag_OrderNowBTN);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        myBagAdapter = new MyBagAdapter(getApplicationContext(), MyBag.menuItemDetails);
        recyclerView.setAdapter(myBagAdapter);


        orderNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"dfds",Toast.LENGTH_SHORT).show();
            }
        });

    }
}