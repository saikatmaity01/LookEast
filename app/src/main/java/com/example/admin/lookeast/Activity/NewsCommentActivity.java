package com.example.admin.lookeast.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.lookeast.Adapter.CommentAdapter;
import com.example.admin.lookeast.Fragment.AppData;
import com.example.admin.lookeast.R;
import com.example.admin.lookeast.SetGet.Comment_SetGet;
import com.example.admin.lookeast.URL.ConstData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsCommentActivity extends AppCompatActivity {
    ListView lv;
    String news_cmnt;
    EditText et_cmnt;
    Button btn_cmnt;
    ArrayList<Comment_SetGet> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_comment);
        lv = (ListView) findViewById(R.id.lv);
        et_cmnt = (EditText) findViewById(R.id.et_cmnt);
        btn_cmnt = (Button) findViewById(R.id.btn_cmnt);
        //news_cmnt = et_cmnt.getText().toString();
        btn_cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cmnt = ConstData.News_Comment;
                //getComment(cmnt, news_cmnt);
                getComment(cmnt);
                Intent intent = new Intent(NewsCommentActivity.this,NewsCommentActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(NewsCommentActivity.this, "Your comment was sucessfully posted", Toast.LENGTH_SHORT).show();
            }
        });
        String url = ConstData.News_Details + "news_id=" + AppData.News_Id;
        getNewsCommentDetails(url);
    }

    public void getNewsCommentDetails(String url) {
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
                        for (int i = 0; i < jnews.length(); i++) {
                            JSONObject jobj_news = jnews.getJSONObject(i);
                            jobj_news.getString("news_title");
                            jobj_news.getString("news_description");
                            jobj_news.getString("likes");
                            jobj_news.getString("image");
                            JSONArray jcomments = jobj_news.getJSONArray("comments");
                            for (int j = 0; j < jcomments.length(); j++) {
                                JSONObject jobj_comments = jcomments.getJSONObject(j);
                                Comment_SetGet comment_setGet = new Comment_SetGet();
                                comment_setGet.setUser(jobj_comments.getString("user"));
                                comment_setGet.setComment(jobj_comments.getString("comment"));
                                comment_setGet.setPost_date(jobj_comments.getString("post_date"));
                                arrayList.add(comment_setGet);
                            }
                            if (jcomments.length() > 0) {
                                CommentAdapter commentAdapter = new CommentAdapter(NewsCommentActivity.this, arrayList);
                                lv.setAdapter(commentAdapter);
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getContext(), "Para Error1", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsCommentActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getComment(String cmnt) {
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
                hashMap.put("comment", et_cmnt.getText().toString().trim());
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
