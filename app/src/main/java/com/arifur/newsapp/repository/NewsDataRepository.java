package com.arifur.newsapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.NewsApiClient;

import java.util.List;

/**
 * @author : Arif
 * @date : 04-January-2021 06:59 PM
 * @package : com.arifur.newsapp.repository
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class NewsDataRepository {
    private static NewsDataRepository instance;
    private static NewsApiClient mApiClient;
    public static NewsDataRepository getInstance(){
        if (instance==null){
            instance= new NewsDataRepository();
        }
        return instance;
    }

    private NewsDataRepository(){
        mApiClient= NewsApiClient.getInstance();

    }

    public LiveData<List<Article>> getArticles(){
        return mApiClient.getArticles();
    }

}
