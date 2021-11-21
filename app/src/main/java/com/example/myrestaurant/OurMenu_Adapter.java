package com.example.myrestaurant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.MenuItem;
import Models.MenuItemDetails;
import Models.MyBag;

public class OurMenu_Adapter extends RecyclerView.Adapter<OurMenu_Adapter.ViewHoder>{
    private Context mContext;
    private ArrayList<MenuItem> menuItemList;

    public OurMenu_Adapter(){

    }

    @NonNull
    @Override
    public OurMenu_Adapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_menu_items,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OurMenu_Adapter.ViewHoder holder, int position) {
        MenuItem menuItem = menuItemList.get(position);
        //TextVIew
        holder.textView.setText(menuItem.getName());
        String price =Double.toString(menuItemList.get(position).getPrice());
        holder.priceView.setText(price);

        //ImageView
        Glide.with(mContext)
                .load(menuItem.getImageUrl())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),MenuItem_Details_Activity.class);
                i.putExtra("Name",menuItem.getName().toString());
                i.putExtra("Image" ,menuItem.getImageUrl());
                i.putExtra("id",menuItem.getId());
                i.putExtra("description",menuItem.getDescription().toString());
                i.putExtra("price",price);

                v.getContext().startActivity(i);

            }
        });

    }



    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public OurMenu_Adapter(Context mContext, ArrayList<MenuItem> menuList) {
        this.mContext = mContext;
        this.menuItemList = menuList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView, priceView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView3);
            textView = itemView.findViewById(R.id.mItemName);
            priceView = itemView.findViewById(R.id.menu_price);
        }
    }
}
