package com.example.admin.lookeast.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.lookeast.Activity.NewsActivity;
import com.example.admin.lookeast.Fragment.AppData;
import com.example.admin.lookeast.SetGet.News_Category_SetGet;
import com.example.admin.lookeast.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Saikat on 4/29/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    ArrayList<News_Category_SetGet> arrayList;

    public MyAdapter(Context context, ArrayList<News_Category_SetGet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(arrayList.get(position).getImage()).into(holder.iv_news);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_des.setText(arrayList.get(position).getDescription());
        //holder.tv_date.setText(Util.changeAnyDateFormat(arrayList.get(position).getDated(),"yyyy-MM-dd","dd-MM-yyyy"));
        holder.tv_date.setText(arrayList.get(position).getDated());
        holder.ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NewsActivity.class);
                context.startActivity(intent);
               ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_des, tv_date;
        ImageView iv_news;
        RelativeLayout ll_bg;

        public ViewHolder(View v) {
            super(v);
            iv_news = (ImageView) v.findViewById(R.id.iv_news);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_des = (TextView) v.findViewById(R.id.tv_des);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            ll_bg=(RelativeLayout)v.findViewById(R.id.ll_bg);
        }
    }
}
