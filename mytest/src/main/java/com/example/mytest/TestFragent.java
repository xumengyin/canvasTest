package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/11/10.
 */
public class TestFragent extends Fragment implements View.OnClickListener {
    LinearLayout mLandlayout, mPortLayout;
    boolean isPort = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_config, null, false);
        mLandlayout = (LinearLayout) view.findViewById(R.id.land_layout);
        mPortLayout = (LinearLayout) view.findViewById(R.id.port_layout);
        mLandlayout.setOnClickListener(this);
        mPortLayout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mLandlayout)) {
            setLan(false);
        } else {
            setLan(true);
        }
    }

    private void setLan(boolean isLand) {
        getActivity().sendBroadcast(new Intent("haha").putExtra("land", isLand));
        if (isLand) {
            mLandlayout.setVisibility(View.VISIBLE);
            mLandlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 720));
            mPortLayout.setVisibility(View.GONE);
        } else {
            mLandlayout.setVisibility(View.GONE);
            mPortLayout.setVisibility(View.VISIBLE);
            mLandlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
        }
    }
}
