package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySignInDataBinding;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignInDataActivity extends AppCompatActivity {

    ActivitySignInDataBinding binding;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    Intent intent;
    GoogleSignInAccount acct;
    Profile profile;
    int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        profile = Profile.getCurrentProfile();
        acct = GoogleSignIn.getLastSignedInAccount(this);

        if (firebaseUser!=null){
            if (firebaseUser.getEmail()!=null){
                binding.EmailTextView.setText(firebaseUser.getEmail());
                if (binding.EmailTextView.getText().toString() != null && binding.EmailTextView.getText().toString() != ""&& !binding.EmailTextView.getText().toString().isEmpty()) {
                    binding.EmailTextView.setEnabled(false);
                }
            }
        }

        facebookData();
        gmailData();
    }

    private void gmailData() {

        if (acct != null) {

            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
            binding.userName.setText(personName);
            binding.EmailTextView.setEnabled(false);
            binding.EmailTextView.setText(personEmail);
        }
    }

    private void facebookData() {

        if (profile!=null){
            binding.userName.setText(profile.getName());
        }
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                final String uUsername = binding.userName.getText().toString();
                String uUserEmail = binding.EmailTextView.getText().toString();
                String uNumber = binding.PhoneTextView.getText().toString();
                String uEmail = binding.EmailTextView.getText().toString();


                if (uUserEmail.isEmpty() || uUsername.isEmpty() || uNumber.isEmpty()) {
                    Toast.makeText(SignInDataActivity.this, "All Fields Are Required.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Users")
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

                                        if (uNumber.equals(number2)&& uEmail.equals(email)){
                                            count++;
                                        }
                                    }
                                    if (count>=1){
                                        Toast.makeText(SignInDataActivity.this, " Mobile Number/Email Already Registered ", Toast.LENGTH_LONG).show();
                                        binding.progressBar.setVisibility(View.GONE);
                                    }else {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        firebaseFirestore = FirebaseFirestore.getInstance();
                                        String uid = user.getUid();
                                        Map<String, Object> note = new HashMap<>();
                                        note.put("Name", uUsername);
                                        note.put("Number", uNumber);
                                        note.put("uid", user.getUid());
                                        note.put("Email", uEmail);


                                        firebaseFirestore.collection("Customer")
                                                .document(user.getUid())
                                                .set(note)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            finish();
                                                            Log.d("Push", "DocumentSnapshot successfully written!");
                                                            startActivity(new Intent(SignInDataActivity.this,MainActivity.class));
                                                        } else {
                                                            Log.d("Push", "Failed");
                                                        }
                                                        binding.progressBar.setVisibility(View.GONE);


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                                    }
                                }
                            });
                }

                }

        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(SignInDataActivity.this,MainActivity.class);
//        intent.putExtra("token","1");
//        startActivity(intent);
//    }
}