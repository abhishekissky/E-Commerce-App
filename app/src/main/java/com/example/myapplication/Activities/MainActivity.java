package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.fragments.CategoryFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.OrderFragment;
import com.example.myapplication.fragments.WishListFragment;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView userLoginRegister,cartCount,profileName,email,userName;
    FirebaseFirestore firebaseFirestore;
    ImageView card;
    GoogleSignInAccount acct;
    FirebaseUser firebaseUser;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseFirestore = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        cartCount = findViewById(R.id.cartCount);
        acct = GoogleSignIn.getLastSignedInAccount(this);
        card = findViewById(R.id.card);
        profile = Profile.getCurrentProfile();
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddToCardActivity.class);
                startActivity(intent);
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setItemIconSize(70);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        View headerView = navigationView.getHeaderView(0);
        userLoginRegister = (TextView) headerView.findViewById(R.id.userLoginRegister);
        userName = headerView.findViewById(R.id.userName);

        if (profile!=null){
            userName.setText(profile.getName());
        }

        userLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        if (firebaseUser!=null) {
            cardCount();
        }

        BottomNavigationView bNavigationView = findViewById(R.id.bottom_navigation);
        bNavigationView.setOnNavigationItemSelectedListener(bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment())
                .commit();

    }

    private void cardCount() {

        firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("card")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v("Count", String.valueOf(queryDocumentSnapshots.size()));

                        cartCount.setText(String.valueOf(queryDocumentSnapshots.size()));
                    }
                });
    }


    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){

                case R.id.home:
                     fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                            .setCustomAnimations(R.anim.slide_in_left,R.anim.base_slide_right_in) .commit();
                    drawerLayout.closeDrawers();

                    break;

                case R.id.category:
                    fragment = new CategoryFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                            .setCustomAnimations(R.anim.slide_in_left,R.anim.base_slide_right_in) .commit();
                    drawerLayout.closeDrawers();
                    break;

                case R.id.wishList:
                    fragment = new WishListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                            .setCustomAnimations(R.anim.slide_in_left,R.anim.base_slide_right_in) .commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.order:
                    fragment = new OrderFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                            .setCustomAnimations(R.anim.slide_in_left,R.anim.base_slide_right_in) .commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.logOut:
                    if (Profile.getCurrentProfile()!=null){
                        LoginManager.getInstance().logOut();
                    }else if (acct!=null){
                        signOut();
                    }else if (firebaseUser!=null){
                        FirebaseAuth.getInstance().signOut();
                        signOut();
                    }
                    LoginManager.getInstance().logOut();
                    FirebaseAuth.getInstance().signOut();
                    signOut();
                    drawerLayout.closeDrawers();
                    break;

            }

            return true;
        }
    };

    private void signOut() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

        googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this,"SignOut",Toast.LENGTH_LONG);
            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationView = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment())
                            .commit();
                    break;

                case R.id.category:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new CategoryFragment())
                            .commit();
                    break;

                case R.id.whishList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new WishListFragment())
                            .commit();
                    break;
                case R.id.order:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new OrderFragment())
                            .commit();
                    break;

            }
            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser!=null) {
            cardCount();
        }

    }
}