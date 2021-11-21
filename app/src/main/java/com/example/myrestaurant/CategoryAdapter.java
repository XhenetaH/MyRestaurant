package com.example.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHoder> {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Category> categoryList;

    public CategoryAdapter(){

    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Category cat = categoryList.get(position);
        holder.textView.setText(categoryList.get(position).getName());


        //ImageView
        Glide.with(mContext)
                .load(categoryList.get(position).getImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* EditText name = view.findViewById(R.id.nameTXT);
                ImageView img = view.findViewById(R.id.imageView);*/

                Intent i = new Intent(view.getContext(),UDCategoryActivity.class);
                i.putExtra("Name",cat.getName().toString());
                i.putExtra("Image",cat.getImageUrl());
                i.putExtra("id",cat.getID());
                i.putExtra("description",cat.getDescription().toString());
                view.getContext().startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public CategoryAdapter(Context mContext, ArrayList<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.nameTXT);

        }
    }
}
