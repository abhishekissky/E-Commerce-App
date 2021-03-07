package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.WishListAdapter;
import com.example.myapplication.Models.WishListModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class WishListFragment extends Fragment {

    RecyclerView recyclerView;
    List<WishListModel> wishListModels;
    WishListAdapter wishListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView = view.findViewById(R.id.wish_list_recycleView);
        List<WishListModel>wishListModels1 = new ArrayList<>();
        wishListModels1=getData();

        WishListAdapter adapter = new WishListAdapter(getContext(),wishListModels1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }


    private List<WishListModel> getData() {

        wishListModels = new ArrayList<>();
        wishListModels.add(new WishListModel("Potato","20",R.drawable.kiwi));
        wishListModels.add(new WishListModel("Potato","20",R.drawable.ponet));
        wishListModels.add(new WishListModel("Potato","20",R.drawable.pears));
        wishListModels.add(new WishListModel("Potato","20",R.drawable.ponet));
        wishListModels.add(new WishListModel("Potato","20",R.drawable.ponet));
        wishListModels.add(new WishListModel("Potato","20",R.drawable.ponet));
        wishListModels.add(new WishListModel("Potato","20",R.drawable.ponet));

        return wishListModels;

    }
}