package com.arifur.newsapp.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arifur.newsapp.R;
import com.arifur.newsapp.adapters.WorldNewsHeadlinesAdapter;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.NewsApi;
import com.arifur.newsapp.requests.ServiceGenerator;
import com.arifur.newsapp.requests.response.NewsResponse;
import com.arifur.newsapp.viewmodels.NewsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.arifur.newsapp.util.Constants.API_KEY;


public class BusinessFragment extends Fragment {
    NewsViewModel mNewsViewModel;
    private static final String TAG = "BusinessFragment";
    String q="business";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);


        subscribeObservers();
        getQueriedArticles(q);
        testApiCall();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        return view;
    }

    private void subscribeObservers() {
        mNewsViewModel.getQureiedNewsArticles("business").observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles != null) {
                    for (Article article : articles) {
                        Log.d(TAG, "onChanged: Business" + article.getTitle());

                    }
                } else {
                    Log.d("Main", "onChanged: articles Null" + articles);
                }
            }
        });
    }
    public void testApiCall() {
        NewsApi newsApi = ServiceGenerator.getNewsApi();
        Call<NewsResponse> newsResponseCall = newsApi.getQueryNews(API_KEY, "en", "business", 100);
        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    for (int i =0; i<+100; i++) {
                        Log.d(TAG, "onChanged: Business" + response.body().getArticle().get(i));

                    }
                    NewsResponse responses = response.body();
                    Log.d(TAG, "onResponse: " + responses);
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
    public void getQueriedArticles(String q) {
        mNewsViewModel.getQueriedArticles(q);

    }
}