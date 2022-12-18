package com.example.bt4_googleapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchDialog.OnInputListener {
    static String ZIP = "60601";

    RecyclerView recyclerView;
    ArrayList<Official> officials = new ArrayList<>();
    ArrayList<Office> offices = new ArrayList<>();
    MyAdapter myAdapter;

    TextView header_title;

    String headerTitle = "";

    final LoadingDialog loadingdialog = new LoadingDialog(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.action_bar);
        }

        // title, recycleView
        recyclerView = findViewById(R.id.recycleView);
        header_title = findViewById(R.id.header_title);

        myAdapter = new MyAdapter(this, officials, offices);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.line)));
        recyclerView.addItemDecoration(itemDecorator);

        loadingdialog.startLoadingdialog();
        getFromWebAPI();
    }

    protected void getFromWebAPI() {
        offices.clear();
        officials.clear();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://civicinfo.googleapis.com/civicinfo/v2/representatives?key=AIzaSyD6v90vcFnaUfIzlDdPHaQKCAOLsrANs4o&address=" + ZIP;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response) {
                        // JSON
                        JSONObject jObj;
                        try {
                            // normalizedInput
                            jObj = new JSONObject(response);
                            JSONObject jo = jObj.getJSONObject("normalizedInput");
                            if (!jo.getString("city").isEmpty())
                                headerTitle += jo.getString("city") + ", ";
                            if (!jo.getString("state").isEmpty())
                                headerTitle += jo.getString("state") + " ";
                            if (!jo.getString("zip").isEmpty())
                                headerTitle += jo.getString("zip");
                            header_title.setText(headerTitle);

                            // offices
                            jObj = new JSONObject(response);
                            JSONArray jsonArry = jObj.getJSONArray("offices");
                            for (int i = 0; i < jsonArry.length(); i++) {
                                Office o = new Office();
                                JSONObject obj = jsonArry.getJSONObject(i);
                                if (!obj.isNull("name")) {
                                    o.name = obj.getString("name");
                                }
                                else {
                                    o.name = "No Data Provided";
                                }
                                if (!obj.isNull("divisionId")) {
                                    o.divisionId = obj.getString("divisionId");
                                }
                                else {
                                    o.divisionId = "No Data Provided";
                                }
                                if (!obj.isNull("levels")) {
                                    JSONArray jsonArray = obj.getJSONArray("levels");
                                    String[] tmpLevels = new String[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        tmpLevels[j] = jsonArray.getString(j);
                                    }
                                    o.levels = tmpLevels;
                                }
                                else {
                                    o.levels = new String[]{"No Data Provided"};
                                }
                                if (!obj.isNull("roles")) {
                                    JSONArray jsonArray = obj.getJSONArray("roles");
                                    String[] tmpRoles = new String[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        tmpRoles[j] = jsonArray.getString(j);
                                    }
                                    o.roles = tmpRoles;
                                }
                                else {
                                    o.roles = new String[]{"No Data Provided"};
                                }
                                if (!obj.isNull("officialIndices")) {
                                    JSONArray jsonArray = obj.getJSONArray("officialIndices");
                                    String[] tmpOfficialIndices = new String[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        tmpOfficialIndices[j] = jsonArray.getString(j);
                                    }
                                    o.officialIndices = tmpOfficialIndices;
                                }
                                else {
                                    o.officialIndices = new String[]{"No Data Provided"};
                                }
                                offices.add(o);
                            }
                            // officials
                            jObj = new JSONObject(response);
                            jsonArry = jObj.getJSONArray("officials");
                            for (int i = 0; i < jsonArry.length(); i++) {
                                Official o = new Official();
                                JSONObject obj = jsonArry.getJSONObject(i);
                                if (!obj.isNull("name")) {
                                    o.name = obj.getString("name");
                                }
                                else {
                                    o.name = "No Data Provided";
                                }
                                if (!obj.isNull("address")) {
                                    JSONArray jsonArray = obj.getJSONArray("address");
                                    Address[] tmpAddress = new Address[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        Address a = new Address();
                                        JSONObject obj2 = jsonArray.getJSONObject(j);
                                        if (!obj2.isNull("line1")) {
                                            a.line.add(obj2.getString("line1"));
                                        }
                                        else {
                                            a.line.add("No Data Provided");
                                        }
                                        if (!obj2.isNull("line2")) {
                                            a.line.add(obj2.getString("line2"));
                                        }
                                        if (!obj2.isNull("line3")) {
                                            a.line.add(obj2.getString("line3"));
                                        }
                                        a.city = obj2.getString("city");
                                        a.state = obj2.getString("state");
                                        a.zip = obj2.getString("zip");
                                        tmpAddress[j] = a;
                                    }
                                    o.address = tmpAddress;
                                }
                                else {
                                    o.address = new Address[]{
                                            new Address("No Data Provided")
                                    };
                                }
                                if (!obj.isNull("party")) {
                                    o.party = obj.getString("party");
                                }
                                else {
                                    o.party = "No Data Provided";
                                }
                                if (!obj.isNull("phones")) {
                                    JSONArray jsonArray = obj.getJSONArray("phones");
                                    String[] tmpPhone = new String[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        tmpPhone[j] = jsonArray.getString(j);
                                    }
                                    o.phone = tmpPhone;
                                }
                                else {
                                    o.phone = new String[]{"No Data Provided"};
                                }
                                if (!obj.isNull("urls")) {
                                    JSONArray jsonArray = obj.getJSONArray("urls");
                                    String[] tmpUrls = new String[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        tmpUrls[j] = jsonArray.getString(j);
                                    }
                                    o.urls = tmpUrls;
                                }
                                else {
                                    o.urls = new String[]{"No Data Provided"};
                                }
                                if (!obj.isNull("emails")) {
                                    JSONArray jsonArray = obj.getJSONArray("emails");
                                    String[] tmpUrls = new String[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        tmpUrls[j] = jsonArray.getString(j);
                                    }
                                    o.emails = tmpUrls;
                                }
                                else {
                                    o.emails = new String[]{"No Data Provided"};
                                }
                                if (!obj.isNull("channels")) {
                                    JSONArray jsonArray = obj.getJSONArray("channels");
                                    Channels[] tmpChannels = new Channels[jsonArray.length()];
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        Channels c = new Channels();
                                        JSONObject obj2 = jsonArray.getJSONObject(j);
                                        c.type = obj2.getString("type");
                                        c.id = obj2.getString("id");
                                        tmpChannels[j] = c;
                                    }
                                    o.channels = tmpChannels;
                                }
                                else {
                                    o.channels = new Channels[]{
                                            new Channels("No Data Provided")
                                    };
                                }
                                if (!obj.isNull("photoUrl"))
                                    o.img = obj.getString("photoUrl");
                                else {
                                    o.img = "No Data Provided";
                                }
                                officials.add(o);
                            }
                            myAdapter.setHeaderTitle(headerTitle);
                            myAdapter.notifyDataSetChanged();
                            loadingdialog.dismissdialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
                headerTitle = "No Data For Location";
                header_title.setText(headerTitle);
                myAdapter.setHeaderTitle(headerTitle);
                offices = null;
                officials = null;
                myAdapter.notifyDataSetChanged();
                loadingdialog.dismissdialog();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void search(View view) {
        SearchDialog searchDialog = new SearchDialog();
        searchDialog.show(getSupportFragmentManager(), String.valueOf(R.string.app_name));
    }

    @Override
    public void sendInput(String input) {
        ZIP = input;
        finish();
        startActivity(getIntent());
    }

    public void about(View view) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}