package com.example.krishnateja.buddiesnearby.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.krishnateja.buddiesnearby.Fragments.LeftDrawerFragment;
import com.example.krishnateja.buddiesnearby.Fragments.FriendsFragment;
import com.example.krishnateja.buddiesnearby.Fragments.MapViewFragment;
import com.example.krishnateja.buddiesnearby.Fragments.RightDrawerFragment;
import com.example.krishnateja.buddiesnearby.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    private LeftDrawerFragment mLeftDrawerFragment;
    private RightDrawerFragment mRightDrawerFragment;
    private FriendsFragment mMainFragment;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Context mApplicationContext;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int START_FLAG = 1;
    private static final int END_FLAG = 2;
    private GoogleApiClient mGoogleApiClient;
    private int mSelection = 0;
    private int mFlag = 0;
    private static final int DONE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mLeftDrawerFragment = (LeftDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_left_drawer_fragment);
        mRightDrawerFragment = (RightDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_right_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mLeftDrawerFragment.getDrawerLayout(drawerLayout);
        mRightDrawerFragment.getDrawerLayout(drawerLayout);
        setUp(drawerLayout, mToolbar);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    public void logoutFromFacebook(){
        LoginManager.getInstance().logOut();
        Intent intent=new Intent(this,HelperActivity.class);
        startActivity(intent);
    }

    public void openOrClose(int flag) {
        if (flag == START_FLAG) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        }
    }

    public void setUp(final DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };


        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MapViewFragment();

            } else {
                mMainFragment = new FriendsFragment();
                return mMainFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Map";
            } else {
                return "Friends";
            }
        }
    }
}
