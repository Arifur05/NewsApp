package com.arifur.newsapp.views;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arifur.newsapp.R;

/**
 * @author : Arif
 * @date : 28-December-2020 01:41 AM
 * @package : com.arifur.newsapp.Views
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public abstract class BaseActivity extends AppCompatActivity {
        ProgressBar mProgressBar;
        @Override
        public void setContentView(int layoutResID) {

                ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
                FrameLayout frameLayout = constraintLayout.findViewById(R.id.container_activity);
                mProgressBar = constraintLayout.findViewById(R.id.progress_bar);

                getLayoutInflater().inflate(layoutResID, frameLayout, true);


                super.setContentView(layoutResID);
        }

        public void showProgressBar(boolean visibility){
                mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
}
