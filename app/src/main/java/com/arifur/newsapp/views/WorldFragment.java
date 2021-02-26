package com.arifur.newsapp.views;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arifur.newsapp.R;
import com.arifur.newsapp.adapters.AllNewsAdapter;
import com.arifur.newsapp.adapters.OnNewsListener;
import com.arifur.newsapp.adapters.WorldNewsHeadlinesAdapter;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.util.Resource;
import com.arifur.newsapp.viewmodels.NewsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WorldFragment extends Fragment implements OnNewsListener {
    private static final String TAG = "WorldFragment";
    private NewsViewModel mNewsViewModel;
    private RecyclerView mTopHeadlinesRV, mAllNewsRV;
    private WorldNewsHeadlinesAdapter mWorldNewsHeadlinesAdapter;
    private AllNewsAdapter mAllNewsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);


        subscribeObservers();
        getTopHeadlines();

        //testApiCall();

    }

    private void subscribeObservers() {
        mNewsViewModel.getWorldNewsHeadlinesArticle().observe(this, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                if (listResource.data != null) {
                    mWorldNewsHeadlinesAdapter = new WorldNewsHeadlinesAdapter(listResource.data, initGlide());
                    mTopHeadlinesRV.setAdapter(mWorldNewsHeadlinesAdapter);
                } else {
                    Log.d(TAG, "onChanged: List is Null\n" + listResource.status);
                }
            }
        });

        mNewsViewModel.getAllNewsArticle().observe(this, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                if (listResource.status == Resource.Status.SUCCESS) {
                    if (listResource.data != null) {
                        mAllNewsAdapter.setArticle(listResource.data);
                        Log.d(TAG, "onChanged: " + listResource.data.size());
                    }
                } else {
                    Log.d(TAG, "onChanged: All News " + listResource.message);
                }
            }


        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_world, container, false);
        mTopHeadlinesRV = view.findViewById(R.id.world_top_headlines_rv);
        mAllNewsRV = view.findViewById(R.id.allnews_rv);
        initRecyclerView();
        return view;
    }

    public void getTopHeadlines() {
        mNewsViewModel.getNewsHeadlines();
        mNewsViewModel.getAllNews();
    }


    public void initRecyclerView() {
        mTopHeadlinesRV.setAdapter(mWorldNewsHeadlinesAdapter);
        mAllNewsAdapter = new AllNewsAdapter(this, initGlide());
        mAllNewsRV.setAdapter(mAllNewsAdapter);
        mAllNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        //intent.putExtra("headline",mWorldNewsHeadlinesAdapter.getSelectedArticle(position));
        intent.putExtra("article", mAllNewsAdapter.getSelectedArticle(position));
        startActivity(intent);
    }

    private RequestManager initGlide() {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }
}