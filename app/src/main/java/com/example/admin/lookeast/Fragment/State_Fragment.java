package com.example.admin.lookeast.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.lookeast.R;
import com.example.admin.lookeast.URL.ConstData;
import com.example.admin.lookeast.Utility.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class State_Fragment extends Fragment {
    TextView tv_title1,tv_des1;
    ImageView iv_image1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.current_affairs_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_title1=(TextView)getActivity().findViewById(R.id.tv_title1);
        tv_des1=(TextView)getActivity().findViewById(R.id.tv_des1);
        iv_image1=(ImageView) getActivity().findViewById(R.id.iv_image1);
        //String crt=getArguments().getString("SIDE");
        String url = ConstData.News_Details + "news_id=" +AppData.News_Id;
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
                    JSONArray jsonArray=new JSONArray(result);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    JSONObject jobj=jsonObject.getJSONObject("response");
                    status = jobj.getString("status");
                    message = jobj.getString("message");
                    if (status.equals("1")) {
                        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        JSONObject jnews=jobj.getJSONObject("news");
                        tv_title1.setText(jnews.getString("news_title"));
                        tv_des1.setText(jnews.getString("news_description"));
                        Picasso.with(getContext()).load(jnews.getString("image")).resize(750,750).into(iv_image1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(getContext(), "Para Error2", Toast.LENGTH_SHORT).show();
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
}
