package com.example.krishnateja.buddiesnearby.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Utils.FriendsFragmentUtils.FriendsFragmentRecyclerAdapter;

/**
 * Created by krishnateja on 4/15/2015.
 */
public class FriendsFragment extends Fragment {

    FriendsFragmentRecyclerAdapter mFriendsFragmentRecyclerAdapter;

    private static final String TAG = FriendsFragment.class.getSimpleName();
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_friends, container, false);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_friends_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mFriendsFragmentRecyclerAdapter=new FriendsFragmentRecyclerAdapter();
        recyclerView.setAdapter(mFriendsFragmentRecyclerAdapter);
    }

}
