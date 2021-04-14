package com.example.myapplication.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.ProductImageAdapter;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductViewActivity extends AppCompatActivity {
    int[] defaultImage = {R.drawable.pears,R.drawable.ponet,R.drawable.ic_fruits,R.drawable.kiwi};
    ProductImageAdapter productImageAdapter;
    ViewPager viewPager;
    TextView product_mrp,ProductName,productPrice,product_qty_i_D,ProductQty,product_qty_de,product_qty_In,sellerName;
    TextView InclusiveAllTax,productPricePerUnit,dispatchIn,priceUnit,discountPercentage,discountPrice,ProductUnit;
    Button add_to_cardBtn,wishList;
    ImageView back_img;
    Intent intent;
    String uid;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ArrayList<String> imageUrl;
    int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        intent = getIntent();

        init();
        getData();
        setName();
        setImageAdapter();

    }

    private void setImageAdapter() {
        productImageAdapter = new ProductImageAdapter(defaultImage,this,imageUrl);
        viewPager.setAdapter(productImageAdapter);
    }

    private void init() {

        viewPager = findViewById(R.id.viewPager);
        product_mrp= findViewById(R.id.product_mrp);
        ProductName = findViewById(R.id.ProductName);
        productPrice = findViewById(R.id.productPrice);
        product_qty_i_D = findViewById(R.id.product_qty_i_D);
        ProductQty = findViewById(R.id.ProductQty);
        add_to_cardBtn = findViewById(R.id.add_to_cardBtn);
        product_qty_de = findViewById(R.id.product_qty_de);
        product_qty_In = findViewById(R.id.product_qty_In);
        sellerName = findViewById(R.id.sellerName);
        InclusiveAllTax = findViewById(R.id.InclusiveAllTax);
        wishList = findViewById(R.id.wishList);
        progressBar = findViewById(R.id.progressBar);
        productPricePerUnit = findViewById(R.id.productPricePerUnit);
        priceUnit = findViewById(R.id.priceUnit);
        discountPercentage = findViewById(R.id.discountPercentage);
        discountPrice = findViewById(R.id.discountPrice);
        ProductUnit = findViewById(R.id.ProductUnit);
        dispatchIn = findViewById(R.id.dispatchIn);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        product_mrp.setPaintFlags(product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_to_cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser==null){
                    Intent intent = new Intent(ProductViewActivity.this,SignUpActivity.class);
                    intent.putExtra("token","1");
                    startActivity(intent);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    getUserData(1);

                }
            }
        });

        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser==null){
                    Intent intent = new Intent(ProductViewActivity.this,SignUpActivity.class);
                    intent.putExtra("seller_uid",uid);
                    startActivity(intent);
                }else {
//                    Toast.makeText(ProductViewActivity.this, "You are Login", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    getUserData(0);
                }
            }
        });

        product_qty_de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count= Integer.parseInt(String.valueOf(product_qty_i_D.getText()));
                if (count==1){
                    product_qty_i_D.setText("1");

                }else {
                    count -= 1;
                    product_qty_i_D.setText("" + count);
                }
            }
        });

        product_qty_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count= Integer.parseInt(String.valueOf(product_qty_i_D.getText()));
                count++;
                product_qty_i_D.setText("" + count);
            }
        });

    }

    private void getData() {

        product_mrp.setText(intent.getStringExtra("product_mrp_digit"));
        ProductName.setText(intent.getStringExtra("ProductNameTv"));
        productPrice.setText(intent.getStringExtra("offer_price"));
//        ProductQty.setText(intent.getStringExtra("ProductQty"));
        InclusiveAllTax.setText(intent.getStringExtra("gst"));
        ProductUnit.setText(intent.getStringExtra("ProductUnit"));
        productPricePerUnit.setText(intent.getStringExtra("offer_price"));
//        discountPercentage.setText(intent.getStringExtra("offer_price"));
        priceUnit.setText(intent.getStringExtra("ProductUnit"));
        discountPrice.setText(intent.getStringExtra("discountRupeeString"));
        discountPercentage.setText(intent.getStringExtra("discountPercentageString"));
        dispatchIn.setText(intent.getStringExtra("dispatch_in"));

        if (firebaseUser!=null) {
            getTotalPrice();
        }
        imageUrl= (ArrayList<String>) intent.getSerializableExtra("image_url");
        Log.v("Image Url productView",imageUrl.toString());

        String cod = intent.getStringExtra("cod");
        uid = intent.getStringExtra("uid");
        Log.v("Uid ProductView",uid);
    }


    private void getTotalPrice() {

        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("CardTotalPrice")
                .document("totalPrice")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            totalPrice = Integer.valueOf( documentSnapshot.getData().get("totalPrice").toString());
                        }
                    }
                });

    }

    private void addTotalPrice() {
        int qty = Integer.valueOf(product_qty_i_D.getText().toString());
        int offerPrice = Integer.valueOf(productPrice.getText().toString());
        int totalPrice1 = offerPrice*qty;
        String totalPriceS = String.valueOf(totalPrice1+totalPrice);
        Map<String, Object> data = new HashMap<>();
        data.put("totalPrice",totalPriceS);


        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("CardTotalPrice")
                .document("totalPrice")
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v("Total Price","Fetch successfully");
                    }
                });
    }

    private void addToCard() {

        String product_name = ProductName.getText().toString();
        String productMrp = product_mrp.getText().toString();
        String product_qty = ProductQty.getText().toString();
        String product_qty_i_d = product_qty_i_D.getText().toString();
        String gst = InclusiveAllTax.getText().toString();
        String seller = sellerName.getText().toString();
        String offerPrice = intent.getStringExtra("offer_price");
        String productUnit = intent.getStringExtra("ProductUnit");
        String discountPrice = intent.getStringExtra("discountRupeeString");
        String discountPercentage = intent.getStringExtra("discountPercentageString");
        String dispatchIn = intent.getStringExtra("dispatch_in");

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
                        Toast.makeText(ProductViewActivity.this, "Product Added.", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(AddProductActivity.this,Home.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductViewActivity.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ProductViewActivity.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
//
                    }
                });
    }

    private void setName() {
        DocumentReference docRef = firebaseFirestore.collection("Users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name =documentSnapshot.getData().get("Name").toString();
                Log.v("Name",documentSnapshot.getData().get("Name").toString());
                sellerName.setText(name);
            }
        });

    }

    private void saveInWishList() {
        String product_name = ProductName.getText().toString();
        String productMrp = product_mrp.getText().toString();
        String product_qty = ProductQty.getText().toString();
        String product_qty_i_d = product_qty_i_D.getText().toString();
        String gst = InclusiveAllTax.getText().toString();
        String seller = sellerName.getText().toString();
        String offerPrice = intent.getStringExtra("offer_price");
        String productUnit = intent.getStringExtra("ProductUnit");
        String discountPrice = intent.getStringExtra("discountRupeeString");
        String discountPercentage = intent.getStringExtra("discountPercentageString");
        String dispatchIn = intent.getStringExtra("dispatch_in");

        Map<String, Object> add = new HashMap<>();
        add.put("product_name", product_name);
//        add.put("product_qty_i_d",product_qty_i_d);
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
                .document(firebaseUser.getUid()).collection("wish_list")
                .add(add)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProductViewActivity.this, "Product Added.", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(AddProductActivity.this,Home.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductViewActivity.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ProductViewActivity.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
//
                    }
                });
    }

    private void getUserData(int acces){
        MainActivity mainActivity = new MainActivity();
        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if(!documentSnapshot.exists()){
                            Intent intent = new Intent(ProductViewActivity.this,SignInDataActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            if (acces==1) {
                                addTotalPrice();
                                addToCard();
                            }else {
                                saveInWishList();

                            }
                        }
//                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }

}