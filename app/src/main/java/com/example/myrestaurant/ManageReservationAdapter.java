package com.example.myrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Models.MenuItemDetails;
import Models.Orders;

public class ManageReservationAdapter extends RecyclerView.Adapter<ManageReservationAdapter.ViewHoder> {
    private Context mContext;
    private ArrayList<MenuItemDetails> menuItemDetailsList;
    public ManageReservationAdapter(){

    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayorder_item,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.title.setText(menuItemDetailsList.get(position).getName());
        holder.quantity.setText(menuItemDetailsList.get(position).getQuantity());
        holder.price.setText(Double.toString(menuItemDetailsList.get(position).getPrice()));
        holder.total.setText(Double.toString(menuItemDetailsList.get(position).getTotalPrice()));

        Glide.with(mContext)
                .load(menuItemDetailsList.get(position).getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return menuItemDetailsList.size();
    }

    public ManageReservationAdapter(Context mContext, ArrayList<MenuItemDetails> menuItemDetailsList) {
        this.mContext = mContext;
        this.menuItemDetailsList = menuItemDetailsList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,price,total,quantity;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.diplayorderIV);
            title = itemView.findViewById(R.id.displayorderTitle);
            price = itemView.findViewById(R.id.displayorderPrice);
            total = itemView.findViewById(R.id.displayorderTotalPrice);
            quantity = itemView.findViewById(R.id.displayorderQuantity);

        }
    }
}
