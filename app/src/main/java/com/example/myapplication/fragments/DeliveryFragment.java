package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.AddressActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDeliveryBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DeliveryFragment extends Fragment {
    String totalAmount;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    TextView topText;
    ImageView deliveryImg;
    public DeliveryFragment() {
        // Required empty public constructor
    }

    FragmentDeliveryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        topText = getActivity().findViewById(R.id.deliveryTxt);
        topText.setTextColor(getResources().getColor(R.color.black));
        deliveryImg = getActivity().findViewById(R.id.deliveryImg);
        deliveryImg.setImageResource(R.drawable.ic_baseline_navigate_next_white_24);
        deliveryImg.setBackgroundResource(R.drawable.green_oval_background);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        totalAmount= getArguments().getString("totalAmount");
        binding.totalAmount.setText(totalAmount);
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();

            }
        });
        return view;
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
//                            finish();
                            checkDefaultAddress();


                        }else if (!queryDocumentSnapshots.isEmpty()){
//                                    startActivity(new Intent(AddToCardActivity.this, SelectAddressActivity.class));
                            Bundle bundle = new Bundle();
                            bundle.putString("totalAmount",totalAmount);
                            selectAddressFragment selectAddressFragment= new selectAddressFragment();
                            selectAddressFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().add(selectAddressFragment,"selectAddress").replace(R.id.fragmentLayout,selectAddressFragment)
                                    .commit();
//                            topText.setBackgroundColor(getResources().getColor(R.color.white));


                        }
                    }
                });
    }

   private void checkDefaultAddress(){

        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("defaultAddress")
                .document("defaultAddress")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()){
                            Intent intent = new Intent(getContext(), AddressActivity.class);
                            intent.putExtra("totalAmount",totalAmount);
                            intent.putExtra("token","1");
                            startActivity(intent);
                        }else if (documentSnapshot.exists()){
                            Bundle bundle = new Bundle();
                            bundle.putString("totalAmount",totalAmount);
                            selectAddressFragment selectAddressFragment= new selectAddressFragment();
                            selectAddressFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().add(selectAddressFragment,"selectAddress").replace(R.id.fragmentLayout,selectAddressFragment)
                                    .commit();
                        }
                    }
                });

   }
}