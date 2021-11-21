package com.example.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Models.MenuItem;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHoder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<MenuItem> menuList;

    public MenuItemAdapter(){

    }
    public MenuItemAdapter(Context mContext, ArrayList<MenuItem> MenuList) {
        this.mContext = mContext;
        this.menuList = MenuList;
    }
    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        MenuItem menuItem = menuList.get(position);
        holder.textView.setText(menuList.get(position).getName());
        String price =Double.toString(menuList.get(position).getPrice());
        holder.priceTxt.setText(price);

        //ImageView
        Glide.with(mContext)
                .load(menuList.get(position).getImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* EditText name = view.findViewById(R.id.nameTXT);
                ImageView img = view.findViewById(R.id.imageView);*/

                Intent i = new Intent(view.getContext(),UDMenuItemActivity.class);
                i.putExtra("Name",menuItem.getName().toString());
                i.putExtra("Image" ,menuItem.getImageUrl());
                i.putExtra("id",menuItem.getId());
                i.putExtra("description",menuItem.getDescription().toString());
                i.putExtra("price",price);
                i.putExtra("category",menuItem.getCategory());
                view.getContext().startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView,priceTxt;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_itemIV);
            textView = itemView.findViewById(R.id.menu_itemName);
            priceTxt = itemView.findViewById(R.id.menu_itemPrice);
        }
    }
}
