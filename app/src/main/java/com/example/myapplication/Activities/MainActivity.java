package com.example.myapplication.Activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.fragments.CategoryFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.OrderFragment;
import com.example.myapplication.fragments.WishListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView userLoginRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

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
        userLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });


        BottomNavigationView bNavigationView = findViewById(R.id.bottom_navigation);
        bNavigationView.setOnNavigationItemSelectedListener(bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment())
                .commit();

    }



    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){

                case R.id.home:
                     fragment = new HomeFragment();

                    break;

                case R.id.category:
                    fragment = new CategoryFragment();
                    break;

                case R.id.wishList:
                    fragment = new WishListFragment();
                    break;
                case R.id.order:
                    fragment = new OrderFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                    .setCustomAnimations(R.anim.slide_in_left,R.anim.base_slide_right_in) .commit();
            drawerLayout.closeDrawers();
            return true;
        }
    };

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
}