package com.aymardkouakou.tablayoutcoordinatorsample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private List<String> mDatas;
    private String mTitle;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    MainFragment withTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public String getTitle() {
        return this.mTitle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        initData();
        mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerAdapter(mRecyclerView.getContext(), mDatas));

        return v;
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add(mTitle + (char) i);
        }
    }

}
