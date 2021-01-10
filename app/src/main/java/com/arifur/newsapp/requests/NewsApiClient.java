package com.arifur.newsapp.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.arifur.newsapp.AppExecutors;
import com.arifur.newsapp.model.Article;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author : Arif
 * @date : 06-January-2021 12:43 AM
 * @package : com.arifur.newsapp.requests
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class NewsApiClient {
    private static NewsApiClient instance;
    private MutableLiveData<List<Article>> mArticle;
    public static NewsApiClient getInstance(){
        if (instance== null){
            instance= new NewsApiClient();
        }
        return instance;
    }

    private NewsApiClient(){
        mArticle=new MutableLiveData<>();
    }

    public LiveData<List<Article>> getArticles(){
        return mArticle;
    }

    public void getWorldNewsApi(){
        final Future handler= AppExecutors.getInstance().getNewtworkIO().submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        AppExecutors.getInstance().getNewtworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }
}
