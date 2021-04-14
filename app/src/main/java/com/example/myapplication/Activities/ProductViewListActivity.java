package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ProductListViewAdapter;
import com.example.myapplication.Models.ProductViewListModel;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductViewListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ProductListViewAdapter productListViewAdapter;
    List<ProductViewListModel> productList;
    List<String>sUid;
    Intent intent;
    String category,cat;
    FirestoreRecyclerOptions<ProductViewListModel> allProducts;
    ProgressBar progressBar;
    ImageView back_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view_list);
        recyclerView = findViewById(R.id.productView_list_recycleView);
        progressBar = findViewById(R.id.progressBar);
        back_img = findViewById(R.id.back_img);
        intent = getIntent();
        category = intent.getStringExtra("category");
        getSellerUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();
//        sUid = new ArrayList<>();
//        uid = getIntent().getStringArrayListExtra("uid");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getSellerUid() {

        progressBar.setVisibility(View.VISIBLE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        sUid = new ArrayList<>();

        Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
//                            Log.v("Data Get", String.valueOf(querySnapshot));
//                            Log.v("Data MGet", String.valueOf(querySnapshot.get("Address")));
                            sUid.add(String.valueOf(querySnapshot.get("uid")));
                            Log.v("list", String.valueOf(sUid));
                        }
                            if (category.equals("grain")){
                                cat = "Grain";
                            }else if (category.equals("beverage")){
                                cat = "Beverages";
                            }else if (category.equals("bakery")){
                                cat = "Bakery";
                            }else if (category.equals("fruit")){
                                cat = "Fruits";
                            }else if (category.equals("snake")){
                                cat = "Snakes";
                            }else if (category.equals("vegetable")){
                                cat = "Vegetables";
                            }
                        getData(cat);


                    }
                });
    }

    private void getData(String cat) {


        for (int i=0;i<sUid.size();i++) {

            firebaseFirestore.collection("Users")
                    .document(sUid.get(i))
                    .collection("user_products_details")
                    .whereEqualTo("product_category", cat)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()){
                                for (QueryDocumentSnapshot allProduct:queryDocumentSnapshots){
                                    ProductViewListModel productViewListModel = allProduct.toObject(ProductViewListModel.class);
                                    productList.add(productViewListModel);
                                    Log.v("pr",productList.toString());
                                }
                                productListViewAdapter = new ProductListViewAdapter(ProductViewListActivity.this, productList,progressBar);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ProductViewListActivity.this));
                                recyclerView.setAdapter(productListViewAdapter);

                            }else {
                                progressBar.setVisibility(View.GONE);
                            }


                        }
                    });

        }
        progressBar.setVisibility(View.GONE);
    }


}