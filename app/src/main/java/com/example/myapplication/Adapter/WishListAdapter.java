package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.WishListModel;
import com.example.myapplication.R;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    Context context;
    List<WishListModel> list;

    public WishListAdapter(Context context, List<WishListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ProductNameTv.setText(list.get(position).getProduct_name());
        holder.product_mrp_digit.setText(list.get(position).getProduct_mrp_digit());
        holder.productIv.setImageResource(list.get(position).getProduct_image());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ProductNameTv,ProductPriceTv,product_mrp_digit;
        ImageView productIv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductNameTv = itemView.findViewById(R.id.ProductNameTv);
            ProductPriceTv = itemView.findViewById(R.id.ProductPriceTv);
            product_mrp_digit = itemView.findViewById(R.id.product_mrp_digit);
            productIv = itemView.findViewById(R.id.productIv);
        }
    }
}
