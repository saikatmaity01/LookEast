package com.example.admin.lookeast.Activity;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.lookeast.Adapter.ViewPagerAdapter1;
import com.example.admin.lookeast.Fragment.Entertainment_Fragment;
import com.example.admin.lookeast.R;

public class NewsActivity extends AppCompatActivity {
    DrawerLayout drawer_layout;
    ImageView img_view_menu_activity_main;
    private ViewPagerAdapter1 mAdapter = null;
    private ViewPager mContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        img_view_menu_activity_main = (ImageView) findViewById(R.id.img_view_menu_activity_main);
        mContainer = (ViewPager) findViewById(R.id.news_container);
        img_view_menu_activity_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        mAdapter = new ViewPagerAdapter1(getSupportFragmentManager());
        mAdapter.addFragment(new Entertainment_Fragment());
        /*mAdapter.addFragment(new State_Fragment());
        mAdapter.addFragment(new Sports_Fragment());*/
        mContainer.setAdapter(mAdapter);
    }
}
