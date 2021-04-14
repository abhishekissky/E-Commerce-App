package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.ProductViewActivity;
import com.example.myapplication.Models.ProductViewListModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListViewAdapter extends RecyclerView.Adapter<ProductListViewAdapter.ProductViewHolder> {


    Context context;
    List<ProductViewListModel> stringList;
    ProgressBar progressBar;
    int discountPercentageInt,discount,productPrice;
    String discountPercentageString,discountRupeeString;


    public ProductListViewAdapter(Context context, List<ProductViewListModel> stringList,ProgressBar progressBar) {
        this.context = context;
        this.stringList = stringList;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public ProductListViewAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_list_view_layout,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Log.v("image_url",stringList.get(position).getImage_url().toString());
        List<String> imageUrl =  stringList.get(position).getImage_url();
        Glide.with(context).load(imageUrl.get(0)).into(holder.productIv);




        holder.ProductNameTv.setText(stringList.get(position).getProduct_name());
        holder.product_mrp_digit.setText(stringList.get(position).getProduct_price());
        holder.ProductUnit.setText(stringList.get(position).getQuantity_unit());
        String discountUnit = stringList.get(position).getDiscountUnit();
        productPrice  = Integer.parseInt(stringList.get(position).getProduct_price());
        if (discountUnit.equals("₹")){
            holder.saveRupeeDigit.setText(stringList.get(position).getDiscount());
            discount = Integer.parseInt(stringList.get(position).getDiscount());
            discountRupeeString = String.valueOf(discount);
            discountPercentageInt = (discount*100)/productPrice;
            discountPercentageString = String.valueOf(discountPercentageInt);
            holder.saveRupeePercent.setText(discountPercentageString);
            Log.v("discount",stringList.get(position).getDiscountUnit());

        }else if (discountUnit.equals("%")){
            discountPercentageInt = Integer.parseInt(stringList.get(position).getDiscount());
            discountPercentageString = String.valueOf(discountPercentageInt);
            discount = (productPrice*discountPercentageInt)/100;
            discountRupeeString = String.valueOf(discount);
            holder.saveRupeePercent.setText(stringList.get(position).getDiscount());
            holder.saveRupeeDigit.setText(discountRupeeString);
            holder.saveRupeePercentLayout.setVisibility(View.VISIBLE);
        }
        String offerPrice = String.valueOf(productPrice-discount);
        holder.offer_price.setText(offerPrice);

        progressBar.setVisibility(View.GONE);
        Log.v("image_url1",imageUrl.get(0));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductViewActivity.class);
                intent.putExtra("ProductNameTv",stringList.get(position).getProduct_name());
                intent.putExtra("ProductUnit",stringList.get(position).getQuantity_unit());
                intent.putExtra("offer_price",offerPrice);
                intent.putExtra("discountPercentageString",discountPercentageString);
                intent.putExtra("discountRupeeString",discountRupeeString);
                intent.putExtra("product_mrp_digit",stringList.get(position).getProduct_price());
                intent.putExtra("uid",stringList.get(position).getUid());
                intent.putExtra("cod",stringList.get(position).getCod());
                intent.putExtra("gst",stringList.get(position).getGst());
                intent.putExtra("discount_unit",stringList.get(position).getDiscountUnit());
                intent.putExtra("dispatch_in",stringList.get(position).getDispatch_in());
                intent.putStringArrayListExtra("image_url", (ArrayList<String>) stringList.get(position).getImage_url());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView ProductQty,ProductNameTv,offer_price,ProductUnit,saveRupeeDigit,saveRupeePercent,product_mrp_digit;
        LinearLayout saveRupeePercentLayout ;
        ImageView productIv;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
//            ProductQty = itemView.findViewById(R.id.ProductQty);
            productIv = itemView.findViewById(R.id.productIv);
            ProductNameTv = itemView.findViewById(R.id.ProductNameTv);
            offer_price = itemView.findViewById(R.id.offer_price);
            ProductUnit = itemView.findViewById(R.id.ProductUnit);
            saveRupeeDigit = itemView.findViewById(R.id.saveRupeeDigit);
            saveRupeePercent = itemView.findViewById(R.id.saveRupeePercent);
            product_mrp_digit = itemView.findViewById(R.id.product_mrp_digit);
            saveRupeePercentLayout = itemView.findViewById(R.id.saveRupeePercentLayout);
            product_mrp_digit.setPaintFlags(product_mrp_digit.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
