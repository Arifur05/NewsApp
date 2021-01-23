package com.arifur.newsapp.views;


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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arifur.newsapp.R;
import com.arifur.newsapp.adapters.OnNewsListener;
import com.arifur.newsapp.adapters.WorldNewsRecyclerAdapter;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.NewsApi;
import com.arifur.newsapp.requests.NewsApiClient;
import com.arifur.newsapp.requests.ServiceGenerator;
import com.arifur.newsapp.requests.response.NewsResponse;
import com.arifur.newsapp.viewmodels.WorldNewsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.arifur.newsapp.util.Constants.API_KEY;

public class WorldFragment extends Fragment {
    private static final String TAG = "WorldFragment";
    private WorldNewsViewModel mWorldNewsViewModel;
    private RecyclerView mTopHeadlinesRV;
    private WorldNewsRecyclerAdapter mWorldNewsRecyclerAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorldNewsViewModel =  new ViewModelProvider(this).get(WorldNewsViewModel.class);

        //initRecyclerView();
        subscribeObservers();
        getTopHeadlines();
        //testApiCall();

    }

    private void subscribeObservers(){
        mWorldNewsViewModel.getWorldNewsArticle().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles!= null){
                    for (Article article: articles){
                        Log.d(TAG, "onChanged: " +article.getTitle());
                        mWorldNewsRecyclerAdapter=new WorldNewsRecyclerAdapter(articles);
                        mTopHeadlinesRV.setAdapter(mWorldNewsRecyclerAdapter);
                    }
                }
                else {
                    Log.d("Main", "onChanged: articles Null" +articles.get(100));
                }
            }
        });
        //getAllNews();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_world, container, false);
        mTopHeadlinesRV= view.findViewById(R.id.world_top_headlines_rv);
        initRecyclerView();
        return view;
    }
    public void getTopHeadlines(){
        mWorldNewsViewModel.getAllNews("bbc-news, cnn, independent,abc-news");

    }
    public void getAllNews(){
        mWorldNewsViewModel.getAllNews("");

    }

    public void initRecyclerView(){

        mTopHeadlinesRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false ));


    }


    public void testApiCall(){
        NewsApi newsApi=ServiceGenerator.getNewsApi();
        Call<NewsResponse> newsResponseCall= newsApi.getWorldNews(API_KEY,"en","bbc-news, cnn, independent,abc-news");
        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if(response.isSuccessful()){
                    NewsResponse responses= response.body();
                    Log.d(TAG, "onResponse: "+responses);
                }
                else {
                    Log.d(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}