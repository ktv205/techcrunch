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

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.LeftDrawerModel;
import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Utils.LeftDrawerUtils.LeftDrawerRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class LeftDrawerFragment extends Fragment {
    LeftDrawerRecyclerAdapter mLeftDrawerRecyclerAdapter;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_left_drawer, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_left_drawer_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mLeftDrawerRecyclerAdapter = new LeftDrawerRecyclerAdapter(filldata());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mLeftDrawerRecyclerAdapter);

    }

    public void getDrawerLayout(DrawerLayout drawerLayout) {

    }

    public ArrayList<LeftDrawerModel> filldata() {
        ArrayList<LeftDrawerModel> leftDrawerModelArrayList = new ArrayList<>();
        String[] names = {AppConstants.LeftDrawerConstants.FEEDBACK, AppConstants.LeftDrawerConstants.SETTINGS, AppConstants.LeftDrawerConstants.LOGOUT};
        int[] ress = {R.drawable.feedback, R.drawable.settings, R.drawable.logout};
        for (int i = 0; i < names.length; i++) {
            LeftDrawerModel model = new LeftDrawerModel();
            model.setName(names[i]);
            model.setRes(ress[i]);
            leftDrawerModelArrayList.add(model);
        }
        return leftDrawerModelArrayList;


    }
}
