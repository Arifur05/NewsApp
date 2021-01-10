package com.arifur.newsapp.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.arifur.newsapp.R;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.viewmodels.WorldNewsViewModel;

import java.util.List;

public class WorldFragment extends Fragment {

    private WorldNewsViewModel mWorldNewsViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorldNewsViewModel = new ViewModelProvider(this).get(WorldNewsViewModel.class);

        subscribeObservers();
    }

    private void subscribeObservers(){
        mWorldNewsViewModel.getWorldNewsArticle().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_world, container, false);
    }
}