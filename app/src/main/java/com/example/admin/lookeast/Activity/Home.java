package com.example.admin.lookeast.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.lookeast.Fragment.AppData;
import com.example.admin.lookeast.Fragment.Business;
import com.example.admin.lookeast.Fragment.International;
import com.example.admin.lookeast.Fragment.National;
import com.example.admin.lookeast.Fragment.Political;
import com.example.admin.lookeast.Fragment.Sports;
import com.example.admin.lookeast.URL.ConstData;
import com.example.admin.lookeast.Fragment.State;
import com.example.admin.lookeast.Fragment.Entertainment;
import com.example.admin.lookeast.SetGet.NewsList_Setget;
import com.example.admin.lookeast.R;
import com.example.admin.lookeast.Utility.Util;
import com.example.admin.lookeast.Adapter.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    DrawerLayout drawer_layout;
    ImageView img_view_menu_activity_main;
    ArrayList<NewsList_Setget> newsListSetget = new ArrayList<>();
    private ViewPagerAdapter mAdapter = null;
    private TabLayout mTabs;
    private ViewPager mContainer;
  // int limit=(ViewPagerAdapter.getCount() > 1 ? ViewPagerAdapter.getCount() - 1 : 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        img_view_menu_activity_main = (ImageView) findViewById(R.id.img_view_menu_activity_main);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mContainer = (ViewPager) findViewById(R.id.container);
        img_view_menu_activity_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        if(Util.isConnected(this))
        {
            getNewsList();
        }
        else
        {
            Toast.makeText(this, "Please Connect Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void getNewsList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ConstData.News_List, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                String status, message;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jobj = jsonObject.getJSONObject("response");
                    status = jobj.getString("status");
                    message = jobj.getString("message");
                    if (status.equals("1")) {
                        //Toast.makeText(Home.this, message, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jobj.getJSONArray("category_list");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jcat_list = jsonArray.getJSONObject(i);
                                NewsList_Setget newsList_setget = new NewsList_Setget();
                               /* AppData.Cat_Id = jcat_list.getString("cat_id");
                                newsList_setget.setCat_id(AppData.Cat_Id);*/
                                newsList_setget.setCat_id(jcat_list.getString("cat_id"));
                                newsList_setget.setCategory_name(jcat_list.getString("category_name"));
                                newsListSetget.add(newsList_setget);
                            }
                        }
                    }
                    Locale l = Locale.getDefault();
                    mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                    mAdapter.addFragment(new International(), "International".toUpperCase(l));
                    mAdapter.addFragment(new National(), "National".toUpperCase(l));
                    mAdapter.addFragment(new State(), "State".toUpperCase(l));
                    mAdapter.addFragment(new Political(), "Political".toUpperCase(l));
                    mAdapter.addFragment(new Business(), "Business".toUpperCase(l));
                    mAdapter.addFragment(new Entertainment(), "Entertainment".toUpperCase(l));
                    mAdapter.addFragment(new Sports(), "Sports".toUpperCase(l));
                    mContainer.setAdapter(mAdapter);

                  // mContainer.setOffscreenPageLimit(3);
                    // mContainer.getAdapter().notifyDataSetChanged();

                    mTabs.setupWithViewPager(mContainer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
