package com.example.myapplication.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.ProductViewViewPagerAdapter;
import com.example.myapplication.R;

public class ProductViewActivity extends AppCompatActivity {
    int[] defaultImage = {R.drawable.pears,R.drawable.ponet,R.drawable.ic_fruits,R.drawable.kiwi};
    ProductViewViewPagerAdapter productViewViewPagerAdapter;
    ViewPager viewPager;
    TextView product_mrpTxt;
    ImageView back_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        init();

    }

    private void init() {

        viewPager = findViewById(R.id.viewPager);
        product_mrpTxt = findViewById(R.id.product_mrpTxt);
        productViewViewPagerAdapter = new ProductViewViewPagerAdapter(defaultImage,this);
        viewPager.setAdapter(productViewViewPagerAdapter);
        product_mrpTxt.setPaintFlags(product_mrpTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}