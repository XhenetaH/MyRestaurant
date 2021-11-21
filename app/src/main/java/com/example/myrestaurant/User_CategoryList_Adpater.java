package com.example.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User_CategoryList_Adpater extends RecyclerView.Adapter<User_CategoryList_Adpater.ViewHoder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Category> categoriesList;

    public User_CategoryList_Adpater(){

    }

    @NonNull
    @Override
    public User_CategoryList_Adpater.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category_item,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Category cat = categoriesList.get(position);
        //TextVIew
        holder.textView.setText(cat.getName());

        //ImageView
        Glide.with(mContext)
                .load(cat.getImageUrl())
                .into(holder.imageView);
        ///Bon edhe holder.viewitem.setOnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent("custom-message");
                i.putExtra("CategoryName",cat.getName());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);

            }
        });
    }



    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public User_CategoryList_Adpater(Context mContext, ArrayList<Category> categoriesList) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
    }





    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView5);
            textView = itemView.findViewById(R.id.categorylist_name);
        }
    }
}
