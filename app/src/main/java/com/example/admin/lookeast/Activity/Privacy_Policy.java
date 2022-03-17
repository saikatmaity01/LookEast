package com.example.admin.lookeast.Activity;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.lookeast.R;

public class Privacy_Policy extends AppCompatActivity {
    DrawerLayout drawer_layout;
    ImageView img_view_menu_activity_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__policy);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        img_view_menu_activity_main = (ImageView) findViewById(R.id.img_view_menu_activity_main);
        img_view_menu_activity_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
    }
}
