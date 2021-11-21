package com.example.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import DB.CategoriesDB;
import DB.MenuItemDB;
import Models.ManagementCard;
import Models.MenuItemDetails;
import Models.MyBag;

public class MenuItem_Details_Activity extends AppCompatActivity {
    private MenuItemDB db = new MenuItemDB();

    private TextView nameTxt,descriptionTxt,priceTxt,quantity;
    private ImageView imageV;
    private Button decrementBtn, incrementBtn, AddToCardBtn;
    double totalPrice;
    private ManagementCard managementCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_details);
        nameTxt = findViewById(R.id.detail_name);
        descriptionTxt = findViewById(R.id.detail_description);
        priceTxt = findViewById(R.id.detail_price);
        imageV = findViewById(R.id.imageView4);
        decrementBtn = findViewById(R.id.decrementBTN);
        incrementBtn = findViewById(R.id.incrementBTN);
        quantity = findViewById(R.id.quantityTXT);
        AddToCardBtn = findViewById(R.id.addToBagBTN);

        String name = getIntent().getStringExtra("Name");
        String ID = getIntent().getStringExtra("id");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("Image");
        String price = getIntent().getStringExtra("price");



        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("Image")).into(imageV);

        nameTxt.setText(name);
        descriptionTxt.setText(description);
        priceTxt.setText(price);

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = quantity.getText().toString();

                if(Integer.parseInt(currentValue)>1) {
                    int value = Integer.parseInt(currentValue);
                    value--;
                    quantity.setText(String.valueOf(value));
                    totalPrice = value * Double.parseDouble(price);


                }
            }
        });

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = quantity.getText().toString();

                int value = Integer.parseInt(currentValue);
                value++;
                quantity.setText(String.valueOf(value));
                totalPrice = value * Double.parseDouble(price);
            }
        });

        AddToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuItemDetails menuItemDetails =new MenuItemDetails();
                menuItemDetails.setImageUrl(imageUrl);
                menuItemDetails.setName(name);
                menuItemDetails.setPrice(Double.parseDouble(price));
                menuItemDetails.setQuantity(Integer.parseInt(quantity.getText().toString()));
                menuItemDetails.setTotalPrice(totalPrice);
                menuItemDetails.setID(ID);


                boolean existAlready = false;
                int n = 0;
                for (int i = 0; i < MyBag.menuItemDetails.size(); i++) {
                    if (MyBag.menuItemDetails.get(i).getID().equals(menuItemDetails.getID())) {
                        existAlready = true;
                        n = i;
                        break;
                    }
                }

                if (existAlready) {
                    MyBag.menuItemDetails.get(n).setQuantity(menuItemDetails.getQuantity()+MyBag.menuItemDetails.get(n).getQuantity());
                } else {
                    MyBag.menuItemDetails.add(menuItemDetails);
                }

                Toast.makeText(getApplicationContext(), "Added To Your Card", Toast.LENGTH_SHORT).show();
                /*ArrayList<MenuItemDetails> menuItemsList = new ArrayList<>();
                boolean result = true;
                for(int i = 0; i< MyBag.menuItemDetails.size(); i++)
                {
                    if (MyBag.menuItemDetails.get(i).getID().toString() == ID)
                        result=false;
                    Toast.makeText(getApplicationContext(), "This item is in your bag.", Toast.LENGTH_SHORT).show();
                }
                if(result)
                {
                    MenuItemDetails menuItemDetails =new MenuItemDetails();
                    menuItemDetails.setImageUrl(imageUrl);
                    menuItemDetails.setName(name);
                    menuItemDetails.setPrice(Double.parseDouble(price));
                    menuItemDetails.setQuantity(Integer.parseInt(quantity.getText().toString()));
                    menuItemDetails.setTotalPrice(totalPrice);
                    menuItemDetails.setID(ID);
                    MyBag.menuItemDetails.add(menuItemDetails);
                }*/

            }
        });

    }
}