package com.example.bt4_googleapi;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class OfficialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        ScrollView scrollViewOfficial = findViewById(R.id.official_activity);
        TextView header = findViewById(R.id.header_title_official);
        TextView vanPhong = findViewById(R.id.vanPhong);
        TextView ten = findViewById(R.id.ten);
        TextView dang = findViewById(R.id.dang);
        ImageView imageView = findViewById(R.id.imageView);
        TextView address = findViewById(R.id.addressContent);
        TextView phone = findViewById(R.id.phoneContent);
        TextView email = findViewById(R.id.emailContent);
        TextView webSite = findViewById(R.id.webSiteContent);
        ImageView facebookImageView = findViewById(R.id.facebookImageView);
        ImageView googlePlusImageView = findViewById(R.id.googlePlusImageView);
        ImageView twitterImageView = findViewById(R.id.twitterImageView);
        ImageView youtubeImageView = findViewById(R.id.youtubeImageView);

        Intent intent = getIntent();
        // Header
        header.setText(intent.getStringExtra("Header Title"));
        // Ten
        vanPhong.setText(intent.getStringExtra("Office Name"));
        Official official = (Official) intent.getSerializableExtra("Official");

        ten.setText(official.name);
        dang.setText(official.party);
        scrollViewOfficial.setBackgroundColor(official.getBackgroundColor());

        // Image official
        if (official.imgExists())
            Glide.with(this)
                .load(official.img)
                .error(R.drawable.non_image_user)
                .placeholder(R.drawable.loading_img).apply(new RequestOptions().override(194, 194))
                .centerCrop()
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(OfficialActivity.this, PhotoActivity.class);
                photoIntent.putExtras(getIntent().getExtras());
                startActivity(photoIntent);
            }
        });

        // Address
        address.setText("");
        for (Address a : official.address) {
            if (!a.getAddress().equals("No Data Provided")) {
                SpannableString tmpS = new SpannableString(a.getAddress());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        String content = "geo:0,0?q=" + a.getAddress().replace(' ', '+');
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                        startActivity(intent);
                    }
                };
                tmpS.setSpan(clickableSpan, 0, tmpS.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                address.append(tmpS);
            }
            else {
                address.append(a.getAddress());
            }
            address.append("\n");
        }
        address.setMovementMethod(LinkMovementMethod.getInstance());
        address.setHighlightColor(Color.TRANSPARENT);

        // Phone
        phone.setText("");
        for (String a : official.phone) {
            phone.append(a);
            phone.append("\n");
        }

        // Email
        email.setText("");
        for (String a : official.emails) {
            email.append(a);
            email.append("\n");
        }

        // WebSite
        webSite.setText("");
        for (String a : official.urls) {
            webSite.append(a);
            webSite.append("\n");
        }

        // Facebook
        String id = official.getIdChannels("Facebook");
        if (id.equals("No Data Provided"))
            facebookImageView.setVisibility(View.INVISIBLE);
        else
            facebookImageView.setOnClickListener(view -> facebookClicked(official.getIdChannels("Facebook")));

        // Google Plus
        id = official.getIdChannels("GooglePlus");
        if (id.equals("No Data Provided"))
            googlePlusImageView.setVisibility(View.INVISIBLE);
        else
            googlePlusImageView.setOnClickListener(view -> googlePlusClicked(official.getIdChannels("GooglePlus")));

        // Twitter
        id = official.getIdChannels("Twitter");
        if (id.equals("No Data Provided"))
            twitterImageView.setVisibility(View.INVISIBLE);
        twitterImageView.setOnClickListener(view -> twitterClicked(official.getIdChannels("Twitter")));

        // Youtube
        id = official.getIdChannels("YouTube");
        if (id.equals("No Data Provided"))
            youtubeImageView.setVisibility(View.INVISIBLE);
        youtubeImageView.setOnClickListener(view -> youTubeClicked(official.getIdChannels("YouTube")));
    }

    public void facebookClicked(String id) {
        String FACEBOOK_URL = "https://www.facebook.com/" + id;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo("com.facebook.katana", 0);
            urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void twitterClicked(String id) {
        Intent intent;
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + id));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + id));
        }
        startActivity(intent);
    }

    public void googlePlusClicked(String id) {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", id);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + id));
        }
        startActivity(intent);
    }

    public void youTubeClicked(String id) {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + id));
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + id));
        }
        startActivity(intent);
    }
}
