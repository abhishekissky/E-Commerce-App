package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSelectAddressBinding;


public class selectAddressFragment extends Fragment {


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


        return view;
    }
}