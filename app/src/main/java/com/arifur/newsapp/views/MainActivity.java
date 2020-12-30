package com.arifur.newsapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.arifur.newsapp.adapters.TabAdapter;
import com.arifur.newsapp.R;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    TabAdapter mTabAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mTabLayout= findViewById(R.id.tabs_layout);
        mViewPager= findViewById(R.id.tab_container_viewPager);

        initTabs();

    }

    public void  initTabs(){
        mTabAdapter= new TabAdapter(getSupportFragmentManager(),0);
        mTabAdapter.addFragment(new WorldFragment(),"World");
        mTabAdapter.addFragment(new BusinessFragment(),"Business");
        mTabAdapter.addFragment(new TechnologyFragment(),"Technology");
        mTabAdapter.addFragment(new SportsFragment(),"Sports");
        mTabAdapter.addFragment(new EntertainmentFragment(),"Entertainment");
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(mTabAdapter.getItemPosition(0));
    }
}
