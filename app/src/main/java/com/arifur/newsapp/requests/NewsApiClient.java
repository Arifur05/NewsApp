package com.arifur.newsapp.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.arifur.newsapp.AppExecutors;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.response.NewsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import retrofit2.Call;
import retrofit2.Response;

import static com.arifur.newsapp.util.Constants.API_KEY;

/**
 * @author : Arif
 * @date : 06-January-2021 12:43 AM
 * @package : com.arifur.newsapp.requests
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class NewsApiClient {
    private static final String TAG = "NewsApiClient";
    private static NewsApiClient instance;
    private MutableLiveData<List<Article>> mArticle= new MutableLiveData<>();
    private RetriveWorldNewsRunnable mRetriveWorldNewsRunnable;

    public static NewsApiClient getInstance() {
        if (instance == null) {
            instance = new NewsApiClient();
        }
        return instance;
    }

    private NewsApiClient() {
        mArticle = new MutableLiveData<>();
    }

    public LiveData<List<Article>> getArticles() {
        return mArticle;
    }

    public void getWorldNewsApi(String sources) {
        if (mRetriveWorldNewsRunnable != null) {
            mRetriveWorldNewsRunnable = null;
        }
        mRetriveWorldNewsRunnable = new RetriveWorldNewsRunnable(sources);
        final Future handler = AppExecutors.getInstance().getNewtworkIO().submit(mRetriveWorldNewsRunnable);
        AppExecutors.getInstance().getNewtworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);

            }
        }, 10000, TimeUnit.MILLISECONDS);
    }

    private class RetriveWorldNewsRunnable implements Runnable {
        String sources;

        public RetriveWorldNewsRunnable(String sources) {
            this.sources = sources;
        }


        @Override
        public void run() {
            try {
                Response<NewsResponse> response = getNews(sources).execute();
                if (response.code()==200 & response.body()!=null){
                    List<Article> articleslist = new ArrayList<Article>
                            (((NewsResponse) response.body()).getArticle());
                    mArticle.postValue(articleslist);
                    for (Article article: articleslist) {
                        Log.d(TAG, "run: " + article);
                    }
                }
                else {
                    Log.d(TAG, "run: "+response.errorBody());
                }
            } catch (IOException e) {
                Log.d(TAG, "run: "+e.getMessage());
            }
        }

        private Call<NewsResponse> getNews(String sources) {
            return ServiceGenerator.getNewsApi().getWorldNews(API_KEY,"en", sources);
        }
    }
}
