//package com.example.myapplication.Activities;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.myapplication.Adapter.AddressAdapter;
//import com.example.myapplication.Models.AddressModel;
//import com.example.myapplication.databinding.ActivitySelectAddressBinding;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//
//public class SelectAddressActivity extends AppCompatActivity {
//    ActivitySelectAddressBinding binding;
//
//    FirebaseUser firebaseUser;
//    FirebaseFirestore firebaseFirestore;
//    AddressAdapter addressAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivitySelectAddressBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        getDefaultAddress();
//
//        Query query = firebaseFirestore.collection("Customer").document(firebaseUser.getUid()).collection("address");
//
//        FirestoreRecyclerOptions<AddressModel> allProducts = new FirestoreRecyclerOptions.Builder<AddressModel>()
//                .setQuery(query,AddressModel.class)
//                .build();
//
//        addressAdapter = new AddressAdapter(allProducts,this,);
//        binding.addressRecycleView.setHasFixedSize(true);
//        binding.addressRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        binding.addressRecycleView.setAdapter(addressAdapter);
//
//        Log.v("id",addressAdapter.id);
//    }
//
//    private void getDefaultAddress() {
//
//        firebaseFirestore.collection("Customer")
//                .document(firebaseUser.getUid())
//                .collection("address")
//                .document("defaultAddress")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()){
//                            String address = documentSnapshot.getData().get("address") +","
//                                    + documentSnapshot.getData().get("landmark")+","
//                                    + documentSnapshot.getData().get("city")+","
//                                    + documentSnapshot.getData().get("state")+","
//                                    + documentSnapshot.getData().get("country")+","
//                                    +documentSnapshot.getData().get("pinCode").toString();
//
//                            binding.customerAddress.setText(address);
//                            String mobile = documentSnapshot.getData().get("mobile_no").toString();
//                            binding.mobileNumber.setText(mobile);
//                            binding.defaultAddressCard.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        addressAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        addressAdapter.stopListening();
//    }
//}