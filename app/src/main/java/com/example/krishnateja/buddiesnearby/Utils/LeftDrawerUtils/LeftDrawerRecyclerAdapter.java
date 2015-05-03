package com.example.krishnateja.buddiesnearby.Utils.LeftDrawerUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.buddiesnearby.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class LeftDrawerRecyclerAdapter extends RecyclerView.Adapter<LeftDrawerRecyclerAdapter.ViewHolder> {


    public LeftDrawerRecyclerAdapter(Context context, ArrayList<String> data, HashMap<Integer, Integer> sections) {

    }

    public LeftDrawerRecyclerAdapter() {

    }

    public static final String TAG = LeftDrawerRecyclerAdapter.class.getSimpleName();

    @Override
    public LeftDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left_drawer_recycle_view, viewGroup, false), i);


    }


    @Override
    public void onBindViewHolder(final LeftDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {

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
