package com.example.myrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Models.ChangeNumberItemsListener;
import Models.ManagementCard;
import Models.MenuItemDetails;
import Models.MyBag;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHoder>{

    //private Context mContext;
    private ArrayList<MenuItemDetails> menuItemList;
    private ChangeNumberItemsListener changeNumberItemsListener;
    private ManagementCard managementCard;
    public OrderAdapter()
    {

    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_card,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {

        holder.title.setText(menuItemList.get(position).getName());
        holder.priceView.setText(String.valueOf(menuItemList.get(position).getPrice()));
        holder.totalPrice.setText(String.valueOf(Math.round((menuItemList.get(position).getQuantity() * menuItemList.get(position).getPrice()))));
        holder.quantity.setText(String.valueOf(menuItemList.get(position).getQuantity()));


        Glide.with(holder.itemView.getContext())
                .load(menuItemList.get(position).getImageUrl())
                .into(holder.imageView);
        holder.plusCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCard.plusNumberFood(menuItemList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });

            }
        });
        holder.minusCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCard.MinusNumerFood(menuItemList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public OrderAdapter(Context mContext, ArrayList<MenuItemDetails> menuItemList,ChangeNumberItemsListener changeNumberItemsListener) {
        this.menuItemList = menuItemList;
        this.changeNumberItemsListener=changeNumberItemsListener;
        this.managementCard = new ManagementCard(mContext);
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView,plusCardBtn,minusCardBtn;
        TextView title, priceView,totalPrice,quantity;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.picCard);
            title = itemView.findViewById(R.id.title2Txt);
            priceView = itemView.findViewById(R.id.feeEachItem);
            totalPrice = itemView.findViewById(R.id.totalEachItem);
            quantity = itemView.findViewById(R.id.numberItemTxt);

            plusCardBtn = itemView.findViewById(R.id.plusCardBtn);
            minusCardBtn = itemView.findViewById(R.id.minusCardBtn);

        }
    }
}
