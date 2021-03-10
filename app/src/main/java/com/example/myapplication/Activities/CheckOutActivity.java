package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCheckOutBinding;
import com.example.myapplication.fragments.DeliveryFragment;
import com.example.myapplication.fragments.PaymentFragment;
import com.example.myapplication.fragments.selectAddressFragment;

public class CheckOutActivity extends AppCompatActivity {

    ActivityCheckOutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getSupportActionBar().setTitle("CheckOut");

        DeliveryFragment fragment = new DeliveryFragment();
        selectAddressFragment fragment1 = new selectAddressFragment();
        PaymentFragment fragment2 = new PaymentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentLayout,fragment2);
        transaction.commit();
    }


}