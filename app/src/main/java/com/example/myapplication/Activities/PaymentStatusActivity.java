package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityPaymentStatusBinding;

public class PaymentStatusActivity extends AppCompatActivity {

    ActivityPaymentStatusBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        intent.getStringExtra("status");
        binding.status.setText(intent.getStringExtra("status"));

    }
}