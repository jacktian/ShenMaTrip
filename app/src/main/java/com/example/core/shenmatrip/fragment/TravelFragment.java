package com.example.core.shenmatrip.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.shenmatrip.R;

/**
 * Created by Core on 2016/2/19.
 */
public class TravelFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel,container,false);

        return view;
    }
}
