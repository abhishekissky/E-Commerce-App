package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;


public class ProductViewViewPagerAdapter extends PagerAdapter {

    int[] defaultImages;
    Context context;

    public ProductViewViewPagerAdapter(int[] defaultImages, Context context) {
        this.defaultImages = defaultImages;
        this.context = context;
    }

    @Override
    public int getCount() {


        return defaultImages.length;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.sliding_image_layout, container, false);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imageView);
        imageView.setImageResource(defaultImages[position]);
        container.addView(imageLayout, 0);
        return imageLayout;
    }
}
