package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseFirestore firebaseFirestore;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String uUsername = binding.NameTextView.getText().toString();
                String uUserEmail = binding.EmailTextView.getText().toString();
                String uNumber = binding.PhoneTextView.getText().toString();
                String uEmail = binding.EmailTextView.getText().toString();

                if (uUserEmail.isEmpty() || uUsername.isEmpty() ||  uNumber.isEmpty() || uEmail.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All Fields Are Required.", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Customer")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    binding.progressBar.setVisibility(View.VISIBLE);
                                    for(QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                                        Log.v("Data Get", String.valueOf(querySnapshot));
                                        Log.v("Data MGet", String.valueOf(querySnapshot.get("Number")));
                                        String number2 = String.valueOf(querySnapshot.get("Number"));
                                        String email = String.valueOf(querySnapshot.get("Email"));

                                        if (uNumber.equals(number2) || uEmail.equals(email)){
                                            count++;
                                        }
                                    }
                                    if (count>=1){
                                        Toast.makeText(LoginActivity.this, " Mobile Number/Email Already Registered ", Toast.LENGTH_LONG).show();
                                        binding.progressBar.setVisibility(View.GONE);
                                    }else {
                                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                        intent.putExtra("number", uNumber);
                                        intent.putExtra("uUsername", uUsername);
                                        intent.putExtra("uEmail", uEmail);
                                        startActivity(intent);
                                    }
                                }
                            });
                }

            }
        });
    }
}