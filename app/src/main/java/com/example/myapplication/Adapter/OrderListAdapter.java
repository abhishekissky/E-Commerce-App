package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.OrderListModel;
import com.example.myapplication.R;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    Context context;
    List<OrderListModel> orderListModels;

    public OrderListAdapter(Context context, List<OrderListModel> orderListModels) {
        this.context = context;
        this.orderListModels = orderListModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_orders,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(orderListModels.get(position).getProduct_name());
        holder.ProductQtyUnit.setText(orderListModels.get(position).getProductUnit());
        holder.product_mrp_digit.setText(orderListModels.get(position).getProductMrp());
        holder.offer_price.setText(orderListModels.get(position).getOfferPrice());
        holder.saveRupeeDigit.setText(orderListModels.get(position).getDiscountPrice());
        holder.saveRupeePercent.setText(orderListModels.get(position).getDiscountPercentage());
    }

    @Override
    public int getItemCount() {
        return orderListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,ProductQty,saveRupeePercent,product_mrp_digit,offer_price,ProductQtyUnit,saveRupeeDigit;
        ImageView productIv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.productIv);
            productName = itemView.findViewById(R.id.productName);
            ProductQtyUnit = itemView.findViewById(R.id.ProductQtyUnit);
            product_mrp_digit = itemView.findViewById(R.id.product_mrp_digit);
            offer_price = itemView.findViewById(R.id.offer_price);
            saveRupeeDigit = itemView.findViewById(R.id.saveRupeeDigit);
            saveRupeePercent = itemView.findViewById(R.id.saveRupeePercent);
        }
    }
}
