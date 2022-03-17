package com.example.admin.lookeast.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.lookeast.Adapter.MyAdapter;
import com.example.admin.lookeast.Adapter.SportsAdapter;
import com.example.admin.lookeast.SetGet.News_Category_SetGet;
import com.example.admin.lookeast.R;
import com.example.admin.lookeast.URL.ConstData;
import com.example.admin.lookeast.Utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Saikat on 4/29/2018.
 */

public class State extends Fragment {
    RecyclerView rc_current_affairs;
    ArrayList<News_Category_SetGet> arr = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    ListView lv_ca;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.current_affairs,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* rc_current_affairs=(RecyclerView)getActivity().findViewById(R.id.rc_current_affairs);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rc_current_affairs.setLayoutManager(mLayoutManager);*/
        lv_ca=(ListView)getActivity().findViewById(R.id.lv_ca);
       // String url = ConstData.News_Category+"category="+3;
        String url = ConstData.News_Category+"category="+3;
        if (Util.isConnected(getContext()))
        {
            getCurrentAffairsData(url);
        }
        else
        {
            Toast.makeText(getContext(), "Please Check Internet", Toast.LENGTH_SHORT).show();
        }
    }
    public void getCurrentAffairsData(String url)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                String status, message;
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject jobj=jsonObject.getJSONObject("response");
                    status = jobj.getString("status");
                    message = jobj.getString("message");
                    if (status.equals("1")) {
                        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jobj.getJSONArray("news_list");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jnews_list = jsonArray.getJSONObject(i);
                            News_Category_SetGet news_category_setGet = new News_Category_SetGet();
                            AppData.News_Id=jnews_list.getString("news_id");
                            news_category_setGet.setNews_id(AppData.News_Id);
                            //news_category_setGet.setNews_id(jnews_list.getString("news_id"));
                            news_category_setGet.setTitle(jnews_list.getString("title"));
                            news_category_setGet.setDescription(jnews_list.getString("description"));
                            news_category_setGet.setImage(jnews_list.getString("image"));
                            news_category_setGet.setDated(jnews_list.getString("dated"));
                            arr.add(news_category_setGet);
                        }
                        if(arr.size()>0)
                        {
                            SportsAdapter sportsAdapter=new SportsAdapter(getActivity(),arr);
                            lv_ca.setAdapter(sportsAdapter);
                            /*MyAdapter myAdapter=new MyAdapter(getActivity(),arr);
                            rc_current_affairs.setAdapter(myAdapter);*/
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof NetworkError) {
                } else if( error instanceof ClientError) {
                } else if( error instanceof ServerError) {
                } else if( error instanceof AuthFailureError) {
                } else if( error instanceof ParseError) {
                } else if( error instanceof NoConnectionError) {
                } else if( error instanceof TimeoutError) {
                }
            }
        })/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("category", String.valueOf(4));
                return params;
            }
        }*/;
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    }

