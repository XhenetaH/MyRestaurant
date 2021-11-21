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
    private ArrayList<MenuItemDetails> menuItemDetails;
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
        holder.title.setText(menuItemDetails.get(position).getName());
        String quantity =Integer.toString(menuItemDetails.get(position).getQuantity());
        String price =Double.toString(menuItemDetails.get(position).getPrice());
        String total =Double.toString(menuItemDetails.get(position).getTotalPrice());

        holder.quantity.setText(quantity);
        holder.price.setText(price);
        holder.total.setText(total);

        Glide.with(mContext)
                .load(menuItemDetails.get(position).getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return menuItemDetails.size();
    }

    public ManageReservationAdapter(Context mContext, ArrayList<MenuItemDetails> _menuItemDetails) {
        this.mContext = mContext;
        this.menuItemDetails = _menuItemDetails;
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
