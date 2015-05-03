package com.example.krishnateja.buddiesnearby.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Utils.LeftDrawerUtils.LeftDrawerRecyclerAdapter;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class LeftDrawerFragment extends Fragment {
    LeftDrawerRecyclerAdapter mLeftDrawerRecyclerAdapter;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_left_drawer,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_left_drawer_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mLeftDrawerRecyclerAdapter = new LeftDrawerRecyclerAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mLeftDrawerRecyclerAdapter);

    }

    public void getDrawerLayout(DrawerLayout drawerLayout){

    }
}
