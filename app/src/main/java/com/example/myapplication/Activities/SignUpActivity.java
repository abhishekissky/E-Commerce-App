package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySignUpBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    EditText PhoneTextView;
    String sellerUid;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button signUpButton;
    SignInButton sign_in_button;
    Intent intent;
    String token;
    LoginButton connectWithFbButton;
    GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1001;
    FirebaseFirestore firebaseFirestore;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        callbackManager = CallbackManager.Factory.create();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.myapplication",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        }
//        catch (PackageManager.NameNotFoundException e) {
//        }
//        catch (NoSuchAlgorithmException e) {
//        }



        intent = getIntent();
        token = intent.getStringExtra("token");
        callbackManager = CallbackManager.Factory.create();
        firebaseAuth = FirebaseAuth.getInstance();
        PhoneTextView = findViewById(R.id.PhoneTextView);
        signUpButton = findViewById(R.id.signUpButton);
        connectWithFbButton =findViewById(R.id.connectWithFbButton);
        sign_in_button = findViewById(R.id.sign_in_button);
        connectWithFbButton.setPermissions(Arrays.asList(EMAIL));

        //Google SingIn

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(SignUpActivity.this, gso);

        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGmail();
            }
        });

        //Mobile SignIn
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = PhoneTextView.getText().toString();
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(getApplicationContext(), "Enter Enter Mobile Number!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Customer")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for(QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                                        Log.v("Data Get", String.valueOf(querySnapshot));
                                        Log.v("Data MGet", String.valueOf(querySnapshot.get("Number")));
                                        String number2 = String.valueOf(querySnapshot.get("Number"));

                                        if (number.equals(number2)){
                                            count++;
                                            Intent intent = new Intent(SignUpActivity.this,OtpActivity.class);
                                            intent.putExtra("number",number);
                                            intent.putExtra("token",token);
                                            startActivity(intent);
                                        }
                                    }

                                        Toast.makeText(SignUpActivity.this, "Please Register Your Mobile Number", Toast.LENGTH_LONG).show();

//                                    binding.progressBar.setVisibility(View.GONE);
                                }


                            });
                }

            }
        });

        //Facebook SingIn
        binding.connectWithFbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
            }
        });


    }

    private void signInWithGmail() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d("Aryan", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Aryan", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Aryan", "signInWithCredential:success");
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (firebaseUser!=null){
                                binding.progressBar.setVisibility(View.VISIBLE);
                                getData(firebaseUser.getUid());
                            }else {
                                Toast.makeText(SignUpActivity.this, "Some Error ", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Aryan", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("Aryan", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Aryan", "signInWithCredential:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user!=null){
                                binding.progressBar.setVisibility(View.VISIBLE);
                                getData(user.getUid());
                            }
//                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Aryan", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            Toast.makeText(this, "Already logged in ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
        }
    }
    private void getData(String uid){
        binding.progressBar.setVisibility(View.VISIBLE);
        MainActivity mainActivity = new MainActivity();
        firebaseFirestore.collection("Customer")
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if(documentSnapshot.exists()){
                            Log.v("Data",documentSnapshot.getData().toString());
                            if (documentSnapshot.getData().get("Number")!=null&& documentSnapshot.getData().get("Name")!=null){

                                if (token!=null) {
                                    if (token.equals("1")) {

                                        Intent intent = new Intent(SignUpActivity.this, ProductViewListActivity.class);
                                        intent.putExtra("uid", sellerUid);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else {
                                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                    finish();
                                }
                            }else {
                                Intent intent = new Intent(SignUpActivity.this,SignInDataActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            Intent intent = new Intent(SignUpActivity.this,SignInDataActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }


}