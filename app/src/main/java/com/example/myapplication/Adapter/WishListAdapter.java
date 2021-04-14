package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.ProductViewActivity;
import com.example.myapplication.Models.WishListModel;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishListAdapter extends FirestoreRecyclerAdapter<WishListModel,WishListAdapter.ViewHolder> {

    public int check;
    Context context;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    TextView plzAdPro;


    public WishListAdapter(@NonNull FirestoreRecyclerOptions<WishListModel> options,Context context,ProgressBar progressBar,FirebaseFirestore firebaseFirestore,FirebaseUser firebaseUser,TextView plzAdPro) {
        super(options);
        this.progressBar=progressBar;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseUser = firebaseUser;
        this.plzAdPro = plzAdPro;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull WishListModel model) {
            String docId = this.getSnapshots().getSnapshot(position).getId();
            List<String > imageUrl = model.getImageUrl();

            Glide.with(context).load(imageUrl.get(0)).into(holder.productIv);

            holder.ProductNameTv.setText(model.getProduct_name());
            holder.ProductQtyUnit.setText(model.getProductUnit());
            holder.product_mrp_digit.setText(model.getProductMrp());
            holder.offer_price.setText(model.getOfferPrice());
            holder.saveRupeeDigit.setText(model.getDiscountPrice());
            holder.saveRupeePercent.setText(model.getDiscountPercentage());
            progressBar.setVisibility(View.GONE);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductViewActivity.class);
                    intent.putExtra("ProductNameTv",model.getProduct_name());
                    intent.putExtra("ProductUnit",model.getProductUnit());
                    intent.putExtra("offer_price",model.getOfferPrice());
                    intent.putExtra("discountPercentageString",model.getDiscountPercentage());
                    intent.putExtra("discountRupeeString",model.getDiscountPrice());
                    intent.putExtra("product_mrp_digit",model.getProductMrp());
                    intent.putExtra("uid",model.getSeller_uid());
                    intent.putExtra("cod",model.getCod());
                    intent.putExtra("gst",model.getGst());
//                    intent.putExtra("discount_unit",stringList.get(position).getDiscountUnit());
                    intent.putExtra("dispatch_in",model.getDispatchIn());
                    intent.putStringArrayListExtra("image_url", (ArrayList<String>) model.getImageUrl());

                    context.startActivity(intent);
                }
            });

            holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseFirestore.collection("Customer")
                            .document(firebaseUser.getUid())
                            .collection("wish_list")
                            .document(docId)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(context, "Product Deleted Form WishList", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            holder.addToCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    String product_name = model.getProduct_name();
                    String productMrp = model.getProductMrp();
//                    String product_qty = ProductQty.getText().toString();
                    String product_qty_i_d = "1";
                    String gst = model.getGst();
//                    String seller = sellerName.getText().toString();
                    String offerPrice = model.getOfferPrice();
                    String productUnit = model.getProductUnit();
                    String discountPrice = model.getDiscountPrice();
                    String discountPercentage = model.getDiscountPercentage();
                    String dispatchIn = model.getDispatchIn();
                    String uid = model.getSeller_uid();

                    Map<String, Object> add = new HashMap<>();
                    add.put("product_name", product_name);
                    add.put("product_qty_i_d",product_qty_i_d);
//        add.put("product_qty",product_qty);
                    add.put("productMrp",productMrp);
                    add.put("gst",gst);
                    add.put("seller_uid", uid);
                    add.put("offerPrice", offerPrice);
                    add.put("discountPrice", discountPrice);
                    add.put("discountPercentage", discountPercentage);
                    add.put("dispatchIn", dispatchIn);
                    add.put("productUnit", productUnit);
                    add.put("imageUrl", imageUrl);

                    firebaseFirestore.collection("Customer")
                            .document(firebaseUser.getUid()).collection("card")
                            .add(add)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(context, "Product Added.", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(AddProductActivity.this,Home.class));

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Error, Try again.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(context, "Error, Try again.", Toast.LENGTH_SHORT).show();
//
                                }
                            });
                }
            });
            if (model.getProduct_name()==null){
                plzAdPro.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_layout,parent,false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ProductNameTv,ProductQty,deleteProduct,saveRupeePercent,product_mrp_digit,offer_price,ProductQtyUnit,saveRupeeDigit;
        ImageView productIv;
        Button addToCardBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //            ProductQty = itemView.findViewById(R.id.ProductQty);
            productIv = itemView.findViewById(R.id.productIv);
            ProductNameTv = itemView.findViewById(R.id.ProductNameTv);
            ProductQtyUnit = itemView.findViewById(R.id.ProductQtyUnit);
            product_mrp_digit = itemView.findViewById(R.id.product_mrp_digit);
            offer_price = itemView.findViewById(R.id.offer_price);
            saveRupeeDigit = itemView.findViewById(R.id.saveRupeeDigit);
            saveRupeePercent = itemView.findViewById(R.id.saveRupeePercent);
            deleteProduct = itemView.findViewById(R.id.deleteProduct);
            addToCardBtn = itemView.findViewById(R.id.addToCardBtn);

        }
    }
}
