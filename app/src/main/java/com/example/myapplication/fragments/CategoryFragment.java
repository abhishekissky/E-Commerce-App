package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.ProductViewListActivity;
import com.example.myapplication.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    FragmentCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","vegetable");
                startActivity(intent);
            }
        });

        binding.snakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","snake");
                startActivity(intent);
            }
        });


        binding.fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","fruit");
                startActivity(intent);
            }
        });

        binding.bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","bakery");
                startActivity(intent);
            }
        });

        binding.beverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","beverage");
                startActivity(intent);            }
        });


        binding.grain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","grain");
                startActivity(intent);
            }
        });
        return view;
    }
}