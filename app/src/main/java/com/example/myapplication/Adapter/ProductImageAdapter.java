package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;


public class ProductImageAdapter extends PagerAdapter {

    int[] defaultImages;
    Context context;
    List<String >imageUrl;

    public ProductImageAdapter(int[] defaultImages, Context context, List<String >imageUrl) {
        this.defaultImages = defaultImages;
        this.context = context;
        this.imageUrl=imageUrl;
    }

    @Override
    public int getCount() {
        return imageUrl.size();
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

        Glide.with(context).load(imageUrl.get(position)).into(imageView);
//        imageView.setImageResource(defaultImages[position]);
        container.addView(imageLayout, 0);
        return imageLayout;
    }
}
