package com.example.admin.lookeast.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admin.lookeast.Activity.About_Us;
import com.example.admin.lookeast.Activity.Contact_Us;
import com.example.admin.lookeast.Activity.Home;
import com.example.admin.lookeast.Activity.Privacy_Policy;
import com.example.admin.lookeast.Activity.Terms_Conditions;
import com.example.admin.lookeast.R;

/**
 * Created by Saikat on 4/28/2018.
 */

public class Slider_Fragment extends Fragment {
    LinearLayout ln_home,ln_PPolicy,ln_TC,ln_Contact_Us,ln_About_us;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ln_home=(LinearLayout)getActivity().findViewById(R.id.ln_home);
        ln_PPolicy=(LinearLayout)getActivity().findViewById(R.id.ln_PPolicy);
        ln_TC=(LinearLayout)getActivity().findViewById(R.id.ln_TC);
        ln_Contact_Us=(LinearLayout)getActivity().findViewById(R.id.ln_Contact_Us);
        ln_About_us=(LinearLayout)getActivity().findViewById(R.id.ln_About_us);
        ln_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Home.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                getActivity().finish();
            }
        });
        ln_PPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Privacy_Policy.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        ln_TC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Terms_Conditions.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        ln_Contact_Us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Contact_Us.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        ln_About_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),About_Us.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
}
