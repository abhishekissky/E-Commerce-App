package com.example.myapplication.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activities.AddressActivity;
import com.example.myapplication.Activities.CheckOutActivity;
import com.example.myapplication.Adapter.AddressAdapter;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSelectAddressBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class selectAddressFragment extends Fragment {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    AddressAdapter addressAdapter;
    String totalAmount;
    Intent intent;
    String name,mobileNumber,home,address,landmark,city,state,country,pinCode,alternateMobile;
    TextView topText;
    String defaultAdd;
    ImageView addressImg;
    public selectAddressFragment() {
        // Required empty public constructor
    }

   FragmentSelectAddressBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectAddressBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        defaultAdd = binding.defaultAddress.getText().toString();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        totalAmount= getArguments().getString("totalAmount");
        binding.totalAmount.setText(totalAmount);

        topText = getActivity().findViewById(R.id.addressTxt);
        topText.setTextColor(getResources().getColor(R.color.black));
        addressImg = getActivity().findViewById(R.id.addressImg);
        addressImg.setImageResource(R.drawable.ic_baseline_navigate_next_white_24);
        addressImg.setBackgroundResource(R.drawable.green_oval_background);

        getDefaultAddress(0);
        addNewAddress();

        Query query = firebaseFirestore.collection("Customer").document(firebaseUser.getUid()).collection("address");

        FirestoreRecyclerOptions<AddressModel> allProducts = new FirestoreRecyclerOptions.Builder<AddressModel>()
                .setQuery(query,AddressModel.class)
                .build();

        addressAdapter = new AddressAdapter(allProducts,getContext(),totalAmount);
        binding.addressRecycleView.setHasFixedSize(true);
        binding.addressRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.addressRecycleView.setAdapter(addressAdapter);

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (addressAdapter.id==null){

                    getDefaultAddress(1);

                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("docId", addressAdapter.id);
                    bundle.putString("totalAmount", totalAmount);
                    bundle.putString("address", addressAdapter.destination);
                    bundle.putString("name", addressAdapter.name);
                    PaymentFragment paymentFragment = new PaymentFragment();
                    paymentFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragmentLayout, paymentFragment).commit();
                }
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDefaultAddress();
            }
        });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAddress();
            }
        });
        return view;
    }

    private void editAddress() {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("mobileNu",mobileNumber);
        intent.putExtra("alternateMoNu",alternateMobile);
        intent.putExtra("address",address);
        intent.putExtra("landmark",landmark);
        intent.putExtra("city",city);
        intent.putExtra("state",state);
        intent.putExtra("country",country);
        intent.putExtra("pinCode",pinCode);
        intent.putExtra("addressType",home);
        intent.putExtra("accessCode","11");
        intent.putExtra("defaultAddress",defaultAdd);
        intent.putExtra("token","1");
        intent.putExtra("totalAmount", totalAmount);
        startActivity(intent);
    }

    private void deleteDefaultAddress() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Delete Alert");
        alertDialogBuilder.setMessage("Are you sure you want to delete this address");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        deleteFromDatabase();

                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteFromDatabase() {
        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("defaultAddress")
                .document("defaultAddress")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Address Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), CheckOutActivity.class);
                        intent.putExtra("totalAmount",totalAmount);
                        intent.putExtra("token","1");
                        startActivity(intent);
                    }
                });

    }

    private void addNewAddress() {
        binding.addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                intent.putExtra("totalAmount", totalAmount);
                intent.putExtra("token","1");
                startActivity(intent);
            }
        });
    }

    private void getDefaultAddress(int i) {

        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("defaultAddress")
                .document("defaultAddress")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (i==0){
                            if (documentSnapshot.exists()){


                                address = documentSnapshot.getData().get("address").toString();
                                landmark = documentSnapshot.getData().get("landmark").toString();
                                city = documentSnapshot.getData().get("city").toString();
                                state = documentSnapshot.getData().get("state").toString();
                                country = documentSnapshot.getData().get("country").toString();
                                pinCode = documentSnapshot.getData().get("pinCode").toString();


                                String addressW = documentSnapshot.getData().get("address") + ","
                                        + documentSnapshot.getData().get("landmark") + ","
                                        + documentSnapshot.getData().get("city") + ","
                                        + documentSnapshot.getData().get("state") + ","
                                        + documentSnapshot.getData().get("country") + ","
                                        + documentSnapshot.getData().get("pinCode").toString();

                                alternateMobile = documentSnapshot.getData().get("alternate_mobile_no").toString();

                                binding.customerAddress.setText(addressW);
                                name = documentSnapshot.getData().get("name").toString();

                                binding.customerName.setText(name);
                                mobileNumber = documentSnapshot.getData().get("mobile_no").toString();
                                binding.mobileNumber.setText(mobileNumber);
                                home = documentSnapshot.getData().get("address_type").toString();
                                binding.home.setText(home);
                                binding.defaultAddressCard.setVisibility(View.VISIBLE);
                            }
                        }else if (i==1){
                            if (!documentSnapshot.exists()){
                                Toast.makeText(getContext(), "Please Select Address", Toast.LENGTH_SHORT).show();
                            }else {
                                Bundle bundle = new Bundle();
                                bundle.putString("docId","defaultAddress");
                                bundle.putString("totalAmount", totalAmount);
                                bundle.putString("address", addressAdapter.destination);
                                bundle.putString("name", addressAdapter.name);
                                PaymentFragment paymentFragment = new PaymentFragment();
                                paymentFragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().add(paymentFragment,"Payment").replace(R.id.fragmentLayout, paymentFragment).commit();
//                                topText.setBackgroundColor(getResources().getColor(R.color.white));

                            }
                        }
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        addressAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        addressAdapter.stopListening();
    }
}