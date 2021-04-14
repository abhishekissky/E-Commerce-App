package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    private String Number_entered_by_user,code_by_system;

    EditText otpEt;
    Button otpVerifyBtn;
    TextView resendOtp;
    ProgressBar progressBar;
    List<String> uid;
    Intent intent;
    FirebaseFirestore firebaseFirestore;
    String sellerUid,token;
    FirebaseUser user;
    String uUsername,uEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEt = findViewById(R.id.otpEt);
        otpVerifyBtn = findViewById(R.id.otpVerifyBtn);
        resendOtp = findViewById(R.id.resendOtp);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        firebaseFirestore = FirebaseFirestore.getInstance();

        intent = getIntent();
        sellerUid = intent.getStringExtra("seller_uid");
        Number_entered_by_user = intent.getStringExtra("number");
        token = intent.getStringExtra("token");
        uEmail= intent.getStringExtra("uEmail");
        uUsername = intent.getStringExtra("uUsername");

        send_otp_to_user(Number_entered_by_user);

        otpVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCode();
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_otp_to_user(Number_entered_by_user);
            }
        });
    }

    private void checkCode() {
        String userEnteredOtp = otpEt.getText().toString();
        if (userEnteredOtp.isEmpty()|| userEnteredOtp.length()>6){
            Toast.makeText(this, "Wrong OTP", Toast.LENGTH_LONG).show();
            return;
        }
        finish_everything(userEnteredOtp);
    }

    private void send_otp_to_user(String number_entered_by_user) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+number_entered_by_user,
                60,
                TimeUnit.SECONDS,
                this,
                mcallback
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            code_by_system=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code =  phoneAuthCredential.getSmsCode();

            if (code!=null){
                finish_everything(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

//            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("User Error","Verification Failed" + e.getMessage());
        }
    };

    private void finish_everything(String code) {

        otpEt.setText(code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_by_system,code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential credential) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OtpActivity.this, "You Are Login successfully", Toast.LENGTH_LONG).show();
                    if (token!=null){
                        if (token.equals("1")){
                            saveData();
                            intent = new Intent(OtpActivity.this, ProductViewListActivity.class);
                            intent.putExtra("uid",sellerUid);
                        }else {
                            saveData();
                            intent = new Intent(OtpActivity.this, MainActivity.class);
                        }
                    }else {
                        saveData();
                        intent = new Intent(OtpActivity.this,MainActivity.class);

                    }
                    startActivity(intent);

                }else {
                    Log.e("User Error","onComplete"+task.getException().getMessage());

                }
            }
        });
    }

    private void saveData() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user.getUid();
        Map<String, Object> note = new HashMap<>();
        note.put("Name", uUsername);
        note.put("Number",Number_entered_by_user);
        note.put("uid",user.getUid());
        note.put("Email",uEmail);



        firebaseFirestore.collection("Customer")
                .document(user.getUid())
                .set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("Push", "DocumentSnapshot successfully written!");
                        }else {
                            Log.d("Push", "Failed");
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });


    }


}