package com.example.myrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Models.Orders;
import Models.Table;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHoder>{

    private Context mContext;
    private ArrayList<Orders> orderList;

    public ReservationAdapter(){}

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        String total = Double.toString(orderList.get(position).getTotal());
        String TableId = orderList.get(position).getID();
        holder.orderId.setText(TableId);
        holder.totalPrice.setText(total);
        /*if(tableList.get(position).getStatus().equals("free"))
        {
            holder.layout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.green_category_background));
        }
        else if(tableList.get(position).getStatus().equals("busy"))
        {
            holder.layout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.red_category_background));
        }
        else if(tableList.get(position).getStatus().equals("waiting"))
        {
            holder.layout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.yellow_category_background));
        }*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),ManageReservation_Activity.class);
                i.putExtra("TableID",TableId);
                view.getContext().startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public ReservationAdapter(Context mContext, ArrayList<Orders> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView totalPrice, orderId;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            totalPrice = itemView.findViewById(R.id.txtTotalPrice);
            orderId = itemView.findViewById(R.id.OrderIDTxt);
        }
    }
}
