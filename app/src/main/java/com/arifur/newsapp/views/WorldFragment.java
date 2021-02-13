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
import com.arifur.newsapp.requests.NewsApi;
import com.arifur.newsapp.requests.ServiceGenerator;
import com.arifur.newsapp.requests.response.NewsResponse;
import com.arifur.newsapp.util.Resource;
import com.arifur.newsapp.viewmodels.NewsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.arifur.newsapp.util.Constants.API_KEY;

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
                if (listResource != null) {
                    Log.d(TAG, "onChanged: status" + listResource.status);
                    if (listResource.data != null) {
                        Log.d(TAG, "onChanged: data" + listResource.data);
                    }
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
        //mNewsViewModel.getAllNews();

    }


    public void initRecyclerView() {


        mAllNewsAdapter= new AllNewsAdapter(this);
        mAllNewsRV.setAdapter(mAllNewsAdapter);
        mTopHeadlinesRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAllNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    /*
    public void testApiCall() {
        NewsApi newsApi = ServiceGenerator.getNewsApi();
        Call<NewsResponse> newsResponseCall = newsApi.getAllHeadlines(API_KEY, "en", "bbc-news, cnn, independent,abc-news");
        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse responses = response.body();
                    Log.d(TAG, "onResponse: " + responses.getArticle().get(1).getSource().getId());
                } else {
                    Log.d(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
*/
    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        //intent.putExtra("headline",mWorldNewsHeadlinesAdapter.getSelectedArticle(position));
        intent.putExtra("article" ,mAllNewsAdapter.getSelectedArticle(position));
        startActivity(intent);
    }




}