package com.example.krishnateja.buddiesnearby.Utils.FriendsFragmentUtils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.buddiesnearby.R;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class FriendsFragmentRecyclerAdapter extends RecyclerView.Adapter<FriendsFragmentRecyclerAdapter.ViewHolder> {


    public FriendsFragmentRecyclerAdapter() {


    }

    public static final String TAG = FriendsFragmentRecyclerAdapter.class.getSimpleName();

    public FriendsFragmentRecyclerAdapter(FragmentActivity activity, Object o) {

    }

    @Override
    public FriendsFragmentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_fragment_friends_recycle_view, viewGroup, false), i);
    }


    @Override
    public void onBindViewHolder(final FriendsFragmentRecyclerAdapter.ViewHolder viewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 5;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView, int type) {
            super(itemView);

        }

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
