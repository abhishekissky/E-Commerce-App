package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCheckOutBinding;
import com.example.myapplication.fragments.DeliveryFragment;
import com.example.myapplication.fragments.selectAddressFragment;

public class CheckOutActivity extends AppCompatActivity {

    ActivityCheckOutBinding binding;
    Intent intent;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getSupportActionBar().setTitle("CheckOut");
        intent = getIntent();
        token=intent.getStringExtra("token");
        DeliveryFragment deliveryFragment = new DeliveryFragment();
        selectAddressFragment selectAddressFragment = new selectAddressFragment();
//        PaymentFragment fragment2 = new PaymentFragment();
        if (token.equals("1")){
            Bundle bundle = new Bundle();
            bundle.putString("totalAmount",intent.getStringExtra("totalAmount"));
            selectAddressFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentLayout,selectAddressFragment);
            transaction.commit();

            binding.deliveryTxt.setTextColor(getResources().getColor(R.color.black));
            binding.deliveryImg.setImageResource(R.drawable.ic_baseline_navigate_next_white_24);
            binding.deliveryImg.setBackgroundResource(R.drawable.green_oval_background);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("totalAmount",intent.getStringExtra("totalAmount"));
            deliveryFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentLayout,deliveryFragment);
            transaction.commit();
        }


//    }

    }



}