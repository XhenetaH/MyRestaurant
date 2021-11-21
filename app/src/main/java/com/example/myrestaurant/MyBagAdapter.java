package com.example.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Models.MenuItemDetails;
import Models.Table;

public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.ViewHoder>{
    private Context mContext;
    private ArrayList<MenuItemDetails> menuItemDetailsList;
    public MyBagAdapter(){

    }

    public MyBagAdapter(Context mContext, ArrayList<MenuItemDetails> menuItemDetailsList) {
        this.mContext = mContext;
        this.menuItemDetailsList = menuItemDetailsList;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bag_tem,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        MenuItemDetails menuItemDetails = menuItemDetailsList.get(position);

        holder.name.setText(menuItemDetailsList.get(position).getName());
        holder.totalPrice.setText(Double.toString(menuItemDetailsList.get(position).getTotalPrice()));
        holder.price.setText(Double.toString(menuItemDetailsList.get(position).getPrice()));
        holder.quantity.setText(Integer.toString(menuItemDetailsList.get(position).getQuantity()));

        //ImageView
        Glide.with(mContext)
                .load(menuItemDetailsList.get(position).getImageUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return menuItemDetailsList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price,quantity,totalPrice;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.bag_itemImageView);
            name = itemView.findViewById(R.id.bag_item_name);
            price = itemView.findViewById(R.id.bag_item_price);
            quantity = itemView.findViewById(R.id.bag_item_quantity);
            totalPrice = itemView.findViewById(R.id.bag_item_totalPrice);
        }
    }
}
