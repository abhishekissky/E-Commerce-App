package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapter.OrderListAdapter;
import com.example.myapplication.Models.OrderListModel;
import com.example.myapplication.databinding.FragmentOrderBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    OrderListAdapter orderListAdapter;
    List<OrderListModel>orderListModels;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
        orderListModels = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null) {
            getOrderData();
        }

        return view;
    }

    private void getOrderData() {

        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("orderList")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot allProduct : queryDocumentSnapshots) {
                                OrderListModel orderListModel = allProduct.toObject(OrderListModel.class);
                                orderListModels.add(orderListModel);
                                Log.v("pr", orderListModel.toString());
                            }

                            orderListAdapter = new OrderListAdapter(getContext(), orderListModels);
                            binding.recyclerView.setHasFixedSize(true);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.recyclerView.setAdapter(orderListAdapter);
                        }
                    }
                });
    }
}