package com.arifur.newsapp.requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.arifur.newsapp.util.AppExecutors;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.response.NewsResponse;

import java.io.IOException;
import java.util.ArrayList;
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
    private final MutableLiveData<List<Article>> mArticle;
    private final MutableLiveData<List<Article>> mAllNewsArticle;
    private RetriveWorldNewsRunnable mRetriveWorldNewsRunnable;
    private RetriveAllNewsRunnable mRetriveAllNewsRunnable;

    public static NewsApiClient getInstance() {
        if (instance == null) {
            instance = new NewsApiClient();
        }
        return instance;
    }

    private NewsApiClient() {
        mArticle = new MutableLiveData<>();
        mAllNewsArticle= new MutableLiveData<>();
    }

    public MutableLiveData<List<Article>> getArticles() {
        return mArticle;
    }
    public MutableLiveData<List<Article>> getAllArticles(){
        return mAllNewsArticle;
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
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void getAllNewsApi() {
        if (mRetriveAllNewsRunnable != null) {
            mRetriveAllNewsRunnable = null;
        }
        mRetriveAllNewsRunnable = new RetriveAllNewsRunnable();
        final Future handlers = AppExecutors.getInstance().getNewtworkIO().submit(mRetriveAllNewsRunnable);
        AppExecutors.getInstance().getNewtworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handlers.cancel(true);

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
                            (response.body().getArticle());
                    mArticle.postValue(articleslist);
                    for (Article article: articleslist) {
                        Log.d(TAG, "run: " + article);
                    }
                }
                else {
                    Log.d(TAG, "run1: "+response.errorBody());
                }
            } catch (IOException e) {
                Log.d(TAG, "run: "+e.getMessage());
            }
        }

        private Call<NewsResponse> getNews(String sources) {
            return ServiceGenerator.getNewsApi().getWorldNews(API_KEY,"en", sources);
        }
    }

    private class RetriveAllNewsRunnable implements Runnable {


        public RetriveAllNewsRunnable() {
        }

        @Override
        public void run() {
            try {
                Response<NewsResponse> response = getAllNews().execute();
                if (response.code()==200 & response.body()!=null){
                    List<Article> allarticleslist = new ArrayList<Article>
                            (response.body().getArticle());
                    mAllNewsArticle.postValue(allarticleslist);
                    for (Article article: allarticleslist) {
                        Log.d(TAG, "run: " + article);
                    }
                }
                else {
                    Log.d(TAG, "run1: "+response.message());
                }
            } catch (IOException e) {
                Log.d(TAG, "run: "+e.getMessage());
            }
        }

        private Call<NewsResponse> getAllNews() {
            return ServiceGenerator.getNewsApi().getAllNews(API_KEY,"en",100);
        }
    }
}
