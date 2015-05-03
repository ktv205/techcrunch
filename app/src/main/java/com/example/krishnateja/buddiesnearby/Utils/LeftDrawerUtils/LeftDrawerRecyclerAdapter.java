package com.example.krishnateja.buddiesnearby.Utils.LeftDrawerUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishnateja.buddiesnearby.Activities.FeedBackActivity;
import com.example.krishnateja.buddiesnearby.Activities.HelperActivity;
import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.LeftDrawerModel;
import com.example.krishnateja.buddiesnearby.R;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class LeftDrawerRecyclerAdapter extends RecyclerView.Adapter<LeftDrawerRecyclerAdapter.ViewHolder> {

    private ArrayList<LeftDrawerModel> mLeftDrawerModelArrayList;
    private Context mContext;

    public LeftDrawerRecyclerAdapter(Context context, ArrayList<String> data, HashMap<Integer, Integer> sections) {

    }

    public LeftDrawerRecyclerAdapter(Context context, ArrayList<LeftDrawerModel> leftDrawerModelArrayList) {
        mLeftDrawerModelArrayList = leftDrawerModelArrayList;
        mContext = context;

    }

    public static final String TAG = LeftDrawerRecyclerAdapter.class.getSimpleName();

    @Override
    public LeftDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left_drawer_recycle_view, viewGroup, false), i);


    }


    @Override
    public void onBindViewHolder(final LeftDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        viewHolder.textView.setText(mLeftDrawerModelArrayList.get(i).getName());
        viewHolder.imageView.setImageResource(mLeftDrawerModelArrayList.get(i).getRes());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == 0) {
                    mContext.startActivity(new Intent(mContext, FeedBackActivity.class));
                } else if (pos == 1) {

                } else if (pos == 2) {
                    logout();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLeftDrawerModelArrayList.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_imageview);
            textView = (TextView) itemView.findViewById(R.id.list_item_textview);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void logout() {
        FacebookSdk.sdkInitialize(mContext.getApplicationContext());

        if(LoginManager.getInstance()!=null){
            LoginManager.getInstance().logOut();
        }
        mContext.getSharedPreferences
                (AppConstants.AppSharedPref.NAME, Context.MODE_PRIVATE).
                edit().putString(AppConstants.AppSharedPref.USER_ID, "").commit();
        mContext.startActivity(new Intent(mContext, HelperActivity.class));
    }
}
