package com.example.bt_3_assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TitleActivity extends AppCompatActivity {
    ListView listView;
    private Context context;
    ArrayList<String> food_item = new ArrayList<String>();
    ArrayAdapter adapter;
    static private String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        listView = findViewById(R.id.listviewTitle);

        Intent intent = getIntent();

        ArrayList<Food> food = (ArrayList<Food>) intent.getSerializableExtra("food");
        for(int i=0;i<food.size();i++){
            //intent adapter
            food_item.add(food.get(i).getTitle());
            //get URL
            URL = food.get(i).getLink();
            //split title_img
            //replace p
            String p = food.get(i).getDescription();
            p = p.replace("<p>","");
            p = p.replace("</p>","");
            food.get(i).setDescription(p);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(TitleActivity.this);
                dialog.setContentView(R.layout.dialog);

                TextView textView_title = dialog.findViewById(R.id.textView_title);
                textView_title.setText(food.get(i).getTitle());
                TextView textView_description = dialog.findViewById(R.id.textView_description);
                textView_description.setText(food.get(i).getDescription());
                TextView textView_img_des = dialog.findViewById(R.id.textView_img_des);
                textView_img_des.setText(food.get(i).getMedia_description());
                ImageView imageView = dialog.findViewById(R.id.imageView_custom);
                Glide.with(TitleActivity.this)
                        .load(food.get(i).getMedia_content())
                        .into(imageView);
                //c.getFlagImgUrl()).into(imgView)
                //imageView.(food.get(i).setMedia_content());

                TextView textView_img_tilte = dialog.findViewById(R.id.textView_img_title);
                textView_img_tilte.setText(food.get(i).getMedia_title());
                TextView textView_img_date = dialog.findViewById(R.id.textView_img_date);
                textView_img_date.setText(food.get(i).getPubDate());
                Button button_more = dialog.findViewById(R.id.button_more);
                button_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent_web = new Intent(Intent.ACTION_VIEW,Uri.parse(URL));
                        startActivity(intent_web);
                    }
                });
                Button button_close = dialog.findViewById(R.id.button_close);
                button_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });


                dialog.show();

            }
        });


        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, food_item);
        listView.setAdapter(adapter);
    }
}