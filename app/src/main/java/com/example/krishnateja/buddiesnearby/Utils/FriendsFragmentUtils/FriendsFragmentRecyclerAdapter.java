package com.example.krishnateja.buddiesnearby.Utils.FriendsFragmentUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishnateja.buddiesnearby.Activities.ChatActivity;
import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.User;
import com.example.krishnateja.buddiesnearby.R;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class FriendsFragmentRecyclerAdapter extends RecyclerView.Adapter<FriendsFragmentRecyclerAdapter.ViewHolder> {

     private Context mContext;
    private ArrayList<User> mUsers;
    public FriendsFragmentRecyclerAdapter() {


    }

    public static final String TAG = FriendsFragmentRecyclerAdapter.class.getSimpleName();

    public FriendsFragmentRecyclerAdapter(Context context, ArrayList<User> users) {
        mContext=context;
        mUsers=users;

    }

    @Override
    public FriendsFragmentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_fragment_friends_recycle_view, viewGroup, false), i);
    }


    @Override
    public void onBindViewHolder(final FriendsFragmentRecyclerAdapter.ViewHolder viewHolder, int i) {
     viewHolder.textView.setText(mUsers.get(i).get_name());
     viewHolder.textView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(mContext, ChatActivity.class);
             intent.putParcelableArrayListExtra(AppConstants.InAppConstants.FRIENDS,mUsers);
             mContext.startActivity(intent);
         }
     });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView textView;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.item_fragment_friends_recylce_view);

        }

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
