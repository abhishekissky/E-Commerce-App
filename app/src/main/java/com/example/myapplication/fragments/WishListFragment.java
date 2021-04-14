package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.WishListAdapter;
import com.example.myapplication.Models.WishListModel;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class WishListFragment extends Fragment {

    RecyclerView recyclerView;
//    List<WishListModel> wishListModels;
    WishListAdapter wishListAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    TextView plzAdPro;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView = view.findViewById(R.id.wish_list_recycleView);
        progressBar = view.findViewById(R.id.progressBar);
        plzAdPro = view.findViewById(R.id.plzAdPro);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        progressBar.setVisibility(View.VISIBLE);
        if (firebaseUser!=null) {
            Query query = firebaseFirestore.collection("Customer").document(firebaseUser.getUid()).collection("wish_list");
            FirestoreRecyclerOptions<WishListModel> allProducts = new FirestoreRecyclerOptions.Builder<WishListModel>()
                    .setQuery(query, WishListModel.class)
                    .build();

            wishListAdapter = new WishListAdapter(allProducts, getContext(), progressBar, firebaseFirestore, firebaseUser, plzAdPro);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(wishListAdapter);
        }else {

        }
        return view;


    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseUser!=null) {
            wishListAdapter.stopListening();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firebaseUser!=null) {
            wishListAdapter.startListening();
        }
    }

}