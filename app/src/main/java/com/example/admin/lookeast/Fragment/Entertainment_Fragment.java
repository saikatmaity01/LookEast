package com.example.admin.lookeast.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.lookeast.Activity.NewsCommentActivity;
import com.example.admin.lookeast.Adapter.CommentAdapter;
import com.example.admin.lookeast.R;
import com.example.admin.lookeast.SetGet.Comment_SetGet;
import com.example.admin.lookeast.URL.ConstData;
import com.example.admin.lookeast.Utility.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entertainment_Fragment extends Fragment {
    TextView tv_title, tv_des, tv_like;
    ImageView iv_image, iv_like, iv_cmnt, iv_fcbk, iv_like1;
    LinearLayout ll_cmnt;
    Button btn_cmnt;
    String url, news_cmnt, news_like;
    EditText et_cmnt;
    ListView lv;
    //ArrayList<Comment_SetGet> arrayList = new ArrayList<>();
    int flag = 1;
    private long mLastClickTime = 0;
    String android_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.entertainment_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_title = (TextView) getActivity().findViewById(R.id.tv_title);
        tv_like = (TextView) getActivity().findViewById(R.id.tv_like);
        tv_des = (TextView) getActivity().findViewById(R.id.tv_des);
        //tv_name=(TextView)getActivity().findViewById(R.id.tv_name);
        //ll_cmnt = (LinearLayout) getActivity().findViewById(R.id.ll_cmnt);
        iv_image = (ImageView) getActivity().findViewById(R.id.iv_image);
        iv_like = (ImageView) getActivity().findViewById(R.id.iv_like);
        iv_fcbk = (ImageView) getActivity().findViewById(R.id.iv_fcbk);
        iv_cmnt = (ImageView) getActivity().findViewById(R.id.iv_cmnt);
        //btn_cmnt = (Button) getActivity().findViewById(R.id.btn_cmnt);
        // et_cmnt = (EditText) getActivity().findViewById(R.id.et_cmnt);
        //lv = (ListView) getActivity().findViewById(R.id.lv);
        // news_cmnt = et_cmnt.getText().toString();
        // ll_cmnt.setVisibility(View.GONE);
         android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
     //   Toast.makeText(getContext(), android_id, Toast.LENGTH_SHORT).show();
        iv_fcbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "http://www.webcraftzs.com/";
                //String message = AppData.News_Id;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

            }
        });
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //news_like=ConstData.News_Like+"action="+"like"+"&news_id="+AppData.News_Id;
               /* if (flag == 1) {
                   *//* Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);*//*
                    iv_like.setEnabled(true);
                    //iv_like.setBackgroundResource(R.drawable.like_blue);
                } else {
                    iv_like.setEnabled(false);
                }*/
                news_like = ConstData.News_Like;
                getLike(news_like);
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        });
        iv_cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ll_cmnt.setVisibility(View.VISIBLE);
                startActivity(new Intent(getActivity(), NewsCommentActivity.class));
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
       /* btn_cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String cmnt=ConstData.News_Comment+"action="+"comment"+"&news_id="+AppData.News_Id+"&comment="+news_cmnt;
                String cmnt = ConstData.News_Comment;
                getComment(cmnt, news_cmnt);
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
                //tv_name.setVisibility(View.VISIBLE);

            }
        });*/
        //String ent=getArguments().getString("SIDE");
        url = ConstData.News_Details + "news_id=" + AppData.News_Id;
        if (Util.isConnected(getContext())) {

            getNewsDetails(url);
        } else {
            Toast.makeText(getContext(), "Please Check Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void getNewsDetails(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                String status, message;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jobj = jsonObject.getJSONObject("response");
                    status = jobj.getString("status");
                    message = jobj.getString("message");
                    if (status.equals("1")) {
                        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        JSONArray jnews = jobj.getJSONArray("news");
                        //JSONArray device_id = jnews.getJSONArray("device_id");
                        for (int i = 0; i < jnews.length(); i++) {
                            JSONObject jobj_news = jnews.getJSONObject(i);
                            JSONArray device_id = jobj_news.getJSONArray("device_id");
                            tv_title.setText(jobj_news.getString("news_title"));
                            tv_des.setText(jobj_news.getString("news_description"));





                            tv_like.setText(jobj_news.getString("likes"));
                            Picasso.with(getContext()).load(jobj_news.getString("image")).resize(750, 750).into(iv_image);


                            for (int i1 = 0; i1 < device_id.length(); i1++) {

                                String id_device=device_id.getString(i1);
                                if (android_id.equals(id_device)){
                                   // iv_like.setVisibility(View.INVISIBLE);
                                    iv_like.setImageDrawable(getResources().getDrawable(R.drawable.like_blue));
                                    iv_like.setEnabled(false);
                                }
                                else {
                                    iv_like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                                }
                            }

                           /* JSONArray jcomments = jobj_news.getJSONArray("comments");
                            for (int j = 0; j < jcomments.length(); j++) {
                                JSONObject jobj_comments = jcomments.getJSONObject(j);
                                Comment_SetGet comment_setGet = new Comment_SetGet();
                                comment_setGet.setComment(jobj_comments.getString("comment"));
                                comment_setGet.setPost_date(jobj_comments.getString("post_date"));
                                arrayList.add(comment_setGet);
                            }
                            if (jcomments.length() > 0) {
                                CommentAdapter commentAdapter = new CommentAdapter(getActivity(), arrayList);
                                lv.setAdapter(commentAdapter);
                            }*/
                        }

                    }
                } catch (JSONException e) {
                    //e.printStackTrace();
                  Toast.makeText(getContext(), "Para Error1", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getLike(String news_like) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, news_like, new Response.Listener<String>() {
            @Override
            public void onResponse(String request) {

                try {
                    JSONObject jsonObject = new JSONObject(request);
                    JSONObject jobj = jsonObject.getJSONObject("response");
                    jobj.getString("news_like");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("action", "like");
                hashMap.put("news_id", AppData.News_Id);
                hashMap.put("device_id", android_id);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

  /*  public void getComment(String cmnt, final String news_cmnt) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, cmnt, new Response.Listener<String>() {
            @Override
            public void onResponse(String request) {

                try {
                    JSONObject jsonObject = new JSONObject(request);
                    JSONObject jobj = jsonObject.getJSONObject("response");
                    jobj.getString("comment");
                    jobj.getString("dated");
                    jobj.getString("news_id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("action", "comment");
                hashMap.put("news_id", AppData.News_Id);
                hashMap.put("comment", news_cmnt);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }*/
}
