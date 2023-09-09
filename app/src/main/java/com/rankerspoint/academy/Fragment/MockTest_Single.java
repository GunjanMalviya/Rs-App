package com.rankerspoint.academy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rankerspoint.academy.R;

public class MockTest_Single extends Fragment {

    public MockTest_Single() {
    }

    public static MockTest_Single newInstance() {
        MockTest_Single fragment = new MockTest_Single();
        return fragment;
    }
    Context context;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_mock_test__single, container, false);
        context=container.getContext();
        return view;
    }
}