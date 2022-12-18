package com.example.bt_3_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context context;
    ListView listView;
    ImageView imageView;
    ArrayList<String> arrayList_food;
    ArrayList<Food> arrayList_food_title = new ArrayList<>();
    ArrayAdapter adapter;

    final LoadingDialog loadingdialog = new LoadingDialog(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Food food = new Food();

        imageView = findViewById(R.id.imageViewsgu);
        listView = (ListView) findViewById(R.id.listViewsgu);
        arrayList_food = new ArrayList<>();
        arrayList_food.add("Proteins");
        arrayList_food.add("Amino Acids");
        arrayList_food.add("Grains and Starches");
        arrayList_food.add("Fibers and Legumes");
        arrayList_food.add("Vitamins");
        arrayList_food.add("Minerals");

        adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList_food);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String s = arrayList_food.get(position);
                loadingdialog.startLoadingdialog();
                if(s.equals("Proteins")){
                    getFromWebAPI("https://www.petfoodindustry.com/rss/topic/292-proteins");
                }else if(s.equals("Amino Acids")){
                    getFromWebAPI("https://www.petfoodindustry.com/rss/topic/293-amino-acids");
                }else if(s.equals("Grains and Starches")){
                    getFromWebAPI("https://www.petfoodindustry.com/rss/topic/294-grains-and-starches");
                }else if(s.equals("Fibers and Legumes")){
                    getFromWebAPI("https://www.petfoodindustry.com/rss/topic/295-fibers-and-legumes");
                }else if(s.equals("Vitamins")){
                    getFromWebAPI("https://www.petfoodindustry.com/rss/topic/296-vitamins");
                }else if(s.equals("Minerals")){
                    getFromWebAPI("https://www.petfoodindustry.com/rss/topic/297-minerals");
                }
            }
        });




    }
    protected void getFromWebAPI(String url) {
        arrayList_food_title.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Food food = new Food(context,"");
                        try{
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
                           XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                           XmlPullParser parser = parserFactory.newPullParser();
                           parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                           parser.setInput(byteArrayInputStream,null);

                           String tag = "" , text = "", link_img ="";
                           int event = parser.getEventType();
                           while (event!= XmlPullParser.END_DOCUMENT){
                               tag = parser.getName();
                               switch (event){
                                   case XmlPullParser.START_TAG:
                                       if(tag.equals("item"))
                                           food = new Food();
                                       if (tag.equals("media:content")) {
                                           link_img = parser.getAttributeValue(null,"url");
                                           food.setMedia_content(link_img);
                                       }
                                       break;
                                   case XmlPullParser.TEXT:
                                       text = parser.getText();
                                       break;
                                   case XmlPullParser.END_TAG:
                                       switch (tag){
                                           case "title": food.setTitle(text);
                                               break;
                                           case "description": food.setDescription(text);
                                               break;
                                           case "guid": food.setGuid(text);
                                               break;
                                           case "pubDate": food.setPubDate(text);
                                               break;
                                           case "link": food.setLink(text);
                                               break;
                                           case "media:title": food.setMedia_title(text);
                                               break;
                                           case "media:description": food.setMedia_description(text);
                                               break;
                                           case "item":
                                               arrayList_food_title.add(food);
                                               break;
                                       }
                                       break;
                               }
                               event = parser.next();
                           }
                            Intent intent = new Intent(MainActivity.this,TitleActivity.class);
                            intent.putExtra("food", arrayList_food_title);
                            startActivity(intent);

                            adapter.notifyDataSetChanged();
                            loadingdialog.dismissdialog();

                       } catch (XmlPullParserException e) {
                           e.printStackTrace();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
                arrayList_food = null;
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}