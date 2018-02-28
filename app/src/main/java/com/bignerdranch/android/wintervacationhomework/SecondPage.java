package com.bignerdranch.android.wintervacationhomework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 14158 on 2018/2/11.
 */

public class SecondPage extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.second_layout,null);
        return view;
    }
}
