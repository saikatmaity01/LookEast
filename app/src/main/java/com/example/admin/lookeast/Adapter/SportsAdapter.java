package com.example.admin.lookeast.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.lookeast.Activity.NewsActivity;
import com.example.admin.lookeast.Fragment.AppData;
import com.example.admin.lookeast.R;
import com.example.admin.lookeast.SetGet.News_Category_SetGet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SportsAdapter extends BaseAdapter {
    Context context;
    ArrayList<News_Category_SetGet> arr;
    TextView tv_title, tv_des, tv_date;
    ImageView iv_news;
    public SportsAdapter(Context context, ArrayList<News_Category_SetGet> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.news_row,viewGroup,false);
        iv_news = (ImageView) v.findViewById(R.id.iv_news);
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_des = (TextView) v.findViewById(R.id.tv_des);
        tv_date = (TextView) v.findViewById(R.id.tv_date);
        tv_title.setText(arr.get(i).getTitle());
        tv_des.setText(arr.get(i).getDescription());
        tv_date.setText(arr.get(i).getDated());
        Picasso.with(context).load(arr.get(i).getImage()).into(iv_news);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  ArrayList<News_Category_SetGet> arrayList = new ArrayList<>(i);
                String id=arr.get(i).getNews_id();
                AppData.News_Id=id;
                // gridView.setAdapter(null);
                //resultp=data.get(position);
               Toast.makeText(context, "   " + id, Toast.LENGTH_SHORT).show();
                // txt.setText((CharSequence) resultp);
                //resultp = data.get(position);
                //Intent intent = new Intent(context, FullImageActivity.class);
                // Pass all data rank
                //intent.putExtra("rank", resultp.get(Home.RANK));
                // context.startActivity(intent);
                // Sending image id to FullScreenActivity
                Intent intent = new Intent(context, NewsActivity.class);
                //intent.putExtra("news_id", id);
                context.startActivity(intent);


                //   startActivity(new Intent(getApplicationContext(),Profile_details.class).putExtra("wholearray",persons));
            }
        });

        return v;
    }
}
