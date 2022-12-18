package com.example.bt4_googleapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ScrollView scrollViewOfficial = findViewById(R.id.official_activity);
        TextView header = findViewById(R.id.header_title_official);
        TextView vanPhong = findViewById(R.id.vanPhong);
        TextView ten = findViewById(R.id.ten);
        ImageView imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        // Header
        header.setText(intent.getStringExtra("Header Title"));
        // Ten
        vanPhong.setText(intent.getStringExtra("Office Name"));
        Official official = (Official) intent.getSerializableExtra("Official");

        ten.setText(official.name);
        scrollViewOfficial.setBackgroundColor(official.getBackgroundColor());

        // Image official
        if (official.imgExists())
            Glide.with(this)
                    .load(official.img)
                    .error(R.drawable.non_image_user)
                    .placeholder(R.drawable.loading_img).apply(new RequestOptions().override(194, 194))
                    .centerCrop()
                    .into(imageView);
    }
}
