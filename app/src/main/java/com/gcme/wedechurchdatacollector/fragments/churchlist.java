package com.gcme.wedechurchdatacollector.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gcme.wedechurchdatacollector.R;


public class churchlist extends Fragment {
    ListView mychurch_listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_churchlist, container, false);
        mychurch_listview= (ListView) view.findViewById(R.id.mychurch_listview);
        return view;
    }



}
