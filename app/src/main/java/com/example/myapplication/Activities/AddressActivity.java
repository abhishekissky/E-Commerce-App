package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityAddressBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity  {

    ActivityAddressBinding binding;
    String state,defaultAddress;
    RadioButton radioButton;
    Intent intent;
    String token,totalAmount,accessCode,defaultAdd;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String docId,spinnerData,radioData,checkBoxData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        intent = getIntent();


        token=intent.getStringExtra("token");
        totalAmount=intent.getStringExtra("totalAmount");
        accessCode = intent.getStringExtra("accessCode");

        if (accessCode!=null) {
            if (accessCode.equals("11")) {
                editData();
            }
        }else {
            binding.homeRadioButton.setChecked(true);
        }
        stateSpinner();
        checkBox();
        binding.saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        binding.updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultAdd!=null){
                    if (defaultAdd.equals("Default")) {
                        updateDefaultAddress();
                    }
                }else {
                    updateAddress();
                }
            }
        });




    }
    private void editData() {

        binding.updateAddress.setVisibility(View.VISIBLE);
        binding.saveAddress.setVisibility(View.GONE);

        binding.customerName.setText(intent.getStringExtra("name"));
        binding.customerMobileNumber.setText(intent.getStringExtra("mobileNu"));
        binding.customerAlternateMobileNo.setText(intent.getStringExtra("alternateMoNu"));
        binding.customerAddress.setText(intent.getStringExtra("address"));
        binding.customerLandmark.setText(intent.getStringExtra("landmark"));
        binding.customerCity.setText(intent.getStringExtra("city"));
        binding.customerCountry.setText(intent.getStringExtra("country"));
        binding.customerPinCode.setText(intent.getStringExtra("pinCode"));
        defaultAdd = intent.getStringExtra("defaultAddress");
        if (defaultAdd!=null){
            if (defaultAdd.equals("Default")){
                binding.checkBox.setChecked(true);
            }
        }
        docId = intent.getStringExtra("docId");
        radioData = intent.getStringExtra("addressType");
        spinnerData = intent.getStringExtra("state");


        String home = binding.homeRadioButton.getText().toString();
        String other = binding.OtherRadioButton.getText().toString();
        String office = binding.OfficeRadioButton.getText().toString();

        if (home.equals(radioData)){
            binding.homeRadioButton.setChecked(true);
        }else if (office.equals(radioData)){
            binding.OfficeRadioButton.setChecked(true);
        }else if (other.equals(radioData)){
            binding.OtherRadioButton.setChecked(true);
        }


    }

    private void updateDefaultAddress() {

        binding.progressBar.setVisibility(View.VISIBLE);
        String name = binding.customerName.getText().toString();
        String mobileNu = binding.customerMobileNumber.getText().toString();
        String alternateMobileNu = binding.customerAlternateMobileNo.getText().toString();
        String address = binding.customerAddress.getText().toString();
        String landmark = binding.customerLandmark.getText().toString();
        String pinCode = binding.customerPinCode.getText().toString();
        String city = binding.customerCity.getText().toString();
        String country = binding.customerCountry.getText().toString();
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        String radioBtn = radioButton.getText().toString();


        Map<String, Object> add = new HashMap<>();
        add.put("name", name);
        add.put("mobile_no", mobileNu);
        add.put("alternate_mobile_no", alternateMobileNu);
        add.put("address", address);
        add.put("landmark", landmark);
        add.put("pinCode", pinCode);
        add.put("city", city);
        add.put("country", country);
        add.put("state", state);
        add.put("address_type", radioBtn);

        if (name.isEmpty()|| mobileNu.isEmpty()||address.isEmpty()||pinCode.isEmpty()||city.isEmpty()||country.isEmpty()||radioBtn.isEmpty()){
            Toast.makeText(this, "Fill All Required Filled", Toast.LENGTH_SHORT).show();
        }else if (defaultAddress=="1"){

            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                    .collection("defaultAddress")
                    .document("defaultAddress")
                    .set(add)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(AddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddressActivity.this,CheckOutActivity.class);
                            intent.putExtra("totalAmount",totalAmount);
                            intent.putExtra("token",token);
                            startActivity(intent);
                        }
                    });

        }else {

            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                    .collection("address")
                    .add(add)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            firebaseFirestore.collection("Customer")
                                    .document(firebaseUser.getUid())
                                    .collection("defaultAddress")
                                    .document("defaultAddress")
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            binding.progressBar.setVisibility(View.GONE);
                                            Toast.makeText(AddressActivity.this, "Address Deleted", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddressActivity.this, CheckOutActivity.class);
                                            intent.putExtra("totalAmount",totalAmount);
                                            intent.putExtra("token",token);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    });
        }
    }

    private void updateAddress() {

        binding.progressBar.setVisibility(View.VISIBLE);
        String name = binding.customerName.getText().toString();
        String mobileNu = binding.customerMobileNumber.getText().toString();
        String alternateMobileNu = binding.customerAlternateMobileNo.getText().toString();
        String address = binding.customerAddress.getText().toString();
        String landmark = binding.customerLandmark.getText().toString();
        String pinCode = binding.customerPinCode.getText().toString();
        String city = binding.customerCity.getText().toString();
        String country = binding.customerCountry.getText().toString();
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        String radioBtn = radioButton.getText().toString();


        Map<String, Object> add = new HashMap<>();
        add.put("name", name);
        add.put("mobile_no", mobileNu);
        add.put("alternate_mobile_no", alternateMobileNu);
        add.put("address", address);
        add.put("landmark", landmark);
        add.put("pinCode", pinCode);
        add.put("city", city);
        add.put("country", country);
        add.put("state", state);
        add.put("address_type", radioBtn);

        if (name.isEmpty()|| mobileNu.isEmpty()||address.isEmpty()||pinCode.isEmpty()||city.isEmpty()||country.isEmpty()||radioBtn.isEmpty()){
            Toast.makeText(this, "Fill All Required Filled", Toast.LENGTH_SHORT).show();
        }else if (defaultAddress=="1"){

            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                    .collection("defaultAddress")
                    .document("defaultAddress")
                    .set(add)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                                    .collection("address")
                                    .document(docId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            binding.progressBar.setVisibility(View.GONE);
                                            Toast.makeText(AddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddressActivity.this,CheckOutActivity.class);
                                            intent.putExtra("totalAmount",totalAmount);
                                            intent.putExtra("token",token);
                                            startActivity(intent);

                                        }
                                    });

                        }
                    });

        }else {

            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                    .collection("address")
                    .document(docId)
                    .update(add)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(AddressActivity.this, "Address Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddressActivity.this, CheckOutActivity.class);
                            intent.putExtra("totalAmount",totalAmount);
                            intent.putExtra("token",token);
                            startActivity(intent);
                        }
                    });
        }

    }

    private void checkBox() {
        if (!binding.checkBox.isChecked()) {
            defaultAddress = "0";
        }else {
            defaultAddress="1";
        }
        binding.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkBox.isChecked()){
                    defaultAddress="1";
                }else {
                    defaultAddress="0";
                }
            }
        });
    }

    private void saveAddress() {
        binding.progressBar.setVisibility(View.VISIBLE);
        String name = binding.customerName.getText().toString();
        String mobileNu = binding.customerMobileNumber.getText().toString();
        String alternateMobileNu = binding.customerAlternateMobileNo.getText().toString();
        String address = binding.customerAddress.getText().toString();
        String landmark = binding.customerLandmark.getText().toString();
        String pinCode = binding.customerPinCode.getText().toString();
        String city = binding.customerCity.getText().toString();
        String country = binding.customerCountry.getText().toString();
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        String radioBtn = radioButton.getText().toString();


        Map<String, Object> add = new HashMap<>();
        add.put("name", name);
        add.put("mobile_no", mobileNu);
        add.put("alternate_mobile_no", alternateMobileNu);
        add.put("address", address);
        add.put("landmark", landmark);
        add.put("pinCode", pinCode);
        add.put("city", city);
        add.put("country", country);
        add.put("state", state);
        add.put("address_type", radioBtn);
//        add.put("default_address", defaultAddress);


        if (name.isEmpty()|| mobileNu.isEmpty()||address.isEmpty()||pinCode.isEmpty()||city.isEmpty()||country.isEmpty()||radioBtn.isEmpty()){
            Toast.makeText(this, "Fill All Required Filled", Toast.LENGTH_SHORT).show();
        }else if (defaultAddress=="1"){

            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                    .collection("defaultAddress")
                    .document("defaultAddress")
                    .set(add)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(AddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddressActivity.this,CheckOutActivity.class);
                            intent.putExtra("totalAmount",totalAmount);
                            intent.putExtra("token",token);
                            startActivity(intent);
                        }
                    });

        }else {

            firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                    .collection("address")
                    .add(add)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(AddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddressActivity.this, CheckOutActivity.class);
                            intent.putExtra("totalAmount",totalAmount);
                            intent.putExtra("token",token);
                            startActivity(intent);

                        }
                    });
        }
    }

    private void stateSpinner() {

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
        if (spinnerData != null) {

            int spinnerPosition = dataAdapter.getPosition(spinnerData);

            binding.spinner.setSelection(spinnerPosition);

        }

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("Select State")){


                }
                else
                {
                    state= parent.getItemAtPosition(position).toString();

//                    Toast.makeText(AddressActivity.this, "Selected"+item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}