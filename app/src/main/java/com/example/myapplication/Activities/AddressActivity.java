package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {

    ActivityAddressBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<String> cities = new ArrayList<>();

        cities.add(0,"Select State");
        cities.add("Andhra Pradesh");
        cities.add("Arunachal Pradesh");
        cities.add("Assam");
        cities.add("Bihar");
        cities.add("Chhattisgarh");
        cities.add("Goa");
        cities.add("Gujarat");
        cities.add("Haryana");
        cities.add("Himachal Pradesh");
        cities.add("Jharkhand");
        cities.add("Karnataka");
        cities.add("Kerala");
        cities.add("Madhya Pradesh");
        cities.add("Manipur");
        cities.add("Meghalaya");
        cities.add("Mizoram");
        cities.add("Nagaland");
        cities.add("Odisha");
        cities.add("Punjab");
        cities.add("Rajasthan");
        cities.add("Sikkim");
        cities.add("Tamil Nadu");
        cities.add("Telangana");
        cities.add("Tripura");
        cities.add("Uttar Pradesh");
        cities.add("Uttarakhand");
        cities.add("West Bengal");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,cities);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.setAdapter(dataAdapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("Select State"))
                {

                }
                else
                {
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(AddressActivity.this, "Selected"+item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}