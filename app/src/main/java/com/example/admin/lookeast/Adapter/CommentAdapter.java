package com.example.admin.lookeast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.lookeast.R;
import com.example.admin.lookeast.SetGet.Comment_SetGet;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Comment_SetGet> arr;
    TextView tv_comment, tv_date_time, tv_name;
    public CommentAdapter(Context context, ArrayList<Comment_SetGet> arr) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.news_comment_row,viewGroup,false);
        tv_name=(TextView)v.findViewById(R.id.tv_name);
        tv_comment=(TextView)v.findViewById(R.id.tv_comment);
        tv_date_time=(TextView)v.findViewById(R.id.tv_date_time);
        tv_name.setText(arr.get(i).getUser());
        tv_comment.setText(arr.get(i).getComment());
        tv_date_time.setText(arr.get(i).getPost_date());
        return v;
    }
}
