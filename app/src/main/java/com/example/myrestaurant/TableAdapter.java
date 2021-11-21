package com.example.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Models.Table;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHoder> {
    private Context mContext;
    private ArrayList<Table> tableList;

    public TableAdapter(){}

    public TableAdapter(Context mContext, ArrayList<Table> tableList) {
        this.mContext = mContext;
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Table table = tableList.get(position);
        holder.textView.setText(tableList.get(position).getName());

        Glide.with(mContext)
                .load(tableList.get(position).getImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(),UDTableActivity.class);
                i.putExtra("Name",table.getName().toString());
                i.putExtra("Image",table.getImageUrl());
                i.putExtra("id",table.getID());

                view.getContext().startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        ConstraintLayout layout;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.codeImage);
            textView = itemView.findViewById(R.id.tableName);
            layout = itemView.findViewById(R.id.table_Layout);
        }
    }
}
