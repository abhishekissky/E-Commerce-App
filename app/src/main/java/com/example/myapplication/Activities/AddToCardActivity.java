package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AddToCardAdapter;
import com.example.myapplication.Models.AddToCardModel;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class AddToCardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    AddToCardAdapter addToCardAdapter;
    Button placeOrder;
    public TextView totalPrice;
    int totalP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_card);
        recyclerView = findViewById(R.id.add_to_card_recycleView);
        progressBar = findViewById(R.id.progressBar);
        placeOrder = findViewById(R.id.placeOrder);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        totalPrice = findViewById(R.id.totalPrice);

        if(firebaseUser!=null) {
            getUserData();
            getTotalPrice();
            Query query = firebaseFirestore.collection("Customer").document(firebaseUser.getUid()).collection("card");
            FirestoreRecyclerOptions<AddToCardModel> allProducts = new FirestoreRecyclerOptions.Builder<AddToCardModel>()
                    .setQuery(query,AddToCardModel.class)
                    .build();
            addToCardAdapter = new AddToCardAdapter(allProducts,this,progressBar,firebaseFirestore,firebaseUser,totalPrice);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(addToCardAdapter);
        }else {
            startActivity(new Intent(AddToCardActivity.this,SignUpActivity.class));
            finish();
        }





        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(AddToCardActivity.this, GetLocationActivity.class));

                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(AddToCardActivity.this,CheckOutActivity.class);
                intent.putExtra("totalAmount",String.valueOf(totalP));
                intent.putExtra("token","0");
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
//                    getData();
            }
        });

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
                        if (documentSnapshot.exists()) {
                            totalPrice.setText(documentSnapshot.getData().get("totalPrice").toString());
                            totalP = Integer.valueOf(documentSnapshot.getData().get("totalPrice").toString());


                        }
                    }
                });
    }

    private void getData() {

        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("address")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()){
                            finish();

                            Intent intent = new Intent(AddToCardActivity.this,AddressActivity.class);
                            intent.putExtra("totalAmount",String.valueOf(totalP));
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                        }else if (!queryDocumentSnapshots.isEmpty()){
//                                    startActivity(new Intent(AddToCardActivity.this, SelectAddressActivity.class));
                            Intent intent = new Intent(AddToCardActivity.this,CheckOutActivity.class);
                            intent.putExtra("totalAmount",String.valueOf(totalP));
                            intent.putExtra("token","0");
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser!=null) {
            addToCardAdapter.startListening();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseUser!=null) {
            addToCardAdapter.stopListening();
        }

    }

    private void getUserData(){
        progressBar.setVisibility(View.VISIBLE);
        MainActivity mainActivity = new MainActivity();
        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if(!documentSnapshot.exists()){
                            Intent intent = new Intent(AddToCardActivity.this,SignInDataActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}