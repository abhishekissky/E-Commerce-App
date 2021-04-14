package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.AddToCardModel;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddToCardAdapter extends FirestoreRecyclerAdapter<AddToCardModel,AddToCardAdapter.ViewHolder> {

//    public int totalP;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser ;
    Context context;
    ProgressBar progressBar;
    TextView totalPriceTxt;
    String totalCount;
    int totalPrice,count=1;




    public AddToCardAdapter(@NonNull FirestoreRecyclerOptions<AddToCardModel> options, Context context, ProgressBar progressBar, FirebaseFirestore firebaseFirestore, FirebaseUser firebaseUser , TextView totalPriceTxt) {
        super(options);
        this.context=context;
        this.progressBar = progressBar;
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseUser = firebaseUser;

        this.totalPriceTxt=totalPriceTxt;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull AddToCardModel model) {
            String docId = this.getSnapshots().getSnapshot(position).getId();
            List<String> imageUrl = model.getImageUrl();
            Log.v("Image Url",imageUrl.toString());
            Glide.with(context).load(imageUrl.get(0)).into(holder.productIv);
            holder.ProductNameTv.setText(model.getProduct_name());
            holder.ProductPriceTv.setText(model.getProductMrp());
            holder.ProductQtyUnit.setText(model.getProductUnit());
            holder.product_qty.setText(model.getProduct_qty_i_d());
//            totalP+= Integer.parseInt(model.getOfferPrice());
//            totalPrice.setText(String.valueOf(totalP));
//            totalPrice.setText(model.getTotalPrice());

            if (count==1){
                progressBar.setVisibility(View.VISIBLE);
                firebaseFirestore.collection("Customer")
                        .document(firebaseUser.getUid())
                        .collection("CardTotalPrice")
                        .document("totalPrice")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    totalPrice = Integer.valueOf(documentSnapshot.getData().get("totalPrice").toString());
                                    Log.v("total Prod", String.valueOf(totalPrice));
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                count++;
            }

            progressBar.setVisibility(View.GONE);

            holder.product_qty_In.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int offerPrice = Integer.valueOf(model.getOfferPrice());
                    int count= Integer.parseInt(String.valueOf(holder.product_qty.getText()));
                    count++;
                    totalCount = String.valueOf(Integer.valueOf(totalPriceTxt.getText().toString())+offerPrice);
                    increaseDecreaseQty(docId,count,totalCount);
                    holder.product_qty.setText("" + count);
                }
            });

            holder.product_qty_De.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    int offerPrice = Integer.valueOf(model.getOfferPrice());
                    int count= Integer.parseInt(String.valueOf(holder.product_qty.getText()));
                    if (count==1){
                        holder.product_qty.setText("1");
                        totalCount = String.valueOf(totalPrice);
                        increaseDecreaseQty(docId,count,totalCount);
                    }else {
                        count -= 1;

                        totalCount = String.valueOf(Integer.valueOf(totalPriceTxt.getText().toString())-offerPrice);
                        increaseDecreaseQty(docId,count,totalCount);
                        holder.product_qty.setText("" + count);
                    }
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                    subtractTotalPrice(model.getOfferPrice(),model.getProduct_qty_i_d(),docId);
                }
            });
    }

    private void subtractTotalPrice(String offerPrice,String qty,String docId) {
        int totalPrice1 = totalPrice-Integer.valueOf(offerPrice)*Integer.valueOf(qty);
        String totalP = String.valueOf(totalPrice1);
        Map<String, Object> data = new HashMap<>();
        data.put("totalPrice",totalP);


        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("CardTotalPrice")
                .document("totalPrice")
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v("Total Price","Fetch successfully");
                        totalPriceTxt.setText(totalP);

                        firebaseFirestore.collection("Customer")
                                .document(firebaseUser.getUid())
                                .collection("card")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(context, "Product Deleted Form Card", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });



    }

    private void increaseDecreaseQty(String docId, int count, String totalCount) {
        String qty = String.valueOf(count);
        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("card")
                .document(docId)
                .update("product_qty_i_d",qty)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        totalPriceTxt.setText(totalCount);
                        Map<String, Object> data = new HashMap<>();
                        data.put("totalPrice",totalCount);
                        firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                                .collection("CardTotalPrice").document("totalPrice")
                                .set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });

                    }
                });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_to_card_layout,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_qty,ProductNameTv,ProductPriceTv,ProductQty,ProductQtyUnit,product_qty_In,product_qty_De;
        ImageView delete,productIv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductNameTv = itemView.findViewById(R.id.ProductNameTv);
            ProductPriceTv = itemView.findViewById(R.id.ProductPriceTv);
            ProductQtyUnit = itemView.findViewById(R.id.ProductQtyUnit);
            product_qty_In = itemView.findViewById(R.id.product_qty_In);
            product_qty = itemView.findViewById(R.id.product_qty);
            product_qty_De =  itemView.findViewById(R.id.product_qty_De);
            ProductQty = itemView.findViewById(R.id.ProductQty);
            delete = itemView.findViewById(R.id.delete);
            productIv  = itemView.findViewById(R.id.productIv);
        }
    }
}
